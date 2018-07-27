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

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ListIterator;

/**
 * @author afelsher
 *
 * @since 1.0.0
 */
public class UsageGenChars extends UsageGen {

    /**
     * @since 1.0.0
     */
    @Override
    public void printUsage(PrintStream ps, ImperioApp impApp)
            throws OptionException {
        boolean foundReqFlags = false;
        boolean foundOptFlags = false;
        StringWriter optArgs = null;
        StringWriter optFlags = null;
        StringWriter reqArgs = null;
        StringWriter reqFlags = null;

        optArgs = new StringWriter();
        optFlags = new StringWriter();
        reqArgs = new StringWriter();
        reqFlags = new StringWriter();

        ListIterator<OptionIndex> idxIter = impApp.getOptionIndeces();
        while (idxIter.hasNext()) {
            OptionIndex index = idxIter.next();

            Option arg = impApp.getOption(index);

            if (arg.deprecated) {
                continue;
            }
            if (arg.required) {
                switch (arg.type) {
                case ARG:
                    if (arg.character != null) {
                        reqArgs.write(" -" + arg.character + " <"
                                + arg.valPlaceholder + ">");
                    } else {
                        reqArgs.write(" --" + arg.name + " <"
                                + arg.valPlaceholder + ">");
                    }
                    break;
                case CUSTOM:
                case FLAG:
                case INCREMENTER:
                    // Will probably never happen
                    if (arg.character != null) {
                        if (!foundReqFlags) {
                            foundReqFlags = true;
                            reqFlags.write(" -");
                        }
                        reqFlags.write(arg.character);
                    } else {
                        reqArgs.write(" --" + arg.name);
                    }
                    break;
                default:
                    throw new OptionException(
                            "Unknown option type " + arg.type);
                }
            } else {
                switch (arg.type) {
                case ARG:
                    if (arg.character != null) {
                        optArgs.write(" [-" + arg.character + " <"
                                + arg.valPlaceholder + ">]");
                    } else {
                        optArgs.write(" [--" + arg.name + " <"
                                + arg.valPlaceholder + ">]");
                    }
                    break;
                case CUSTOM:
                case FLAG:
                case INCREMENTER:
                    if (arg.character != null) {
                        if (!foundOptFlags) {
                            foundOptFlags = true;
                            optFlags.write(" [-");
                        }
                        optFlags.write(arg.character);
                    } else {
                        optArgs.write(" [--" + arg.name + "]");
                    }
                    break;
                default:
                    throw new OptionException(
                            "Unknown option type " + arg.type);
                }
            }
        }
        if (foundOptFlags) {
            optFlags.write("]");
        }
        if (foundReqFlags) {
            reqFlags.write("]");
        }

        ps.println();
        ps.print(impApp.invocation);
        ps.print(reqFlags.toString() + reqArgs.toString() + optFlags.toString()
                + optArgs.toString());
        if (impApp.usageAddenda != null) {
            ps.print(impApp.usageAddenda);
        }
        ps.println();
    }

}
