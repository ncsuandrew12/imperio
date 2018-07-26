package com.imperio;

public class Option {

    public static Option generate(OptionSpec spec) {
        spec = new OptionSpec(spec);
        if (spec.defaultValue == null) {
            switch (spec.type) {
            case FLAG:
                if (spec.linkOption != null) {
                    spec.defaultValue = !(boolean) spec.linkOption.defaultValue;
                } else {
                    spec.defaultValue = OptionSpec.DEFAULT_DEFAULT_VALUE_FLAG;
                }
                break;
            case DECREMENTER:
            case INCREMENTER:
                if (spec.linkOption != null) {
                    spec.defaultValue = spec.linkOption.defaultValue;
                } else {
                    spec.defaultValue = OptionSpec.DEFAULT_DEFAULT_VALUE_INCR;
                }
                break;
            case ARG:
            case CUSTOM:
            default:
                break;
            }
        } else {
            switch (spec.type) {
            case FLAG:
                if (!Boolean.class
                        .isAssignableFrom(spec.defaultValue.getClass())) {
                    // TODO Warning
                }
                break;
            case DECREMENTER:
            case INCREMENTER:
                if (!Integer.class
                        .isAssignableFrom(spec.defaultValue.getClass())) {
                    // TODO Warning
                }
                break;
            case ARG:
            case CUSTOM:
            default:
                break;
            }
        }

        if ((spec.type == OptionType.ARG) && (spec.valPlaceholder == null)) {
            spec.valPlaceholder = OptionSpec.DEFAULT_ARG_VAL_DESCRPTION;
        }

        Option opt = new Option(spec);

        if (spec.linkOption != null) {
            spec.linkOption.linkOption = opt;
        }
        return opt;
    }

    public final boolean auto;
    protected final OptionCallback callback;
    public final Character character;
    public final Object defaultValue;
    public final boolean deprecated;
    public final String description;
    Option linkOption = null;
    public final String name;
    protected boolean provided = false;
    public final boolean required;
    public final OptionType type;
    public final String valPlaceholder;
    private Object value = null;

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

    public Object getValue() {
        return value;
    }

    void setValue(Object value) {
        this.value = value;
    }

    public boolean isSet() {
        if (value == null) {
            return false;
        }
        if (!Boolean.class.isAssignableFrom(value.getClass())) {
            return false;
        }
        return (boolean) value;
    }

    public boolean isProvided() {
        return provided;
    }

    public String getInvocation() {
        if (name != null) {
            return "--" + name;
        }
        return "-" + character;
    }

}
