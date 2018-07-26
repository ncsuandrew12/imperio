package com.imperio;

public interface ErrorHandler {

    public void err(ErrorType err, String usage, String format, Object... args)
            throws OptionException;

    public void err(ErrorType err, String usage, Throwable t)
            throws OptionException;

    public void err(ErrorType err, String usage, Throwable t, String format,
            Object... args) throws OptionException;

    public ErrorType firstError();

    public void warn(String format, Object... args);

}
