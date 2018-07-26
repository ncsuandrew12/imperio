package com.imperio;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Instances of this class represent an application and can parse a CLI
 * invocation of the application.
 * 
 * <b>Specifying CLI Options:</b>
 * 
 * The most unambiguous way to specify a option is to use "--[opt-name]", where
 * "[opt-name]" is the full option name (e.g. "verbose"). If an arg begins with
 * "--" (e.g. "--verbose"), we will compare the arg (sans "--") to the full
 * option name.
 * 
 * For example, consider an application with only one valid option: "alice".
 * 
 * This Alice option could be specified with "--alice".
 * 
 * In addition to specifying a option via the "--[opt-name]" form, you can also
 * specify via the single-character POSIX "-[char]" form. Some notes on POSIX
 * options:
 * <ol>
 * <li>Each option is only linked to a single character. There are no "alias"
 * characters unless the application codes multiple options to perform the same
 * function, which we do not recommend.</li>
 * <li>The char is explicitly set by the application, and need not bear any
 * relation to the option name. For example, a "verbose" option could be coded
 * to match with "-v", but also "-z", "-q", or whatever the application
 * developer desires.</li>
 * <li>For the sake of user-friendliness, we recommend making the single
 * character derive from the option name in some form, if possible (for example:
 * make "-v" equivalent to "--verbose").</li>
 * <li>Out of a desire to support application with very large varieties of
 * parameters, the application developer is <b>not<b> required to specify a
 * single character, so an application may have options which must be specified
 * via the full option name ("--[opt-name]").</li>
 * <li>The application developer is similarly <b>not</b> required to specify a
 * name for the option. However, the application developer <b>must</b> specify
 * either a name or a single character or both.</li>
 * </ol>
 * 
 * Assuming the application developer does nothing to substantially modify the
 * behavior via callbacks and such, options are mostly POSIX-compliant, meaning:
 * <ol>
 * <li>An option is a hyphen followed by a single alphanumeric character, like
 * this: "-o".</li>
 * <li>An option may require an argument (which must appear immediately after
 * the option); for example, "-o [argument]".</li>
 * <li>Options that do not require arguments can be grouped after a hyphen, so,
 * for example, "-lst" is equivalent to "-t -l -s".</li>
 * <li>Options can appear in any order; thus "-lst" is equivalent to
 * "-tls".</li>
 * <li>Options can appear multiple times.</li>
 * <li>Options precede other non-option arguments: "-lst nonoption".</li>
 * <li>The -- argument terminates options.</li>
 * </ol>
 * 
 * Options deviate from POSIX in these ways:
 * <ol>
 * <li>Options with arguments must be separated from their arguments. "-o
 * [argument]" is valid. "-o[argument]" is not.</li>
 * <li>The - argument is not used to represent anything. It is ignored by this
 * library.</li>
 * <li>The -- string can be used to specify long-form options.</li>
 * </ol>
 * 
 * For example, consider an application with only two valid options: "alice" and
 * "bob". Assume the application developer linked the "x" char to "alice" and
 * didn't link any char to "bob".
 * 
 * Alice could be specified with "-x" or "--alice", but <b>not</b> "-a".
 * 
 * Bob could be specified with "--bob", but <b>not</b> "-b".
 * 
 * For the sake of brevity, multiple single-character options can be specified
 * in a single arg <b>if</b> (and only if) all the options except the last one
 * do not specify values.
 * 
 * For example, consider an application with only four valid options: "alice",
 * "bob", "charlie". "charlie" takes a value, but the other options are flags.
 * Assume the application developer linked the "a" char to "alice", the "b" char
 * to "bob", and the "c" char to "charlie".
 * 
 * "-a" would specify Alice.
 * 
 * "-aa" would specify Alice twice.
 * 
 * "-b" would specify Bob.
 * 
 * "-ab" would specify Alice and Bob.
 * 
 * "-aab" would specify Alice twice and Bob once.
 * 
 * "-c mycharlie" would specify the value "mycharlie" for Charlie.
 * 
 * "-abc mycharlie" would specify Alice and Bob once, and would specify the
 * value "mycharlie" for Charlie
 * 
 * "-acb" and "-acb mycharlie" would be invalid
 * 
 * @param args
 * @return
 * @throws OptionException
 */
