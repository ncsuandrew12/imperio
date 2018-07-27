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
public class OptionSpec {

    public final static String DEFAULT_ARG_VAL_DESCRPTION = "arg";
    public final static boolean DEFAULT_AUTO = false;
    public final static OptionCallback DEFAULT_CALLBACK = null;
    public final static Character DEFAULT_CHARACTER = null;
    public final static Object DEFAULT_DEFAULT_VALUE = null;
    public final static Boolean DEFAULT_DEFAULT_VALUE_FLAG = false;
    public final static Integer DEFAULT_DEFAULT_VALUE_INCR = 0;
    public final static boolean DEFAULT_DEPRECATED = false;
    public final static String DEFAULT_DESCRIPTION = null;
    public final static Option DEFAULT_LINK_OPTION = null;
    public final static String DEFAULT_NAME = null;
    public final static OptionType DEFAULT_OPTION_TYPE = OptionType.FLAG;
    public final static boolean DEFAULT_REQUIRED = false;
    public final static String DEFAULT_VAL_PLACEHOLDER = null;

    public boolean auto = DEFAULT_AUTO;
    public OptionCallback callback = DEFAULT_CALLBACK;
    public Character character = DEFAULT_CHARACTER;
    public Object defaultValue = DEFAULT_DEFAULT_VALUE;
    public boolean deprecated = DEFAULT_DEPRECATED;
    public String description = DEFAULT_DESCRIPTION;
    public Option linkOption = DEFAULT_LINK_OPTION;
    public String name = DEFAULT_NAME;
    public boolean required = DEFAULT_REQUIRED;
    public OptionType type = DEFAULT_OPTION_TYPE;
    public String valPlaceholder = DEFAULT_VAL_PLACEHOLDER;

    /**
     * @since 1.0.0
     */
    public OptionSpec() {
    }

    /**
     * @param spec
     * 
     * @since 1.0.0
     */
    public OptionSpec(OptionSpec spec) {
        this.auto = spec.auto;
        this.required = spec.required;
        this.deprecated = spec.deprecated;
        this.type = spec.type;
        this.name = spec.name;
        this.character = spec.character;
        this.description = spec.description;
        this.valPlaceholder = spec.valPlaceholder;
        this.linkOption = spec.linkOption;
        this.callback = spec.callback;
        this.defaultValue = spec.defaultValue;
    }

}
