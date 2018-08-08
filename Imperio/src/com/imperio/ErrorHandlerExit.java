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
 * This {@link ErrorHandler} behaves the same as {@link ErrorHandlerPrint},
 * except that after logging error messages, it terminates the program using the
 * provided {@link ErrorType#code}.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public class ErrorHandlerExit extends ErrorHandlerPrint {

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp, String format,
            Object... args) throws InternalImperioException {
        super.err(err, impApp, format, args);
        System.exit(err.code);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp, InternalImperioException ex)
            throws InternalImperioException {
        super.err(err, impApp, ex);
        System.exit(err.code);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public void err(ErrorType err, ImperioApp impApp, InternalImperioException ex,
            String format, Object... args) throws InternalImperioException {
        super.err(err, impApp, ex, format, args);
        System.exit(err.code);
    }

}
