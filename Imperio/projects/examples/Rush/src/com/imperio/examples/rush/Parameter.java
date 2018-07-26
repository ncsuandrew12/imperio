package com.imperio.examples.rush;

import com.imperio.Option;
import com.imperio.OptionSpec;
import com.imperio.OptionType;

public enum Parameter {

    ARG_I(),
    FLAG_E(),
    FLAG_U(),
    LAST_LINE(),
    QUIET(),
    SECOND_VERSE(),
    VERBOSITY();

    static {
        Parameter[] parms = Parameter.values();
        for (int i = 0; i < parms.length; i++) {
            OptionSpec optSpec = new OptionSpec();

            switch (parms[i]) {
            case ARG_I:
                optSpec.character = 'i';
                optSpec.type = OptionType.ARG;
                break;
            case FLAG_E:
                optSpec.character = 'e';
                break;
            case FLAG_U:
                optSpec.character = 'u';
                break;
            case LAST_LINE:
                optSpec.character = 'l';
                optSpec.description =
                        "Specify last line of output text.";
                optSpec.defaultValue = "The Trees lyrics © Ole Media Management Lp";
                optSpec.name = "last-line";
                optSpec.required = true;
                optSpec.type = OptionType.ARG;
                optSpec.valPlaceholder = "last-line";
                break;
            case QUIET:
                optSpec.character = 'q';
                optSpec.deprecated = true;
                optSpec.description =
                        "Decrement the verbosity counter.";
                optSpec.defaultValue = 1;
                optSpec.name = "quiet";
                optSpec.type = OptionType.DECREMENTER;
                optSpec.callback = new QuietCallback();
                break;
            case VERBOSITY:
                optSpec.character = 'v';
                optSpec.description =
                        "Increment the verbosity counter.";
                optSpec.defaultValue = 1;
                optSpec.linkOption = QUIET.opt;
                optSpec.name = "verbose";
                optSpec.type = OptionType.INCREMENTER;
                optSpec.callback = new VerbCallback();
                break;
            case SECOND_VERSE:
                optSpec.character = '2';
                optSpec.description =
                        "print second verse";
                break;
            default:
                break;
            }
            parms[i].opt = Option.generate(optSpec);
        }
    }

    private Option opt = null;

    public Option getOpt() {
        return opt;
    }

}
