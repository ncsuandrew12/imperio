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
package com.imperio.examples.echo.p07help;

import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.OptionException;
import com.imperio.OptionHelp;
import com.imperio.OptionSpec;
import com.imperio.StringArrContainer;

public class Echo {

    public static void main(String[] args) throws OptionException {

        OptionSpec optSpec = OptionHelp.generateSpec();
        optSpec.character = null;
        optSpec.description = "display this help and exit";

        /*
         * Create the Options lib representation of this application
         */
        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.description = "Echo the STRING(s) to standard output.";
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

        System.out.printf("backslash escapes %s (%s, !%s)",
                (Parameter.BACKSLASHES_ESCAPE.getOpt().isFlagSet())
                        ? "enabled"
                        : "disabled",
                Parameter.BACKSLASHES_ESCAPE.getOpt().isFlagSet(),
                Parameter.BACKSLASHES_LITERAL.getOpt().isFlagSet());
        System.out.println();

        for (int i = 0; (nonOpts != null) && (i < nonOpts.length); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(nonOpts[i]);
        }
    }

}