public class ImperioApp {

    public static ImperioApp generate(ImperioAppSpec spec)
            throws OptionException {
        ImperioApp ret = new ImperioApp(spec.author, spec.copyright,
                spec.description, spec.errorHandler, spec.helpGen, spec.helpOpt,
                spec.helpAddenda, spec.invocation, spec.logger,
                spec.noMatchError, spec.seeAlso, spec.usageGen,
                spec.usageAddenda);
        return ret;
    }

    public final String author;
    public final String copyright;
    public final String description;
    private final ErrorHandler errorHandler;
    private final List<ExampleCommand> examples = new ArrayList<ExampleCommand>();
    private final HelpGen help;
    public final String helpAddenda;
    public final Option helpOpt;
    private String helpStr = null;
    public final String invocation;
    private final ImpLogger logger;
    public final boolean noMatchError;
    private final List<OptionIndex> optionChars = new ArrayList<OptionIndex>();
    private final List<OptionIndex> optionIndeces =
            new LinkedList<OptionIndex>();
    private final List<OptionIndex> optionNames = new ArrayList<OptionIndex>();
    private final Map<OptionIndex, Option> optionsByIndeces =
            new HashMap<OptionIndex, Option>();
    public final String seeAlso;
    public final String usageAddenda;
    private final UsageGen usageGen;

    private String usageStr = null;

    private ImperioApp(String author, String copyright, String description,
            ErrorHandler handler, HelpGen help, OptionHelp helpOpt,
            String helpAddenda, String invocation, ImpLogger logger,
            boolean noMatchError, String seeAlso, UsageGen usage,
            String usageAddenda) throws OptionException {
        this.author = author;
        this.copyright = copyright;
        this.description = description;
        this.errorHandler = handler;
        this.help = help;
        this.helpAddenda = helpAddenda;
        this.helpOpt = helpOpt;
        this.invocation = invocation;
        this.logger = logger;
        this.noMatchError = noMatchError;
        this.seeAlso = seeAlso;
        this.usageAddenda = usageAddenda;
        this.usageGen = usage;
        add(helpOpt);
    }

    public void addExample(String[] args) {
        addExample(null, args);
    }

    /**
     * Add an example command using the given arguments.
     * 
     * If using the default {@link HelpGen}, it will perform all necessary
     * escaping of special characters, so you only need to escape characters if
     * Java strings required it (e.g. \\, \n). Note that carriage return,
     * newline, and tab characters will be converted to "\r", "\n", and "\t"
     * literals.
     * 
     * @param description
     * @param args
     */
    public void addExample(String description, String[] args) {
        examples.add(new ExampleCommand(description, args));
    }

    public int exampleCount() {
        return examples.size();
    }

    public ExampleCommand getExample(int index) {
        if (examples.size() <= index) {
            return null;
        }
        String[] args = new String[examples.get(index).args.length];
        System.arraycopy(examples.get(index).args, 0, args, 0, args.length);
        return new ExampleCommand(examples.get(index).description, args);
    }

