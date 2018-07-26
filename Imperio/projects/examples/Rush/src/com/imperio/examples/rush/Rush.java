package com.imperio.examples.rush;

import com.imperio.ImpLoggerPrint;
import com.imperio.ImperioApp;
import com.imperio.ImperioAppSpec;
import com.imperio.OptionException;
import com.imperio.OptionHelp;
import com.imperio.OptionSpec;
import com.imperio.UsageGenChars;

public class Rush {

    public static void main(String[] args) throws OptionException {

        OptionSpec optSpec = OptionHelp.generateSpec();
        optSpec.character = 'h';
        optSpec.description = "display this help and exit";

        /*
         * Create the Options lib representation of this application
         */
        ImperioAppSpec iaSpec = new ImperioAppSpec();
        iaSpec.author = "afelsher";
        iaSpec.copyright = "Copyright (c) 2018 by Andrew Felsher.";
        iaSpec.description =
                  "Do stuff with The Trees. Some wrappable text: The Wheel of Time turns, "
                + "and Ages come and pass, leaving memories that become legend. Legend "
                + "fades to myth, and even myth is long forgotten when the Age that gave "
                + "it birth comes again.  In one Age, called the third age by some, an Age "
                + "yet to come, an age long past, a wind rose in the Mountains of Mist. "
                + "The wind was not the beginning. There are neither beginnings or endings "
                + "to the turning of the Wheel of Time. But it was a beginning.    hyphen-"
                + "ated. ............................................................ "
                + "123.45 ...............................................................    "
                + "\n "
                + "   <-3 spaces ................................................. "
                + "123abc.d4 ......................................................... tab"
                +       "\t<-tab ................................................. tab\t"
                +         "<-tab\n"
                + "long_text_abcdefghijklmnopqrstuvwxyz0123456789_abcdefghijklmnopqrstuvwxyz0123456789 "
                + "long_text_ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 "
                + "verical" + ((char) 0x0B)
                +        "tab ....................................................... wrap "
                + "around";
        iaSpec.helpOpt = OptionHelp.generate(optSpec);
        iaSpec.errorHandler = new ErrorHandler();
        iaSpec.logger = new ImpLoggerPrint();
        iaSpec.usageGen = new UsageGenChars();
        ImperioApp impApp = ImperioApp.generate(iaSpec);
        {
            Parameter[] parms = Parameter.values();
            for (int i = 0; i < parms.length; i++) {
                impApp.add(parms[i].getOpt());
            }
        }

        /*
         * Show some example invocations
         */
        impApp.addExample(new String[] {
                Parameter.SECOND_VERSE.getOpt().getInvocation(),
                Parameter.LAST_LINE.getOpt().getInvocation(),
                "Death is preferable to communism.",
                "-vvvvvvqqqqqvvv" });
        impApp.addExample("Showcase error-correcting argument suggestions:", new String[] {
                Parameter.LAST_LINE.getOpt().getInvocation(),
                "blah",
                "-quiet" });

        if (!impApp.processArgs(args)) {
            return;
        }

        System.out.println();
        System.out.println("There is unrest in the forest");
        System.out.println("There is trouble with the trees");
        System.out.println("For the maples want more sunlight");
        System.out.println("And the oaks ignore their pleas");

        if (Parameter.SECOND_VERSE.getOpt().isSet()) {
            System.out.println();
            System.out.println("The trouble with the maples");
            System.out.println("And they're quite convinced they're right");
            System.out.println("They say the oaks are just too lofty");
            System.out.println("And they grab up all the light");
            System.out.println("But the oaks can't help their feelings");
            System.out.println("If they like the way they're made");
            System.out.println("And they wonder why the maples");
            System.out.println("Can't be happy in their shade?");
        }

        System.out.println();
        System.out.println("There is trouble in the forest");
        System.out.println("And the creatures all have fled");
        System.out.println("As the maples scream 'oppression!'");
        System.out.println("And the oaks, just shake their heads");
        System.out.println("So the maples formed a union");
        System.out.println("And demanded equal rights");
        System.out.println("'The oaks are just too greedy");
        System.out.println("We will make them give us light'");
        System.out.println("Now there's no more oak oppression");
        System.out.println("For they passed a noble law");
        System.out.println("And the trees are all kept equal");
        System.out.println("By hatchet,");
        System.out.println("Axe,");
        System.out.println("And saw");

        System.out.println();
        System.out.println(Parameter.LAST_LINE.getOpt().getValue());

        System.out.println();
        System.out.printf("Verbosity: %d\n",
                Parameter.VERBOSITY.getOpt().getValue());
        System.out.println();
    }

}
