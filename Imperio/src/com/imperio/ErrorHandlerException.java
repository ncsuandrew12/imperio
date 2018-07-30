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
public class ErrorHandlerException extends ErrorHandlerPrint {

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp, String format, Object... args)
            throws OptionException {
        err(err, impApp, null, format, args);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp, Throwable t)
            throws OptionException {
        err(err, impApp, t, null);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp, Throwable t, String format,
            Object... args) throws OptionException {
        if (firstError == null) {
            firstError = err;
        }
        if (t == null) {
            throw new OptionException(null, err.msg + " ", format, args);
        }
        if ((t instanceof OptionException) && (format == null)) {
            throw (OptionException) t;
        }
        if (format == null) {
            throw new OptionException(t, err.msg);
        }
        throw new OptionException(t, err.msg + " ", format, args);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void warn(ImperioApp impApp, String format, Object... args) {
        super.warn(impApp, format, args);
    }

}
