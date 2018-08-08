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
 * <p>
 * Implementations of this class handle errors and warnings which may occur
 * within the Imperio library. Developers may create their own implementations,
 * or use the built-in options.
 * </p>
 * 
 * <p>
 * {@link ErrorHandlerException} prints warnings to {@code System.out} and
 * throws {@link ImperioException}s on errors
 * </p>
 * 
 * <p>
 * {@link ErrorHandlerExit} behaves the same as {@link ErrorHandlerPrint}, but
 * exits with the given error type's code after printing.
 * </p>
 * 
 * <p>
 * {@link ErrorHandlerNull} silently ignores all warnings and errors, but does
 * store the first error like the others.
 * </p>
 * 
 * <p>
 * {@link ErrorHandlerPrint} prints errors to {@code System.err} and prints
 * warnings to {@code System.out}
 * </p>
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public interface ErrorHandler {

    /**
     * @param err
     *            error type
     * @param impApp
     *            Imperio application instance
     * @param format
     *            error message format string
     * @param args
     *            error message printf args
     * 
     * @throws InternalImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public void err(ErrorType err, ImperioApp impApp, String format,
            Object... args) throws InternalImperioException;

    /**
     * @param err
     *            error type
     * @param impApp
     *            Imperio application instance
     * @param ex
     *            exception
     * 
     * @throws InternalImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public void err(ErrorType err, ImperioApp impApp, InternalImperioException ex)
            throws InternalImperioException;

    /**
     * Handle an error. Different sub-classes handle errors differently.
     * 
     * @param err
     *            error type
     * @param impApp
     *            Imperio application instance
     * @param ex
     *            exception
     * @param format
     *            error message format string
     * @param args
     *            error message printf args
     * 
     * @throws InternalImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public void err(ErrorType err, ImperioApp impApp, InternalImperioException ex,
            String format, Object... args) throws InternalImperioException;

    /**
     * @return the first error that occurred
     * 
     * @since 1.0.0
     */
    public ErrorType firstError();

    /**
     * Logs a warning.
     * 
     * @param impApp
     *            Imperio application instance
     * @param format
     *            format string
     * @param args
     *            error message printf args
     * 
     * @since 1.0.0
     */
    public void warn(ImperioApp impApp, String format, Object... args);

}
