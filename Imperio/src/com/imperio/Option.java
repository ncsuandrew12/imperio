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

import java.io.File;

/**
 * Instances of this class represent a command-line option and contain all the
 * necessary information for parsing it.
 * 
 * @author afelsher
 *
 * @since 1.0.0
 */
public class Option {

    /**
     * Checks that the given value is an acceptable data type for the given
     * option type.
     * 
     * @param optType
     *            option type
     * @param value
     *            value to check
     * 
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    static Object checkValue(OptionType optType, Object value) throws ImperioException {
        if (value != null) {
            switch (optType) {
            case ARG:
                if (!(value instanceof String)) {
                    wrongValueType(value.getClass(), String.class);
                }
                break;
            case FILE:
                if (!(value instanceof File)) {
                    wrongValueType(value.getClass(), File.class);
                }
                break;
            case FLAG:
                if (!(value instanceof Boolean)) {
                    wrongValueType(value.getClass(), Boolean.class);
                }
                break;
            case DECREMENTER:
            case INCREMENTER:
                if (!(value instanceof Integer) && !(value instanceof Long)) {
                    wrongValueType(value.getClass(), Integer.class);
                }
                break;
            case CUSTOM_ARG:
            case CUSTOM_FLAG:
            default:
                break;
            }
        }
        return value;
    }

    /**
     * @param spec
     * 
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public static Option generate(OptionSpec spec) throws ImperioException {
        spec = new OptionSpec(spec);
        if (spec.defaultValue == null) {
            switch (spec.type) {
            case FLAG:
                if (spec.linkOption != null) {
                    spec.defaultValue = checkValue(spec.type,
                            !spec.linkOption.getDefaultValueBoolean());
                } else {
                    spec.defaultValue = checkValue(spec.type,
                            OptionSpec.DEFAULT_DEFAULT_VALUE_FLAG);
                }
                break;
            case DECREMENTER:
            case INCREMENTER:
                if (spec.linkOption != null) {
                    spec.defaultValue = checkValue(spec.type,
                            spec.linkOption.getDefaultValueInt());
                } else {
                    spec.defaultValue = checkValue(spec.type,
                            OptionSpec.DEFAULT_DEFAULT_VALUE_FLAG);
                }
                break;
            case ARG:
            case CUSTOM_ARG:
            case CUSTOM_FLAG:
            default:
                break;
            }
        } else {
            spec.defaultValue = checkValue(spec.type, spec.defaultValue);
        }

        if ((spec.type.archetype == OptionArchetype.VALUE)
                && (spec.valPlaceholder == null)) {
            spec.valPlaceholder = OptionSpec.DEFAULT_ARG_VAL_DESCRPTION;
        }

        Option opt = new Option(spec);

        if (spec.linkOption != null) {
            spec.linkOption.linkOption = opt;
        }
        return opt;
    }

    /**
     * @param actualClass
     * @param expectedClass
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    private static void wrongDefaultValueType(Class<?> actualClass,
            Class<?> expectedClass) throws ImperioException {
        ImperioException e = new ImperioException(
                "Default value is a " + actualClass.getSimpleName() + ", not a "
                        + expectedClass.getSimpleName());
        throw e;
    }

    /**
     * @param actualClass
     * @param expectedClass
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    private static void wrongValueType(Class<?> actualClass,
            Class<?> expectedClass) throws ImperioException {
        ImperioException e =
                new ImperioException("Value is a " + actualClass.getSimpleName()
                        + ", not a " + expectedClass.getSimpleName());
        throw e;
    }

    public final boolean auto;
    protected final OptionCallback callback;
    public final Character character;
    private final Object defaultValue;
    public final boolean deprecated;
    public final String description;
    Option linkOption = null;
    public final String name;
    protected boolean provided = false;
    public final boolean required;
    public final OptionType type;
    public final String valPlaceholder;
    private Object value = null;

    /**
     * @param spec
     * 
     * @since 1.0.0
     */
    Option(OptionSpec spec) {
        this.auto = spec.auto;
        this.callback = spec.callback;
        this.character = spec.character;
        this.deprecated = spec.deprecated;
        this.description = spec.description;
        this.name = spec.name;
        this.required = spec.required;
        this.type = spec.type;
        this.value = this.defaultValue = spec.defaultValue;
        this.linkOption = spec.linkOption;
        this.valPlaceholder = spec.valPlaceholder;
    }

    /**
     * @return
     * 
     * @since 1.0.0
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public Boolean getDefaultValueBoolean() throws ImperioException {
        if (defaultValue == null) {
            return null;
        }
        if (defaultValue instanceof Boolean) {
            return (boolean) defaultValue;
        }
        wrongDefaultValueType(value.getClass(), Boolean.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public File getDefaultValueFile() throws ImperioException {
        if (defaultValue == null) {
            return null;
        }
        if (defaultValue instanceof File) {
            return (File) defaultValue;
        }
        if (defaultValue instanceof String) {
            return new File((String) defaultValue);
        }
        wrongDefaultValueType(value.getClass(), File.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public Integer getDefaultValueInt() throws ImperioException {
        if (defaultValue == null) {
            return null;
        }
        if (defaultValue instanceof Integer) {
            return (int) defaultValue;
        }
        wrongDefaultValueType(value.getClass(), Integer.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public String getDefaultValueString() throws ImperioException {
        if (defaultValue == null) {
            return null;
        }
        if (defaultValue instanceof String) {
            return (String) defaultValue;
        }
        wrongDefaultValueType(value.getClass(), String.class);
        return null;
    }

    /**
     * @return
     * 
     * @since 1.0.0
     */
    public String getInvocation() {
        if (name != null) {
            return "--" + name;
        }
        return "-" + character;
    }

    /**
     * @return
     * 
     * @since 1.0.0
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public Boolean getValueBoolean() throws ImperioException {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        wrongValueType(value.getClass(), Boolean.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public File getValueFile() throws ImperioException {
        if (value == null) {
            return null;
        }
        if (value instanceof File) {
            return (File) value;
        }
        if (value instanceof String) {
            return new File((String) value);
        }
        wrongValueType(value.getClass(), File.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public Integer getValueInt() throws ImperioException {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (int) value;
        }
        wrongValueType(value.getClass(), Integer.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public String getValueString() throws ImperioException {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        wrongValueType(value.getClass(), String.class);
        return null;
    }

    /**
     * @return
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public boolean isFlagSet() throws ImperioException {
        if (value == null) {
            return false;
        }
        if (type != OptionType.FLAG) {
            return false;
        }
        return getValueBoolean();
    }

    /**
     * @return
     * 
     * @since 1.0.0
     */
    public boolean isProvided() {
        return provided;
    }

    /**
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public void setValue(Object value) throws ImperioException {
        if (value != null) {
            checkValue(this.type, value);
        }
        this.value = value;
    }

}
