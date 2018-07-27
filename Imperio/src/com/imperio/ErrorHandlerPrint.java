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
public class ErrorHandlerPrint implements ErrorHandler {

    protected ErrorType firstError = null;

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, String usage, String format, Object... args)
            throws OptionException {
        if (firstError == null) {
            firstError = err;
        }
        System.err.print(usage);
        System.err.println();
        System.err.printf(err.msg);
        System.err.println();
        if (format != null) {
            System.err.printf(format, args);
            System.err.println();
        }
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, String usage, Throwable t)
            throws OptionException {
        err(err, usage, t, null);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, String usage, Throwable t, String format,
            Object... args) throws OptionException {
        err(err, usage, format, args);
        if (t != null) {
            t.printStackTrace(System.err);
            System.err.println();
        }
    }

    /**
     * @since 1.0.0
     */
    @Override
    public ErrorType firstError() {
        return firstError;
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void warn(String usage, String format, Object... args) {
        if (format == null) {
            (new NullPointerException("null format string"))
                    .printStackTrace(System.out);
        } else {
            System.out.printf("Warning: " + format, args);
            System.out.println();
        }
    }

}
