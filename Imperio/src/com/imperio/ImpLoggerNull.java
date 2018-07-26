package com.imperio;

public class ImpLoggerNull implements ImpLogger {

    @Override
    public void log(String format, Object... args) {
    }

    @Override
    public void log(Throwable t) {
    }

    @Override
    public void log(Throwable t, String format, Object... args) {
    }

}
