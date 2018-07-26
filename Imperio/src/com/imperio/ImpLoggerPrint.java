package com.imperio;

public class ImpLoggerPrint implements ImpLogger {

    @Override
    public void log(String format, Object... args) {
        System.out.printf(format, args);
        System.out.println();
    }

    @Override
    public void log(Throwable t) {
        t.printStackTrace(System.out);
    }

    @Override
    public void log(Throwable t, String format, Object... args) {
        log(format, args);
        log(t);
    }

}
