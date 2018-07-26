package com.imperio;

public class ErrorHandlerNull implements ErrorHandler {

    private ErrorType firstError = null;

    @Override
    public void err(ErrorType err, String usage, String format, Object... args)
            throws OptionException {
        err(err, usage, null, format, args);
    }

    @Override
    public void err(ErrorType err, String usage, Throwable t)
            throws OptionException {
        err(err, usage, t, null);
    }

    @Override
    public void err(ErrorType err, String usage, Throwable t, String format,
            Object... args) throws OptionException {
        if (firstError == null) {
            firstError = err;
        }
    }

    @Override
    public ErrorType firstError() {
        return firstError;
    }

    @Override
    public void warn(String format, Object... args) {
    }

}
