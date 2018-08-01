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
public enum OptionType {
    /**
     * @since 1.0.0
     */
    ARG(OptionArchetype.VALUE),
    /**
     * @since 1.0.0
     */
    CUSTOM_ARG(OptionArchetype.VALUE),
    /**
     * @since 1.0.0
     */
    CUSTOM_FLAG(OptionArchetype.NO_VALUE),
    /**
     * @since 1.0.0
     */
    DECREMENTER(OptionArchetype.NO_VALUE),
    /**
     * @since 1.0.0
     */
    FILE(OptionArchetype.VALUE),
    /**
     * @since 1.0.0
     */
    FLAG(OptionArchetype.NO_VALUE),
    /**
     * @since 1.0.0
     */
    INCREMENTER(OptionArchetype.NO_VALUE);

    public final OptionArchetype archetype;

    OptionType(OptionArchetype archetype) {
        this.archetype = archetype;
    }
}