    public void add(Option option) throws OptionException {
        Character ch = option.character;
        String name = option.name;
        OptionIndex index = new OptionIndex(name, ch);

        if (containsOption(name)) {
            errorHandler.err(ErrorType.GENERIC, getUsageString(),
                    "Multiple options%s use option name \'%s\'",
                    (option.auto
                            ? " (including at least one option auto-generated by "
                                    + "the option library"
                            : ""),
                    name);
            return;
        }
        if (containsOption(ch)) {
            errorHandler.err(ErrorType.GENERIC, getUsageString(),
                    "Multiple options%s use option char \'%s\'",
                    (option.auto
                            ? " (including at least one option auto-generated by "
                                    + "the option library"
                            : ""),
                    ch);
            return;
        }

        if (name == null && ch == null) {
            errorHandler.err(ErrorType.GENERIC, getUsageString(),
                    "Option must have a name or a character.");
        }

        logger.log("");
        logger.log("Adding   option %s", option.getInvocation());
        optionsByIndeces.put(index, option);

        if (name != null) {
            boolean added = false;
            logger.log("Checking option name %s", name);
            for (int i = 0; i < optionNames.size(); i++) {
                if (name.compareTo(optionNames.get(i).name) < 0) {
                    logger.log("Adding option name %s", name);
                    optionNames.add(i, index);
                    added = true;
                    break;
                }
            }
            if (!added) {
                logger.log("Adding   option name %s", name);
                optionNames.add(index);
            }
        }
        if (ch != null) {
            boolean added = false;
            logger.log("Checking option char %c", ch);
            for (int i = 0; i < optionChars.size(); i++) {
                if (ch < optionChars.get(i).character) {
                    logger.log("Adding   option char %c", ch);
                    optionChars.add(i, index);
                    added = true;
                    break;
                }
            }
            if (!added) {
                logger.log("Adding   option char %c", ch);
                optionChars.add(index);
            }
        }

        boolean added = false;
        logger.log("Checking option index %s", index);
        for (int i = 0; i < optionIndeces.size(); i++) {
            if (index.compareTo(optionIndeces.get(i)) < 0) {
                logger.log("Adding   option index %s", index);
                optionIndeces.add(i, index);
                added = true;
                break;
            }
        }
        if (!added) {
            logger.log("Adding   option index %s", index);
            optionIndeces.add(index);
        }
    }

