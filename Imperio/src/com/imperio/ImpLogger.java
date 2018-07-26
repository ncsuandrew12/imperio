package com.imperio;

public interface ImpLogger {

    public void log(String format, Object... args);

    public void log(Throwable t);

    public void log(Throwable t, String format, Object... args);

}
