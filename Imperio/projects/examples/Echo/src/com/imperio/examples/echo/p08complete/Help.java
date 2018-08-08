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
package com.imperio.examples.echo.p08complete;

import java.io.PrintStream;

import com.imperio.HelpGen;
import com.imperio.ImperioApp;
import com.imperio.Option;
import com.imperio.ImperioException;
import com.imperio.OptionType;

public class Help extends HelpGen {

    public Help() {
    }

    @Override
    public void printHelp(PrintStream ps, ImperioApp impApp)
            throws ImperioException {
        String indent = "  ";

        ps.print(impApp.getUsageString());
        ps.println(impApp.description);
        ps.println();

        /*
         * Ensure that we print the info for the options in the correct order,
         * regardless of Imperio's internal ordering.
         */
        Option[] opts = new Option[] {
                Parameter.NO_NEWLINE.getOpt(),
                Parameter.BACKSLASHES_ESCAPE.getOpt(),
                Parameter.BACKSLASHES_LITERAL.getOpt(),
                impApp.helpOpt,
                Parameter.VERSION.getOpt() };

        for (int i = 0; i < opts.length; i++) {
            Option opt = opts[i];

            ps.printf("%s", indent);
            String str = null;
            if (opt.character != null) {
                str = String.format("-%c           ", opt.character);
            } else if (opt.name != null) {
                str = String.format("    --%s", opt.name);
            }
            ps.print(str);
            for (int a = str.length(); a < 13; a++) {
                ps.print(" ");
            }
            ps.print("  ");
            ps.print(opt.description);
            if ((opt.type == OptionType.FLAG) && (opt.getDefaultValue() != null)
                    && (opt.getDefaultValueBoolean())) {
                ps.printf(" (default)");
            }
            ps.println();
        }
        ps.println(impApp.helpAddenda);
    }

}
