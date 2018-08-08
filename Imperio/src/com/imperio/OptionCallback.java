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
 * This class is used as a container for the callback function used by
 * {@link ImperioApp}.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public interface OptionCallback {

    /**
     * When an option has been processed, this function will be called if the
     * option's callback object has been set.
     * 
     * @param impApp
     *            Imperio application instance
     * @param opt
     *            the last option that was processed
     * @param oldVal
     *            the value of the provided option before processing the latest
     *            instance of it
     * @param val
     *            If the option takes an argument, this is the value of the
     *            argument of the most recent instance of the option. If the
     *            option does not take an argument, this is the new value of the
     *            option after processing the most recent instance of the
     *            option.
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public void callback(ImperioApp impApp, Option opt, Object oldVal,
            Object val) throws ImperioException;

}
