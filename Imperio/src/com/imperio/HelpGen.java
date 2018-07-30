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
 * @author afelsher
 *
 * @since 1.0.0
 */
public abstract class HelpGen {

    /**
     * @since 1.0.0
     */
    public HelpGen() {
    }

    /**
     * @param impApp Imperio application instance
     * 
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public String getHelp(ImperioApp impApp) throws ImperioException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        try {
            printHelp(ps, impApp);

            return baos.toString();
        } finally {
            ps.close();
        }
    }

    /**
     * @param impApp Imperio application instance
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public void printHelp(ImperioApp impApp) throws ImperioException {
        printHelp(System.out, impApp);
    }

    /**
     * @param ps
     * @param impApp Imperio application instance
     * 
     * @throws ImperioException 
     * 
     * @since 1.0.0
     */
    public abstract void printHelp(PrintStream ps, ImperioApp impApp)
            throws ImperioException;

}
