package com.imperio;

public class ErrorHandlerExit extends ErrorHandlerPrint {

    @Override
    public void err(ErrorType err, String usage, String format, Object... args)
            throws OptionException {
        super.err(err, usage, format, args);
        System.exit(err.code);
    }

    @Override
    public void err(ErrorType err, String usage, Throwable t)
            throws OptionException {
        super.err(err, usage, t);
        System.exit(err.code);
    }

    @Override
    public void err(ErrorType err, String usage, Throwable t, String format,
            Object... args) throws OptionException {
        super.err(err, usage, t, format, args);
        System.exit(err.code);
    }

}
