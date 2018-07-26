package com.imperio.examples.echo.p08complete;

import java.io.PrintStream;

import com.imperio.HelpGen;
import com.imperio.ImperioApp;
import com.imperio.Option;
import com.imperio.OptionException;
import com.imperio.OptionType;

public class Help extends HelpGen {

    public Help() {
    }

    @Override
    public void printHelp(PrintStream ps, ImperioApp impApp)
            throws OptionException {
        String indent = "  ";

        ps.print(impApp.getUsageString());
        ps.println(impApp.description);
        ps.println();

        /*
         * Ensure that we print the info for the options in the correct order,
         * regardless of Imperio's internal ordering.
         */
        Option[] opts = new Option[] {
                Parameter.NO_NEWLINE.getOpt(),
                Parameter.BACKSLASHES_ESCAPE.getOpt(),
                Parameter.BACKSLASHES_LITERAL.getOpt(),
                impApp.helpOpt,
                Parameter.VERSION.getOpt() };

        for (int i = 0; i < opts.length; i++) {
            Option opt = opts[i];

            ps.printf("%s", indent);
            String str = null;
            if (opt.character != null) {
                str = String.format("-%c           ", opt.character);
            } else if (opt.name != null) {
                str = String.format("    --%s", opt.name);
            }
            ps.print(str);
            for (int a = str.length(); a < 13; a++) {
                ps.print(" ");
            }
            ps.print("  ");
            ps.print(opt.description);
            if ((opt.type == OptionType.FLAG) && (opt.defaultValue != null)
                    && ((boolean) opt.defaultValue)) {
                ps.printf(" (default)");
            }
            ps.println();
        }
        ps.println(impApp.helpAddenda);
    }

}
