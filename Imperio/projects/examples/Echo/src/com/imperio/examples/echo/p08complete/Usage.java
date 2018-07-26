package com.imperio.examples.echo.p08complete;

import java.io.PrintStream;

import com.imperio.ImperioApp;
import com.imperio.OptionException;
import com.imperio.UsageGen;

public class Usage extends UsageGen {

    @Override
    public void printUsage(PrintStream ps, ImperioApp impApp)
            throws OptionException {

        ps.println("Usage: " + impApp.invocation + " [SHORT-OPTION]... [STRING]...");
        ps.println("  or:  " + impApp.invocation + " LONG-OPTION");

    }

}