    public boolean containsOption(Character character) throws OptionException {
        if ((character != null)) {
            Iterator<OptionIndex> iter = getOptionIndeces();
            while (iter.hasNext()) {
                OptionIndex index = iter.next();
                if (index.compareTo(character) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsOption(OptionIndex index) throws OptionException {
        /*
         * We can't just check if the Map contains this index, because it's
         * a hash map, and the hashes may not match even when the indeces do
         * (e.g. index A contains the name and index B contains both the name
         * and the character).
         */
        if (containsOption(index.name)) {
            return true;
        }
        if (containsOption(index.character)) {
            return true;
        }
        return false;
    }

    public boolean containsOption(String name) throws OptionException {
        if ((name != null)) {
            Iterator<OptionIndex> iter = getOptionIndeces();
            while (iter.hasNext()) {
                OptionIndex index = iter.next();
                if (index.compareTo(name) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getHelpString() throws OptionException {
        if (helpStr == null) {
            helpStr = help.getHelp(this);
        }
        return helpStr;
    }

    public Option getOption(Character character) {
        Iterator<OptionIndex> iter = optionsByIndeces.keySet().iterator();
        while (iter.hasNext()) {
            Option opt = optionsByIndeces.get(iter.next());
            if (opt.character == character) {
                return opt;
            }
        }
        return null;
    }

    public Option getOption(OptionIndex index) {
        return optionsByIndeces.get(index);
    }

    public Option getOption(String name) {
        Iterator<OptionIndex> iter = optionsByIndeces.keySet().iterator();
        while (iter.hasNext()) {
            Option opt = optionsByIndeces.get(iter.next());
            if (opt.name.compareTo(name) == 0) {
                return opt;
            }
        }
        return null;
    }

    public ListIterator<OptionIndex> getOptionIndeces() {
        return optionIndeces.listIterator();
    }

    public String getUsageString() throws OptionException {
        if (usageStr == null) {
            usageStr = usageGen.getUsage(this);
        }
        return usageStr;
    }

    /**
     * 
     * @param args
     *            the CLI arguments
     * 
     * @return {@code true} for normal operation if there were no errors.
     *         {@code false} if there was an error or the help option was used.
     * 
     * @throws OptionException
     * 
     * @see {@link #processArgs(String[], StringArrContainer)}
     */
    public boolean processArgs(String[] args) throws OptionException {
        return processArgs(args, null);
    }

    /**
     * Process the CLI arguments.
     * 
     * @param args
     *            the CLI arguments
     * @param sac
     *            A container whose value is set by this function to an array of
     *            the leftover non-option arguments
     * 
     * @return {@code true} for normal operation if there were no errors.
     *         {@code false} if there was an error or the help option was used.
     * 
     * @throws OptionException
     *             If an error occurs and the error handler is configured to
     *             throw exceptions.
     */
    public boolean processArgs(String[] args, StringArrContainer sac)
            throws OptionException {
        int argi = 0;
        boolean printHelp = false;
        boolean ret = true;

        logger.log("");
        logger.log("Processing args (%d)", args == null ? 0 : args.length);
        argloop: for (argi = 0; (args != null)
                && (argi < args.length); argi++) {
            String arg = args[argi];
            logger.log("");
            logger.log("");
            logger.log("Processing arg %d/%d: %s", argi + 1, args.length, arg);
            if (arg.equals("--")) {
                logger.log("Option args terminated by %s at %d/%d", arg,
                        argi + 1, args.length);
                argi++; // Don't include -- in the leftover args.
                break;
            } else if (!arg.startsWith("-")) {
                logger.log("Option args terminated by non-option arg %d/%d: %s",
                        argi + 1, args.length, arg);
                break;
            } else if (arg.equals("-")) {
                errorHandler.warn(
                        "Hyphen(s) without option at option %d: \'%s\'", argi,
                        arg);
                continue argloop;
            } else if (arg.startsWith("---")) {
                errorHandler.err(ErrorType.INVALID_OPTION, usageStr,
                        "Invalid option \'%s\'. Did you mean \'--%s\'?", arg,
                        arg.replaceAll("^-+", ""));
                ret = false;
                continue argloop;
            } else {
                logger.log("Processing subargs for arg %d/%d: %s", argi + 1,
                        args.length, arg);

                boolean isPosix =
                        (arg.startsWith("-") && !arg.startsWith("--"));
                String[] subargs = null;
                String wholeArg = arg.replaceFirst("-+", "");
                Option wholeOpt = null;
                String wholeOptStr = null;

                for (int parmi = 0; parmi < optionNames.size(); parmi++) {
                    OptionIndex index = optionNames.get(parmi);
                    String parmName = index.name;
                    logger.log("Comparing arg %d/%d %s to %s", argi + 1,
                            args.length, wholeArg, parmName);
                    if (parmName.equals(wholeArg)) {
                        wholeOpt = optionsByIndeces.get(index);
                        wholeOptStr = "Did you mean \'"
                                + wholeOpt.getInvocation() + "\'?";
                        break;
                    }
                }

                if (isPosix) {
                    subargs = new String[wholeArg.length()];
                    for (int b = 0; b < subargs.length; b++) {
                        subargs[b] = wholeArg.substring(b, b + 1);
                    }
                } else {
                    subargs = new String[] { wholeArg };
                }

                saloop: for (int sai = 0; sai < subargs.length; sai++) {
                    Option opt = null;
                    String subarg = subargs[sai];
                    Object oldVal = null;

                    logger.log("");
                    logger.log("Processing subarg %d/%d for arg %d/%d: %s",
                            sai + 1, subargs.length, argi + 1, args.length,
                            subarg);

                    if (!isPosix) {
                        opt = wholeOpt;
                    } else {
                        for (int parmi = 0; parmi < optionChars.size(); parmi++) {
                            OptionIndex index = optionChars.get(parmi);
                            Character parmChar = index.character;

                            logger.log("Comparing subarg %d/%d %s to %c",
                                    sai + 1, subargs.length, subarg, parmChar);
                            if ((parmChar != null)
                                    && (parmChar == subarg.charAt(0))) {
                                opt = optionsByIndeces.get(index);
                                if ((opt.type == OptionType.ARG)
                                        && (sai < subargs.length - 1)) {
                                    StringWriter sw = new StringWriter();

                                    sw.write(String.format(
                                    "Cannot include option \'%s\' (which "
                                            + "takes an argument) in "
                                            + "multi-option argument "
                                            + "(\'%s\') unless it is the "
                                            + "final option.",
                                    subarg, arg));
                                    sw.write(" Did you mean \'-");
                                    sw.write(parmChar);
                                    sw.write(" <arg> -");
                                    if (sai > 0) {
                                        sw.write(wholeArg.substring(0, sai));
                                    }
                                    if (sai < subargs.length - 1) {
                                        sw.write(wholeArg.substring(sai + 1));
                                    }
                                    sw.write("\'?");
                                    if (wholeOpt != null) {
                                        sw.write(" " + wholeOptStr);
                                    }
                                    errorHandler.err(ErrorType.OPTION_PARSING,
                                            getUsageString(), sw.toString());
                                    ret = false;
                                    continue saloop;
                                }
                                logger.log(
                                        "Subarg %d/%d for arg %d/%d (%s) matches "
                                                + "option %c",
                                        sai + 1, subargs.length, argi + 1,
                                        args.length, subarg, opt.character);
                                break;
                            }
                        }
                    }
                    if (opt == null) {
                        if (noMatchError) {
                            errorHandler.err(ErrorType.UNKNOWN_OPTION,
                                    getUsageString(),
                                    "Unknown option \'%s\' in argument \'%s\'.%s",
                                    subargs[sai], args[argi],
                                    ((wholeOptStr == null) ? ""
                                            : (" " + wholeOptStr)));
                            ret = false;
                            continue saloop;
                        } else {
                            logger.log("Option args terminated by non-option arg %d/%d: %s",
                                    argi + 1, args.length, arg);
                            break argloop;
                        }
                    }

                    opt.provided = true;
                    oldVal = opt.getValue();

                    if (opt.deprecated) {
                        errorHandler.warn("Using deprecated option: %s",
                                opt.getInvocation());
                    }

                    switch (opt.type) {
                    case ARG:
                        if (args.length <= argi + 1) {
                            errorHandler.err(ErrorType.MISSING_ARG,
                                    getUsageString(),
                                    "No value provided for \'%s\'", args[argi]);
                            ret = false;
                            continue saloop;
                        }
                        if (args[argi + 1].startsWith("-")) {
                            errorHandler.warn(getUsageString(),
                                    "Value for \'%s\' (\'%s\') begins with hyphen. "
                                            + "Possible missing value.",
                                    args[argi], args[argi + 1]);
                        }
                        opt.setValue(args[++argi]);
                        break;
                    case CUSTOM:
                        break;
                    case FLAG:
                        opt.setValue(true);
                        if (opt.linkOption != null) {
                            opt.linkOption.setValue(false);
                        }
                        break;
                    case DECREMENTER:
                        opt.setValue(((Integer) opt.getValue()) - 1);
                        if (opt.linkOption != null) {
                            opt.linkOption.setValue(opt.getValue());
                        }
                        break;
                    case INCREMENTER:
                        opt.setValue(((Integer) opt.getValue()) + 1);
                        if (opt.linkOption != null) {
                            opt.linkOption.setValue(opt.getValue());
                        }
                        break;
                    default:
                        errorHandler.err(ErrorType.IMPERIO, getUsageString(),
                                "Unknown option type %s", opt.type);
                        ret = false;
                        continue saloop;
                    }
                    if (opt.callback != null) {
                        opt.callback.callback(this, opt, oldVal);
                    }
                    if (opt.equals(helpOpt)) {
                        printHelp = true;
                    }
                }
            }
        }

        if ((sac != null) && (args != null) && (argi < args.length)) {
            logger.log("Generating non-opts array");
            String[] nonOpts = new String[args.length - argi];
            for (int i = 0; (i < nonOpts.length); i++) {
                logger.log("Adding non-opts arg %d: %s", i, args[argi + i]);
                nonOpts[i] = args[argi + i];
            }
            sac.arr = nonOpts;
        }

        if (printHelp) {
            help.printHelp(this);
            ret = false;
        } else {
            Iterator<OptionIndex> iter = getOptionIndeces();
            while (iter.hasNext()) {
                OptionIndex index = iter.next();
                Option opt = optionsByIndeces.get(index);
                if (opt.required && !opt.provided) {
                    errorHandler.err(ErrorType.MISSING_REQ_OPTION,
                            getUsageString(), "Required option not given: %s",
                            opt.getInvocation());
                    ret = false;
                }
            }
        }

        return ret;
    }

}
