package com.imperio.examples.echo.p02enum;

import com.imperio.Option;
import com.imperio.OptionSpec;

public enum Parameter {

    BACKSLASHES_ESCAPE(),
    BACKSLASHES_LITERAL(),
    NO_NEWLINE(),
    VERSION();

    static {
        Parameter[] parms = Parameter.values();
        for (int i = 0; i < parms.length; i++) {
            OptionSpec optSpec = new OptionSpec();
            switch (parms[i]) {
            case BACKSLASHES_ESCAPE:
                optSpec.name = "escape";
                break;
            case BACKSLASHES_LITERAL:
                optSpec.name = "no-escape";
                break;
            case NO_NEWLINE:
                optSpec.name = "no-newline";
                break;
            case VERSION:
                optSpec.name = "version";
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
