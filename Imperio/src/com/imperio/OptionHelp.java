package com.imperio;

public class OptionHelp extends Option {

    public static final Character DEFAULT_CHAR = 'h';
    public static final String DEFAULT_DESCRIPTION = "Print this help text.";
    public static final String DEFAULT_NAME = "help";

    public static OptionHelp generate() {
        return generate(generateSpec());
    }

    public static OptionHelp generate(OptionSpec spec) {
        spec.auto = true;
        return new OptionHelp(spec);
    }

    public static OptionSpec generateSpec() {
        OptionSpec optSpec = new OptionSpec();
        optSpec.name = DEFAULT_NAME;
        optSpec.character = DEFAULT_CHAR;
        optSpec.type = OptionType.FLAG;
        optSpec.description = DEFAULT_DESCRIPTION;
        return optSpec;
    }

    private OptionHelp(OptionSpec spec) {
        super(spec);
    }

}
