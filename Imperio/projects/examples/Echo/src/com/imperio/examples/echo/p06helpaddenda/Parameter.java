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
package com.imperio.examples.echo.p06helpaddenda;

import com.imperio.Option;
import com.imperio.OptionException;
import com.imperio.OptionSpec;

public enum Parameter {

    BACKSLASHES_ESCAPE(),
    BACKSLASHES_LITERAL(),
    NO_NEWLINE(),
    VERSION();

    static {
        Parameter[] parms = Parameter.values();
        for (int i = 0; i < parms.length; i++) {
            OptionSpec optSpec = new OptionSpec();

            switch (parms[i]) {
            case BACKSLASHES_ESCAPE:
                optSpec.character = 'e';
                optSpec.name = "escape";
                optSpec.description =
                        "enable interpretation of backslash escapes";
                break;
            case BACKSLASHES_LITERAL:
                optSpec.character = 'E';
                optSpec.name = "no-escape";
                optSpec.description =
                        "disable interpretation of backslash escapes";
                optSpec.linkOption = BACKSLASHES_ESCAPE.opt;
                break;
            case NO_NEWLINE:
                optSpec.character = 'n';
                optSpec.name = "no-newline";
                optSpec.description = "do not output the trailing newline";
                break;
            case VERSION:
                optSpec.name = "version";
                optSpec.description = "output version information and exit";
                break;
            default:
                break;
            }
            try {
                parms[i].opt = Option.generate(optSpec);
            } catch (OptionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Option opt = null;

    public Option getOpt() {
        return opt;
    }

}
