package com.imperio;

public class ErrorHandlerException extends ErrorHandlerPrint {

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
        if (t == null) {
            throw new OptionException(null, err.msg + " ", format, args);
        }
        if ((t instanceof OptionException) && (format == null)) {
            throw (OptionException) t;
        }
        if (format == null) {
            throw new OptionException(t, err.msg);
        }
        throw new OptionException(t, err.msg + " ", format, args);
    }

    @Override
    public void warn(String format, Object... args) {
        super.warn(format, args);
    }

}
