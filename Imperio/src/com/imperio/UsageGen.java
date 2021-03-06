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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Implementations of this class produce usage strings for a given app.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public abstract class UsageGen {

    /**
     * @param impApp
     *            Imperio application instance
     * 
     * @return the usage string for the given app
     * 
     * @throws ImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public String getUsage(ImperioApp impApp) throws ImperioException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        try {
            printUsage(ps, impApp);

            return baos.toString();
        } finally {
            ps.close();
        }
    }

    /**
     * Print the usage to the console.
     * 
     * @param impApp
     *            Imperio application instance
     * 
     * @throws ImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public void printUsage(ImperioApp impApp) throws ImperioException {
        printUsage(System.out, impApp);
    }

    /**
     * Print the usage to the given stream.
     * 
     * @param ps
     *            print stream
     * @param impApp
     *            Imperio application instance
     * 
     * @throws ImperioException
     *             if an error occurs
     * 
     * @since 1.0.0
     */
    public abstract void printUsage(PrintStream ps, ImperioApp impApp)
            throws ImperioException;

}
