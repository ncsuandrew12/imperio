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

/**
 * @author afelsher
 *
 * @since 1.0.0
 */
public class ImperioAppSpec {

    public static final String DEFAULT_AUTHOR = null;
    public static final String DEFAULT_COPYRIGHT = null;
    public static final String DEFAULT_DESCRIPTION = null;
    public static final ErrorHandler DEFAULT_ERROR_HANDLER =
            new ErrorHandlerException();
    public static final String DEFAULT_HELP_ADDENDA = null;
    public static final HelpGen DEFAULT_HELP_GEN = new HelpGenDefault();
    public static final OptionHelp DEFAULT_HELP_OPT = OptionHelp.generate();
    public static final String DEFAULT_INVOCATION = null;
    public static final ImpLogger DEFAULT_LOGGER = new ImpLoggerNull();
    public static final boolean DEFAULT_NO_MATCH_ERROR = true;
    public static final String DEFAULT_SEE_ALSO = null;
    public static final String DEFAULT_USAGE_ADDENDA = null;
    public static final UsageGen DEFAULT_USAGE_GEN = new UsageGenNames();

    public String author = DEFAULT_AUTHOR;
    public String copyright = DEFAULT_COPYRIGHT;
    public String description = DEFAULT_DESCRIPTION;
    public ErrorHandler errorHandler = DEFAULT_ERROR_HANDLER;
    public String helpAddenda = DEFAULT_HELP_ADDENDA;
    public HelpGen helpGen = DEFAULT_HELP_GEN;
    public OptionHelp helpOpt = DEFAULT_HELP_OPT;
    public String invocation;
    public ImpLogger logger = DEFAULT_LOGGER;
    public boolean noMatchError = DEFAULT_NO_MATCH_ERROR;
    public String seeAlso = DEFAULT_SEE_ALSO;
    public String usageAddenda = DEFAULT_USAGE_ADDENDA;
    public UsageGen usageGen = DEFAULT_USAGE_GEN;

    /**
     * @throws OptionException
     * 
     * @since 1.0.0
     */
    public ImperioAppSpec() throws OptionException {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        String cn = ste[ste.length - 1].getClassName();
        int index = cn.lastIndexOf(".");
        if (index > 0) {
            index++;
        } else {
            index = 0;
        }
        invocation = cn.substring(index);
    }

    /**
     * @param spec
     * 
     * @throws OptionException
     * 
     * @since 1.0.0
     */
    public ImperioAppSpec(ImperioAppSpec spec) throws OptionException {
        this.author = spec.author;
        this.copyright = spec.copyright;
        this.description = spec.description;
        this.errorHandler = spec.errorHandler;
        this.helpAddenda = spec.helpAddenda;
        this.helpGen = spec.helpGen;
        this.helpOpt = spec.helpOpt;
        this.invocation = spec.invocation;
        this.logger = spec.logger;
        this.noMatchError = spec.noMatchError;
        this.seeAlso = spec.seeAlso;
        this.usageAddenda = spec.usageAddenda;
        this.usageGen = spec.usageGen;
    }

}
