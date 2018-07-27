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
package com.imperio;

/**
 * @author afelsher
 *
 * @since 1.0.0
 */
public class OptionHelp extends Option {

    public static final Character DEFAULT_CHAR = 'h';
    public static final String DEFAULT_DESCRIPTION = "Print this help text.";
    public static final String DEFAULT_NAME = "help";

    /**
     * @return
     * 
     * @since 1.0.0
     */
    public static OptionHelp generate() {
        return generate(generateSpec());
    }

    /**
     * @param spec
     * 
     * @return
     * 
     * @since 1.0.0
     */
    public static OptionHelp generate(OptionSpec spec) {
        spec.auto = true;
        return new OptionHelp(spec);
    }

    /**
     * @return
     * 
     * @since 1.0.0
     */
    public static OptionSpec generateSpec() {
        OptionSpec optSpec = new OptionSpec();
        optSpec.name = DEFAULT_NAME;
        optSpec.character = DEFAULT_CHAR;
        optSpec.type = OptionType.FLAG;
        optSpec.description = DEFAULT_DESCRIPTION;
        return optSpec;
    }

    /**
     * @param spec
     * 
     * @since 1.0.0
     */
    private OptionHelp(OptionSpec spec) {
        super(spec);
    }

}
