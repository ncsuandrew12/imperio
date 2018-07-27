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
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.imperio.examples.echo.p08complete;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.imperio.ErrorHandlerNull;
import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.OptionException;
import com.imperio.OptionHelp;
import com.imperio.OptionSpec;
import com.imperio.StringArrContainer;
import com.imperio.examples.echo.p07help.Help;
import com.imperio.examples.echo.p07help.Parameter;
import com.imperio.examples.echo.p07help.Usage;

public class Echo {

    public enum EscState {
        ESC, HEX, OCT, PLAIN;
    }

    public static void main(String[] args)
            throws OptionException, UnsupportedEncodingException {
        new Echo().run(args);
    }

    public void run(String[] args)
            throws OptionException, UnsupportedEncodingException {

        OptionSpec optSpec = OptionHelp.generateSpec();
        optSpec.character = 'h';
        optSpec.description = "display this help and exit";

        /*
         * Create the Options lib representation of this application
         */
        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.description = "Echo the STRING(s) to standard output.";
        iaSpec.errorHandler = new ErrorHandlerNull();
        iaSpec.helpAddenda = "\n"
                        + "If -"
                                    + Parameter.BACKSLASHES_ESCAPE.getOpt().character 
                                    + " is in effect, the following sequences are recognized:\n"
                        + "\n"
                        + "  \\\\      backslash\n"
                        + "  \\a      alert (BEL)\n"
                        + "  \\b      backspace\n"
                        + "  \\c      produce no further output\n"
                        + "  \\e      escape\n"
                        + "  \\f      form feed\n"
                        + "  \\n      new line\n"
                        + "  \\r      carriage return\n"
                        + "  \\t      horizontal tab\n"
                        + "  \\v      vertical tab\n"
                        + "  \\0NNN   byte with octal value NNN (1 to 3 digits)\n"
                        + "  \\xHH    byte with hexadecimal value HH (1 to 2 digits)\n"
                        + "\n"
                        + "NOTE: your shell may have its own version of echo, which usually supersedes\n"
                        + "the version described here.  Please refer to your shell's documentation\n"
                        + "for details about the options it supports.\n"
                        + "\n"
                        + "GNU coreutils online help: <http://www.gnu.org/software/coreutils/>\n"
                        + "For complete documentation, run: info coreutils 'echo invocation'";
        iaSpec.helpGen = new Help();
        iaSpec.helpOpt = OptionHelp.generate(optSpec);
        iaSpec.invocation = "/bin/echo";
        iaSpec.usageGen = new Usage();
        ImperioApp impApp = ImperioApp.generate(iaSpec);
        {
            Parameter[] parms = Parameter.values();
            for (int i = 0; i < parms.length; i++) {
                impApp.addOption(parms[i].getOpt());
            }
        }

        StringArrContainer sac = new StringArrContainer();
        if (!impApp.processArgs(args, sac)) {
            return;
        }
        String[] nonOpts = sac.arr;

        if (Parameter.VERSION.getOpt().isFlagSet()) {
            System.out.println("echo (GNU coreutils) 8.22");
            System.out.println(
                    "Copyright (C) 2013 Free Software Foundation, Inc.");
            System.out.println(
                    "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>.");
            System.out.println(
                    "This is free software: you are free to change and redistribute it.");
            System.out.println(
                    "There is NO WARRANTY, to the extent permitted by law.");
            System.out.println();
            System.out.println("Written by Brian Fox and Chet Ramey.");
            System.exit(0);
        }

        boolean println = !Parameter.NO_NEWLINE.getOpt().isFlagSet();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        try {
            EscState state = EscState.PLAIN;
            optloop: for (int noi = 0; (nonOpts != null)
                    && (noi < nonOpts.length); noi++) {
                if (noi > 0) {
                    ps.print(" ");
                }
                if (!Parameter.BACKSLASHES_ESCAPE.getOpt().isFlagSet()) {
                    ps.print(nonOpts[noi]);
                } else {
                    int num = 0x0;
                    int numChars = 0;
                    boolean nonChar = true;
                    state = EscState.PLAIN;
                    charloop: for (int chi = 0; (nonOpts[noi] != null)
                            && (chi < nonOpts[noi].length()); chi++) {
                        char ch = nonOpts[noi].charAt(chi);
                        switch (state) {
                        case PLAIN:
                            if (ch == '\\' && chi < nonOpts[noi].length() - 1) {
                                state = EscState.ESC;
                            } else {
                                ps.print(ch);
                            }
                            break;
                        case ESC:
                            state = EscState.PLAIN;
                            switch(ch) {
                            case 'c':
                                println = false;
                                break optloop;
                            case '0':
                                num = 0x0;
                                numChars = 0;
                                state = EscState.OCT;
                                if (chi == nonOpts[noi].length() - 1) {
                                    continue charloop;
                                }
                                break;
                            case 'x':
                                num = 0x0;
                                numChars = 0;
                                state = EscState.HEX;
                                if (chi == nonOpts[noi].length() - 1) {
                                    ps.print("\\x");
                                    continue charloop;
                                }
                                break;
                            case 'n':
                                ps.print('\n');
                                break;
                            case 'r':
                                ps.print('\r');
                                break;
                            case 't':
                                ps.print('\t'); // ESC
                                break;
                            case 'a':
                                ps.print((char) 0x07); // BEL
                                break;
                            case 'b':
                                ps.print((char) 0x08); // Backspace
                                break;
                            case 'e':
                                ps.print((char) 0x1B); // ESC
                                break;
                            case 'f':
                                ps.print((char) 0xC);  // Form feed
                                break;
                            case 'v':
                                ps.print((char) 0xB);  // Vertical tab
                                break;
                            case '\\':
                            default:
                                ps.print(ch);
                                break;
                            }
                            break;
                        case HEX:
                            nonChar= false;
                            if ((ch >= '0' && ch <= '9')
                                    || (ch >= 'a' && ch <= 'f')
                                    || (ch >= 'A' && ch <= 'F')) {
                                num = (num << 4) | Integer.decode("0x" + ch);
                                numChars++;
                            } else {
                                nonChar = true;
                                chi--; // We need to re-run this character.
                                state = EscState.PLAIN;
                            }
                            if ((numChars == 0) && (nonChar
                                    || (chi == nonOpts[noi].length() - 1))) {
                                ps.print('\\');
                                if (nonChar) {
                                    ps.print('x');
                                }
                            } else if (nonChar || (numChars == 2)
                                    || (chi == nonOpts[noi].length() - 1)) {
                                ps.print(Character.toString((char) num));
                                state = EscState.PLAIN;
                            }
                            break;
                        case OCT:
                            nonChar= false;
                            if ((ch >= '0' && ch <= '7')) {
                                num = (num << 3) | Integer.decode("0" + ch);
                                numChars++;
                            } else {
                                nonChar = true;
                                chi--; // We need to re-run this character.
                                state = EscState.PLAIN;
                            }
                            if ((nonChar && (numChars > 0)) || (numChars == 3)
                                    || (chi == nonOpts[noi].length() - 1)) {
                                ps.print(Character.toString((char) num));
                                state = EscState.PLAIN;
                            }
                            break;
                        default:
                            break;
                        }
                    }
                }
            }
            if (println) {
                ps.println();
            }

            System.out.print(baos.toString());

        } finally {
            ps.close();
        }

    }

}
