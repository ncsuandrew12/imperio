package com.imperio.examples.echo.p06helpaddenda;

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
                optSpec.character = 'e';
                optSpec.name = "escape";
                optSpec.description =
                        "enable interpretation of backslash escapes";
                break;
            case BACKSLASHES_LITERAL:
                optSpec.character = 'E';
                optSpec.name = "no-escape";
                optSpec.description =
                        "disable interpretation of backslash escapes";
                optSpec.linkOption = BACKSLASHES_ESCAPE.opt;
                break;
            case NO_NEWLINE:
                optSpec.character = 'n';
                optSpec.name = "no-newline";
                optSpec.description = "do not output the trailing newline";
                break;
            case VERSION:
                optSpec.name = "version";
                optSpec.description = "output version information and exit";
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
