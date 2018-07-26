package com.imperio;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class UsageGen {

    public String getUsage(ImperioApp impApp) throws OptionException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        try {
            printUsage(ps, impApp);

            return baos.toString();
        } finally {
            ps.close();
        }
    }

    public void printUsage(ImperioApp impApp) throws OptionException {
        printUsage(System.out, impApp);
    }

    public abstract void printUsage(PrintStream ps, ImperioApp impApp)
            throws OptionException;

}
