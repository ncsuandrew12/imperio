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
 * This class enumerates the possible option types. Different option types
 * require different behavior when parsing the associated options.
 * 
 * @author afelsher
 * 
 * @since 1.0.0
 */
public enum OptionType {
    /**
     * <p>
     * Requires an argument to be provided along with the option.
     * </p>
     * 
     * <p>
     * The option's value is set to the given argument.
     * </p>
     * 
     * @since 1.0.0
     */
    ARG(OptionArchetype.VALUE),
    /**
     * <p>
     * Requires an argument to be provided along with the option.
     * </p>
     * 
     * <p>
     * Imperio doesn't do anything with this value other than pass it along to
     * the callback function.
     * </p>
     * 
     * @since 1.0.0
     */
    CUSTOM_ARG(OptionArchetype.VALUE),
    /**
     * <p>
     * No argument may be provided.
     * </p>
     * 
     * <p>
     * Imperio doesn't do anything regarding the value of the option. It merely
     * passes {@code true} as the value to the callback function.
     * </p>
     * 
     * @since 1.0.0
     */
    CUSTOM_FLAG(OptionArchetype.NO_VALUE),
    /**
     * <p>
     * No argument may be provided.
     * </p>
     * 
     * <p>
     * The option's value is decremented by 1.
     * </p>
     * 
     * @since 1.0.0
     */
    DECREMENTER(OptionArchetype.NO_VALUE),
    /**
     * <p>
     * Requires an argument to be provided along with the option.
     * </p>
     * 
     * <p>
     * The option's value is set to a {@link java.io.File} object based on the
     * provided argument.
     * </p>
     * 
     * @since 1.0.0
     */
    FILE(OptionArchetype.VALUE),
    /**
     * <p>
     * No argument may be provided.
     * </p>
     * 
     * <p>
     * The option's value is set to the opposite of the default (boolean) value.
     * </p>
     * 
     * @since 1.0.0
     */
    FLAG(OptionArchetype.NO_VALUE),
    /**
     * <p>
     * No argument may be provided.
     * </p>
     * 
     * <p>
     * The option's value is incremented by 1.
     * </p>
     * 
     * @since 1.0.0
     */
    INCREMENTER(OptionArchetype.NO_VALUE);

    public final OptionArchetype archetype;

    /**
     * @param archetype
     *            the option archetype
     * 
     * @since 1.0.0
     */
    OptionType(OptionArchetype archetype) {
        this.archetype = archetype;
    }
}
