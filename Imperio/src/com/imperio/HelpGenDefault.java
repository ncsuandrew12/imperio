/*
 * MIT License
 * 
 * Copyright (c) 2018 by Andrew Felsher
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.imperio;

import java.io.PrintStream;
import java.util.ListIterator;

/**
 * This {@link HelpGen} builds the application help text in the default manner,
 * roughly imitating a man page.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public class HelpGenDefault extends HelpGen {

    private enum CharState {
        CONCAT,
        LINE_BUILD,
        LINE_PRINT,
        NUM_BUILD,
        SKIP_SPACE,
        TOKEN_CONCAT,
        WORD_BUILD;
    }

    /**
     * @since 1.0.0
     */
    public HelpGenDefault() {
        super();
    }

    /**
     * Print the full help text for the given Imperio app to the given print
     * stream.
     * 
     * @param ps
     *            destination print stream for the help text
     * @param impApp
     *            Imperio application instance
     * 
     * @throws ImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public void printHelp(PrintStream ps, ImperioApp impApp)
            throws ImperioException {
        String indent = "        ";
        int indentLen = indent.length();

        String usage = impApp.getUsageString();
        if (usage != null) {
            ps.println(usage);
        }

        /*
         * Document the options
         */
        ListIterator<OptionIndex> idxIter = impApp.getOptionIndeces();
        ps.println("DESCRIPTION");
        ps.println();

        if (impApp.description != null) {
            wrap(ps, impApp, indent, indentLen, impApp.description);
            ps.println();
        }
        while (idxIter.hasNext()) {
            OptionIndex index = idxIter.next();

            Option opt = impApp.getOption(index);

            ps.printf("%s", indent);
            if (opt.character != null) {
                ps.printf("-%c", opt.character);
                if (opt.name != null) {
                    ps.print(", ");
                }
            }
            if (opt.name != null) {
                ps.printf("--%s", opt.name);
            }
            if (opt.type.archetype == OptionArchetype.VALUE) {
                ps.printf(" <%s>", opt.valPlaceholder);
            }
            if (opt.deprecated) {
                ps.printf(" (DEPRECATED)");
            }
            if (opt.required) {
                ps.printf(" (REQUIRED)");
            }
            if (opt.description != null) {
                ps.println();
                wrap(ps, impApp, indent + indent, 2 * indentLen, opt.description);
            }
            try {
                if ((opt.type == OptionType.FLAG)
                        && (opt.getDefaultValue() != null)
                        && (opt.getDefaultValueBoolean())) {
                    ps.printf(" (default)");
                }
            } catch (ImperioException e) {
                impApp.errorHandler.err(ErrorType.IMPERIO, impApp,
                        new InternalImperioException(e));
            }
            ps.println();
            ps.println();
        }

        /*
         * Document the example commands
         */
        for (int i = 0; i < impApp.exampleCount(); i++) {
            ExampleCommand cmd = null;
            String[] eArgs = null;

            if (i == 0) {
                ps.println("EXAMPLES");
            } else {
                ps.println();
            }

            cmd = impApp.getExample(i);
            eArgs = cmd.args;

            if (cmd.description != null) {
                wrap(ps, impApp, indent, indentLen, cmd.description);
                ps.print(indent);
            }

            /*
             * Print the example invocation.
             */
            ps.print(indent + impApp.invocation);
            for (int a = 0; a < eArgs.length; a++) {
                ps.print(" " + eArgs[a]);
            }
            ps.println();

            if (i == impApp.exampleCount() - 1) {
                ps.println();
            }
        }

        if (impApp.helpAddenda != null) {
            ps.println(impApp.helpAddenda);
            ps.println();
        }

        if (impApp.author != null) {
            ps.println("AUTHOR");
            wrap(ps, impApp, indent, indentLen, "Written by " + impApp.author + ".");
            ps.println();
            ps.println();
        }

        if (impApp.copyright != null) {
            ps.println("COPYRIGHT");
            wrap(ps, impApp, indent, indentLen, impApp.copyright);
            ps.println();
            ps.println();
        }

        if (impApp.seeAlso != null) {
            ps.println("SEE ALSO");
            wrap(ps, impApp, indent, indentLen, impApp.seeAlso);
            ps.println();
            ps.println();
        }
    }

    private void wrap(PrintStream ps, ImperioApp impApp, String indent,
            int indentLen, String str) throws InternalImperioException {
        int wrapWidth = 80 -indentLen;
        
        if (str.length() <= wrapWidth / 2) {
            ps.print(indent + str);
            return;
        }

        CharState state = CharState.LINE_BUILD;
        String token = null;
        String line = null;
        int tokenLen = 0;
        int lineLen = 0;
        int tabLen = 8;
        token = "";
        tokenLen = 0;
        line = "";
        lineLen = 0;
        for (int i = 0; (str != null) && i < str.length(); i++) {
            char ch = str.charAt(i);
            switch (state) {
            case CONCAT:
                if (wrapWidth <= lineLen + tokenLen && lineLen > 0) {
                    state = CharState.LINE_PRINT;
                } else {
                    line = line + token;
                    lineLen += tokenLen;
                    token = "";
                    tokenLen = 0;
                    state = CharState.LINE_BUILD;
                }
                i--;
                break;
            case LINE_BUILD:
                if (token.length() > 0) {
                    state = CharState.TOKEN_CONCAT;
                    i--;
                } else if ((ch >= '!') && (ch <= '~') && (ch != '.')
                        && (ch != '-')) {
                    if (ch >= '0' && ch <= '9') {
                        state = CharState.NUM_BUILD;
                    } else {
                        state = CharState.WORD_BUILD;
                    }
                    token = "" + ch;
                    tokenLen = 1;
                } else {
                    switch (ch) {
                    case '\t':
                        token = token + ch;
                        tokenLen += tabLen;
                        state = CharState.CONCAT;
                        break;
                    case ' ':
                        token = token + ch;
                        tokenLen++;
                        state = CharState.CONCAT;
                        break;
                    case 0x0B: // Vertical tab
                        token = token + ch;
                        state = CharState.CONCAT;
                        break;
                    case '-':
                    case '.':
                        token = token + ch;
                        tokenLen++;
                        state = CharState.TOKEN_CONCAT;
                        break;
                    case '\n':
                        state = CharState.LINE_PRINT;
                        break;
                    default:
                        // Skip unprintables
                        break;
                    }
                }
                break;
            case LINE_PRINT:
                ps.println(indent + line);
                line = "";
                lineLen = 0;
                state = CharState.SKIP_SPACE;
                i--;
                break;
            case NUM_BUILD:
                if ((ch >= '!') && (ch <= '~')) {
                    token = token + ch;
                    tokenLen++;
                } else {
                    state = CharState.TOKEN_CONCAT;
                    i--;
                }
                break;
            case SKIP_SPACE:
                state = CharState.LINE_BUILD;
                if (token.length() > 0) {
                    if (token.charAt(0) == ' ') {
                        token = token.substring(1);
                    }
                    i--;
                } else if (ch != ' ') {
                    i--;
                }
                break;
            case WORD_BUILD:
                if ((ch >= '!') && (ch <= '~')) {
                    token = token + ch;
                    tokenLen++;
                    if (ch == '-') {
                        state = CharState.TOKEN_CONCAT;
                    }
                } else {
                    state = CharState.TOKEN_CONCAT;
                    i--;
                }
                break;
            case TOKEN_CONCAT:
                if (lineLen > 0 && wrapWidth <= lineLen + tokenLen) {
                    state = CharState.LINE_PRINT;
                } else {
                    line = line + token;
                    lineLen += tokenLen;
                    token = "";
                    tokenLen = 0;
                    state = CharState.LINE_BUILD;
                }
                i--;
                break;
            default:
                impApp.errorHandler.err(ErrorType.IMPERIO, impApp,
                        "Unknown wrap state: %s", state);
                return;
            }
        }
        if (token.length() > 0) {
            if (line.length() > 0 && wrapWidth < lineLen + tokenLen) {
                ps.println(indent + line);
                line = indent;
                lineLen = indentLen;
            }
            line = line + token;
            lineLen += tokenLen;
            token = "";
            tokenLen = 0;

            ps.println(indent + line);
            line = "";
            lineLen = 0;
        }
        if (line.length() > 0) {
            ps.println(indent + line);
            line = "";
            lineLen = 0;
        }
    }

}
