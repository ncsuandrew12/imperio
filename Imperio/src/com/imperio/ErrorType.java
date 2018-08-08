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
 * Instances of this class represent a category of errors that may occur. In
 * addition to the predefined types statically defined here, applications may
 * define their own types.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public class ErrorType {

    public static final ErrorType GENERIC = new ErrorType(1, "Generic error.");
    public static final ErrorType IMPERIO =
            new ErrorType(0x100, "Error in Imperio library.");
    public static final ErrorType INVALID_ARG =
            new ErrorType(0x101, "Invalid option argument.");
    public static final ErrorType INVALID_OPTION =
            new ErrorType(0x102, "Invalid option.");
    public static final ErrorType MISSING_ARG = new ErrorType(0x103,
            "Missing argument for " + OptionArchetype.VALUE + " option.");
    public static final ErrorType MISSING_REQ_OPTION =
            new ErrorType(0x104, "Missing required option.");
    public static final ErrorType OPTION_PARSING =
            new ErrorType(0x105, "Unable to parse option.");
    public static final ErrorType UNKNOWN_OPTION =
            new ErrorType(0x106, "Unknown option.");

    public final int code;
    public final String description;
    public final String msg;

    /**
     * @param code
     *            error code
     * @param description
     *            error description
     * 
     * @since 1.0.0
     */
    public ErrorType(int code, String description) {
        this.code = code;
        this.description = description;
        this.msg =
                String.format("Error 0x%02x=%d: %s", code, code, description);
    }

}
