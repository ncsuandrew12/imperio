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
package com.imperio.examples.rush;

import com.imperio.ErrorType;
import com.imperio.ImperioApp;
import com.imperio.ImperioException;
import com.imperio.InternalImperioException;

public class ErrorHandler implements com.imperio.ErrorHandler {

    protected ErrorType firstError = null;

    @Override
    public void err(ErrorType err, ImperioApp impApp, String format,
            Object... args) throws InternalImperioException {
        if (firstError == null) {
            firstError = err;
        }
        System.err.println(
                "###############################################################################");
        System.err.println(
                " ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
        System.err.println(
                "###############################################################################");
        if (err != ErrorType.UNKNOWN_OPTION) {
            try {
                System.err.print(impApp.getUsageString());
            } catch (ImperioException e) {
                e.printStackTrace(System.err);
            }
            System.err.println();
            System.err.printf(err.msg);
            System.err.println();
        }
        if (format != null) {
            System.err.printf(format, args);
            System.err.println();
        }
    }

    @Override
    public void err(ErrorType err, ImperioApp impApp, InternalImperioException ex)
            throws InternalImperioException {
        err(err, impApp, ex, null);
    }

    @Override
    public void err(ErrorType err, ImperioApp impApp, InternalImperioException ex,
            String format, Object... args) throws InternalImperioException {
        err(err, impApp, format, args);
        if (ex != null) {
            ex.printStackTrace(System.err);
            System.err.println();
        }
    }

    @Override
    public ErrorType firstError() {
        return firstError;
    }

    @Override
    public void warn(ImperioApp impApp, String format, Object... args) {
        System.out.println(
                "###############################################################################");
        System.out.println(
                "    WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING");
        System.out.println(
                "###############################################################################");
        if (format == null) {
            (new NullPointerException("null format string"))
                    .printStackTrace(System.out);
        } else {
            System.out.printf("Warning: " + format, args);
            System.out.println();
        }
    }

}
