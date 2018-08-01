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
package com.imperio.examples.rush;

import com.imperio.Option;
import com.imperio.ImperioException;
import com.imperio.OptionSpec;
import com.imperio.OptionType;

public enum Parameter {

    ARG_I(),
    FILE_TXT(),
    FLAG_E(),
    FLAG_U(),
    LAST_LINE(),
    QUIET(),
    SECOND_VERSE(),
    VERBOSITY();

    static {
        Parameter[] parms = Parameter.values();
        for (int i = 0; i < parms.length; i++) {
            OptionSpec optSpec = new OptionSpec();

            switch (parms[i]) {
            case ARG_I:
                optSpec.character = 'i';
                optSpec.type = OptionType.ARG;
                break;
            case FILE_TXT:
                optSpec.character = 'f';
                optSpec.name = "file";
                optSpec.type = OptionType.FILE;
                optSpec.valPlaceholder = "output-file";
                break;
            case FLAG_E:
                optSpec.character = 'e';
                break;
            case FLAG_U:
                optSpec.character = 'u';
                break;
            case LAST_LINE:
                optSpec.character = 'l';
                optSpec.description =
                        "Specify last line of output text.";
                optSpec.defaultValue = "The Trees lyrics © Ole Media Management Lp";
                optSpec.name = "last-line";
                optSpec.required = true;
                optSpec.type = OptionType.ARG;
                optSpec.valPlaceholder = "last-line";
                break;
            case QUIET:
                optSpec.character = 'q';
                optSpec.deprecated = true;
                optSpec.description =
                        "Decrement the verbosity counter.";
                optSpec.defaultValue = 1;
                optSpec.name = "quiet";
                optSpec.type = OptionType.DECREMENTER;
                optSpec.callback = new QuietCallback();
                break;
            case VERBOSITY:
                optSpec.character = 'v';
                optSpec.description =
                        "Increment the verbosity counter.";
                optSpec.defaultValue = 1;
                optSpec.linkOption = QUIET.opt;
                optSpec.name = "verbose";
                optSpec.type = OptionType.INCREMENTER;
                optSpec.callback = new VerbCallback();
                break;
            case SECOND_VERSE:
                optSpec.character = '2';
                optSpec.description =
                        "print second verse";
                break;
            default:
                break;
            }
            try {
                parms[i].opt = Option.generate(optSpec);
            } catch (ImperioException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Option opt = null;

    public Option getOpt() {
        return opt;
    }

}
