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
 * Exception thrown directly from internal Imperio code, regardless of which
 * {@link ErrorHandler} is used.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public class ImperioException extends Exception {

    private static final long serialVersionUID = 984789956991621020L;

    /**
     * @param format
     *            printf format string
     * @param args
     *            printf args
     * 
     * @since 1.0.0
     */
    public ImperioException(String format, Object... args) {
        this((Throwable) null, (String) null, format, args);
    }

    /**
     * @param t
     *            throwable
     * 
     * @since 1.0.0
     */
    public ImperioException(Throwable t) {
        super(t);
    }

    /**
     * @param t
     *            throwable
     * @param format
     *            printf format string
     * @param args
     *            printf args
     * 
     * @since 1.0.0
     */
    public ImperioException(Throwable t, String format, Object... args) {
        this(t, (String) null, format, args);
    }

    /**
     * @param t
     *            throwable
     * @param prefix
     *            error message prefix
     * @param format
     *            printf format string
     * @param args
     *            printf args
     * 
     * @since 1.0.0
     */
    public ImperioException(Throwable t, String prefix, String format,
            Object... args) {
        super((prefix == null ? "" : prefix) + String.format(format, args), t);
    }

}
