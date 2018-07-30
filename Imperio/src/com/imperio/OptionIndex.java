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
public class OptionIndex {

    public final Character character;
    public final String name;

    /**
     * @param name
     * @param character
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public OptionIndex(String name, Character character)
            throws ImperioException {
        if (name == null && character == null) {
            throw new ImperioException(new NullPointerException(
                    "name and character cannot both be null"));
        }
        this.name = name;
        this.character = character;
    }

    /**
     * @param character
     * 
     * @return
     * 
     * @throws ImperioException if the given character is null
     * 
     * @since 1.0.0
     */
    public int compareTo(Character character) throws ImperioException {
        return compareTo(new OptionIndex(null, character));
    }

    /**
     * @param optionIndex
     * 
     * @return
     * 
     * @since 1.0.0
     */
    public int compareTo(OptionIndex optionIndex) {
        if (name != null) {
            if (optionIndex.name != null) {
                return name.compareTo(optionIndex.name);
            } else {
                return name
                        .compareTo(String.format("%c", optionIndex.character));
            }
        }
        if (optionIndex.character != null) {
            if (character < optionIndex.character) {
                return -1;
            } else if (character > optionIndex.character) {
                return 1;
            }
            return 0;
        }
        return String.format("%c", character).compareTo(optionIndex.name);
    }

    /**
     * @param name
     * 
     * @return
     * 
     * @throws ImperioException if the given name is null
     * 
     * @since 1.0.0
     */
    public int compareTo(String name) throws ImperioException {
        return compareTo(new OptionIndex(name, null));
    }

    /**
     * @since 1.0.0
     */
    @Override
    public String toString() {
        if (name != null) {
            return name;
        }
        return String.format("%c", character);
    }

}
