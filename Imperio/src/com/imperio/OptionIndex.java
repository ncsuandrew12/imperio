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
 * Instances of this class are used as a unique identifier for any option in a
 * given Imperio app. Either {@link #character} or {@link #name} may be null,
 * but not both.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public class OptionIndex {

    public final Character character;
    public final String name;

    /**
     * @param name
     *            the option's long-form name
     * @param character
     *            the option's character
     * 
     * @throws ImperioException
     *             if an error occurs
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
     * Compare this index lexicographically to the given character.
     * 
     * @param character
     *            the character to compare to this index's character
     * 
     * @return integer indicating the lexicographic order of this index compared
     *         to the provided character
     * 
     * @throws ImperioException
     *             if the given character is null
     * 
     * @see #compareTo(OptionIndex)
     * 
     * @since 1.0.0
     */
    public int compareTo(Character character) throws ImperioException {
        return compareTo(new OptionIndex(null, character));
    }

    /**
     * <p>
     * Compare this index lexicographically to the given index.
     * </p>
     * <p>
     * If both indices have names, these names are compared.
     * </p>
     * <p>
     * If this index has a name but the provided index doesn't, this index's
     * name is compared to the provided index's character.
     * </p>
     * <p>
     * If this index doesn't have a name and the provided index does, this
     * index's character is compared to the provided index's name.
     * </p>
     * <p>
     * If neither indices have names, the indices' characers are compared.
     * </p>
     * 
     * @param optionIndex
     *            the option index to compare this index to
     * 
     * @return a negative integer if this index lexicographically precedes the
     *         provided index, 0 if they are lexicographically equivalent, or a
     *         positive integer if this inidex lexicographically follows the
     *         provided index
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
     * Compares this index lexicographically to the given name.
     * 
     * @param name
     *            the long-form name to compare to this index's long-form name
     * 
     * @return integer indicating the lexicographic order of this index compared
     *         to the provided name
     * 
     * @see #compareTo(OptionIndex)
     * 
     * @throws ImperioException
     *             if the given name is null
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
