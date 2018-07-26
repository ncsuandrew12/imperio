package com.imperio.examples.echo.p01barebones;

import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.Option;
import com.imperio.OptionException;
import com.imperio.OptionSpec;

public class Echo {

    public static void main(String[] args) throws OptionException {
        OptionSpec optSpec = new OptionSpec();

        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.invocation = "echo";
        ImperioApp impApp = ImperioApp.generate(iaSpec);
        optSpec.name = "no-newline";
        impApp.addOption(Option.generate(optSpec));
        optSpec.name = "escape";
        impApp.addOption(Option.generate(optSpec));
        optSpec.name = "no-escape";
        impApp.addOption(Option.generate(optSpec));
        optSpec.name = "version";
        impApp.addOption(Option.generate(optSpec));

        impApp.processArgs(args);
    }

}
