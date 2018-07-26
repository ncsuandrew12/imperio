package com.imperio;

public class ImperioAppSpec {

    public static final String DEFAULT_AUTHOR = null;
    public static final String DEFAULT_COPYRIGHT = null;
    public static final String DEFAULT_DESCRIPTION = null;
    public static final ErrorHandler DEFAULT_ERROR_HANDLER =
            new ErrorHandlerException();
    public static final HelpGen DEFAULT_HELP_GEN = new HelpGen();
    public static final String DEFAULT_HELP_ADDENDA = null;
    public static final OptionHelp DEFAULT_HELP_OPT = OptionHelp.generate();
    public static final String DEFAULT_INVOCATION = null;
    public static final ImpLogger DEFAULT_LOGGER = new ImpLoggerNull();
    public static final boolean DEFAULT_NO_MATCH_ERROR = true;
    public static final String DEFAULT_SEE_ALSO = null;
    public static final String DEFAULT_USAGE_ADDENDA = null;
    public static final UsageGen DEFAULT_USAGE_GEN = new UsageGenNames();

    public String author = DEFAULT_AUTHOR;
    public String copyright = DEFAULT_COPYRIGHT;
    public String description = DEFAULT_DESCRIPTION;
    public ErrorHandler errorHandler = DEFAULT_ERROR_HANDLER;
    public String helpAddenda = DEFAULT_HELP_ADDENDA;
    public HelpGen helpGen = DEFAULT_HELP_GEN;
    public OptionHelp helpOpt = DEFAULT_HELP_OPT;
    public String invocation;
    public ImpLogger logger = DEFAULT_LOGGER;
    public boolean noMatchError = DEFAULT_NO_MATCH_ERROR;
    public String seeAlso = DEFAULT_SEE_ALSO;
    public String usageAddenda = DEFAULT_USAGE_ADDENDA;
    public UsageGen usageGen = DEFAULT_USAGE_GEN;

    public ImperioAppSpec() throws OptionException {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        String cn = ste[ste.length - 1].getClassName();
        int index = cn.lastIndexOf(".");
        if (index > 0) {
            index++;
        } else {
            index = 0;
        }
        invocation = cn.substring(index);
    }

    public ImperioAppSpec(ImperioAppSpec spec) throws OptionException {
        this.author = spec.author;
        this.copyright = spec.copyright;
        this.description = spec.description;
        this.errorHandler = spec.errorHandler;
        this.helpAddenda = spec.helpAddenda;
        this.helpGen = spec.helpGen;
        this.helpOpt = spec.helpOpt;
        this.invocation = spec.invocation;
        this.logger = spec.logger;
        this.noMatchError = spec.noMatchError;
        this.seeAlso = spec.seeAlso;
        this.usageAddenda = spec.usageAddenda;
        this.usageGen = spec.usageGen;
    }

}
