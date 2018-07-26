package com.imperio.examples.echo.p05linkedflags;

import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.OptionException;
import com.imperio.OptionHelp;
import com.imperio.OptionSpec;
import com.imperio.StringArrContainer;

public class Echo {

    public static void main(String[] args) throws OptionException {

        OptionSpec optSpec = OptionHelp.generateSpec();
        optSpec.character = null;
        optSpec.description = "display this help and exit";

        /*
         * Create the Options lib representation of this application
         */
        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.helpOpt = OptionHelp.generate(optSpec);
        iaSpec.invocation = "echo";
        ImperioApp impApp = ImperioApp.generate(iaSpec);
        {
            Parameter[] parms = Parameter.values();
            for (int i = 0; i < parms.length; i++) {
                impApp.addOption(parms[i].getOpt());
            }
        }

        StringArrContainer sac = new StringArrContainer();
        if (!impApp.processArgs(args, sac)) {
            return;
        }
        String[] nonOpts = sac.arr;

        System.out.printf("backslash escapes %s (%s, !%s)",
                ((boolean) Parameter.BACKSLASHES_ESCAPE.getOpt().getValue())
                        ? "enabled"
                        : "disabled",
                Parameter.BACKSLASHES_ESCAPE.getOpt().getValue(),
                Parameter.BACKSLASHES_LITERAL.getOpt().getValue());
        System.out.println();

        for (int i = 0; (nonOpts != null) && (i < nonOpts.length); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(nonOpts[i]);
        }
    }

}
