package com.imperio.examples.rush;

import com.imperio.ErrorType;
import com.imperio.OptionException;

public class ErrorHandler implements com.imperio.ErrorHandler {

    protected ErrorType firstError = null;

    @Override
    public void err(ErrorType err, String usage, String format, Object... args)
            throws OptionException {
        if (firstError == null) {
            firstError = err;
        }
        System.err.println(
                "###############################################################################");
        System.err.println(
                " ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
        System.err.println(
                "###############################################################################");
        if (err != ErrorType.UNKNOWN_OPTION) {
            System.err.print(usage);
            System.err.println();
            System.err.printf(err.msg);
            System.err.println();
        }
        if (format != null) {
            System.err.printf(format, args);
            System.err.println();
        }
    }

    @Override
    public void err(ErrorType err, String usage, Throwable t)
            throws OptionException {
        err(err, usage, t, null);
    }

    @Override
    public void err(ErrorType err, String usage, Throwable t, String format,
            Object... args) throws OptionException {
        err(err, usage, format, args);
        if (t != null) {
            t.printStackTrace(System.err);
            System.err.println();
        }
    }

    @Override
    public ErrorType firstError() {
        return firstError;
    }

    @Override
    public void warn(String format, Object... args) {
        System.out.println(
                "###############################################################################");
        System.out.println(
                "    WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING");
        System.out.println(
                "###############################################################################");
        if (format == null) {
            (new NullPointerException("null format string"))
                    .printStackTrace(System.out);
        } else {
            System.out.printf("Warning: " + format, args);
            System.out.println();
        }
    }

}
