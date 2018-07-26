package com.imperio.examples.echo.p02enum;

import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.OptionException;

public class Echo {

    public static void main(String[] args) throws OptionException {

        /*
         * Create a list of options.
         */

        /*
         * Create the Options lib representation of this application
         */
        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.invocation = "echo";
        ImperioApp impApp = ImperioApp.generate(iaSpec);
        {
            Parameter[] parms = Parameter.values();
            for (int i = 0; i < parms.length; i++) {
                impApp.add(parms[i].getOpt());
            }
        }

        impApp.processArgs(args);
    }

}
