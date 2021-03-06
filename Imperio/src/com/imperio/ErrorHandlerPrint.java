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
 * This {@link ErrorHandler} prints any errors to {@code System.err} and any
 * warnings to {@code System.out}.
 * 
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
    public void err(ErrorType err, ImperioApp impApp, String format,
            Object... args) throws InternalImperioException {
        if (firstError == null) {
            firstError = err;
        }
        try {
            System.err.print(impApp.getUsageString());
        } catch (ImperioException e) {
            e.printStackTrace(System.err);
        }
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
    public void err(ErrorType err, ImperioApp impApp,
            InternalImperioException ex) throws InternalImperioException {
        err(err, impApp, ex, null);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp,
            InternalImperioException ex, String format, Object... args)
            throws InternalImperioException {
        err(err, impApp, format, args);
        if (ex != null) {
            ex.printStackTrace(System.err);
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
    public void warn(ImperioApp impApp, String format, Object... args) {
        if (format == null) {
            (new NullPointerException("null format string"))
                    .printStackTrace(System.out);
        } else {
            System.out.printf("Warning: " + format, args);
            System.out.println();
        }
    }

}
