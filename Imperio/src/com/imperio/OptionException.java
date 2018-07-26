package com.imperio;

public class OptionException extends Exception {

    private static final long serialVersionUID = 984789956991621020L;

    public OptionException(String format, Object... args) {
        this((Throwable) null, (String) null, format, args);
    }

    public OptionException(Throwable t) {
        super(t);
    }

    public OptionException(Throwable t, String format, Object... args) {
        this(t, (String) null, format, args);
    }

    public OptionException(Throwable t, String prefix, String format,
            Object... args) {
        super((prefix == null ? "" : prefix) + String.format(format, args), t);
    }

}
