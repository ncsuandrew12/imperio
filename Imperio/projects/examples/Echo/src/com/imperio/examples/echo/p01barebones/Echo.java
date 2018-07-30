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
package com.imperio.examples.echo.p01barebones;

import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.Option;
import com.imperio.ImperioException;
import com.imperio.OptionSpec;

public class Echo {

    public static void main(String[] args) throws ImperioException {
        OptionSpec optSpec = new OptionSpec();

        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.invocation = "echo";
        ImperioApp impApp = ImperioApp.generate(iaSpec);
        optSpec.name = "no-newline";
        impApp.addOption(Option.generate(optSpec));
        optSpec.name = "escape";
        impApp.addOption(Option.generate(optSpec));
        optSpec.name = "no-escape";
        impApp.addOption(Option.generate(optSpec));
        optSpec.name = "version";
        impApp.addOption(Option.generate(optSpec));

        impApp.processArgs(args);
    }

}
