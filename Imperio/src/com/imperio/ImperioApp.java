/*
 * MIT License
 * 
 * Copyright (c) 2018 by Andrew Felsher
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.imperio;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * <p>
 * Instances of this class represent an application and can parse a CLI
 * invocation of the application.
 * </p>
 * 
 * <p>
 * <b>Specifying CLI Options:</b>
 * </p>
 * 
 * <p>
 * The most unambiguous way to specify a option is to use "--[opt-name]", where
 * "[opt-name]" is the full option name (e.g. "verbose"). If an arg begins with
 * "--" (e.g. "--verbose"), we will compare the arg (sans "--") to the full
 * option name.
 * </p>
 * 
 * <p>
 * For example, consider an application with only one valid option: "alice".
 * </p>
 * 
 * <p>
 * This Alice option could be specified with "--alice".
 * </p>
 * 
 * <p>
 * In addition to specifying a option via the "--[opt-name]" form, you can also
 * specify via the single-character POSIX "-[char]" form. Some notes on POSIX
 * options:
 * </p>
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
 * <li>Out of a desire to support applications with very large varieties of
 * parameters, the application developer is <b>not</b> required to specify a
 * single character, so an application may have options which must be specified
 * via the full option name ("--[opt-name]").</li>
 * <li>The application developer is similarly <b>not</b> required to specify a
 * name for the option. However, the application developer <b>must</b> specify
 * either a name or a single character or both.</li>
 * </ol>
 * 
 * <p>
 * Assuming the application developer does nothing to substantially modify the
 * behavior via callbacks and such, options are mostly POSIX-compliant, meaning:
 * </p>
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
 * <p>
 * Options deviate from POSIX in these ways:
 * </p>
 * <ol>
 * <li>Options with arguments must be separated from their arguments. "-o
 * [argument]" is valid. "-o[argument]" is not.</li>
 * <li>The - argument is not used to represent anything. It is ignored by this
 * library.</li>
 * <li>The -- string can be used to specify long-form options. However, when
 * used by itself it does terminate the options to be parses per POSIX.</li>
 * </ol>
 * 
 * <p>
 * For example, consider an application with only two valid options: "alice" and
 * "bob". Assume the application developer linked the "x" char to "alice" and
 * didn't link any char to "bob".
 * </p>
 * 
 * <p>
 * Alice could be specified with "-x" or "--alice", but <b>not</b> "-a".
 * </p>
 * 
 * <p>
 * Bob could be specified with "--bob", but <b>not</b> "-b".
 * </p>
 * 
 * <p>
 * For the sake of brevity, multiple single-character options can be specified
 * in a single arg <b>if</b> (and only if) all the options except the last one
 * do not specify values.
 * </p>
 * 
 * <p>
 * For example, consider an application with only three valid options: "alice",
 * "bob", "charlie". "charlie" takes a value, but the other options are flags.
 * Assume the application developer linked the "a" char to "alice", the "b" char
 * to "bob", and the "c" char to "charlie".
 * </p>
 * 
 * <p>
 * "-a" would specify Alice.
 * </p>
 * 
 * <p>
 * "-aa" would specify Alice twice.
 * </p>
 * 
 * <p>
 * "-b" would specify Bob.
 * </p>
 * 
 * <p>
 * "-ab" would specify Alice and Bob.
 * </p>
 * 
 * <p>
 * "-aab" would specify Alice twice and Bob once.
 * </p>
 * 
 * <p>
 * "-c mycharlie" would specify the value "mycharlie" for Charlie.
 * </p>
 * 
 * <p>
 * "-abc mycharlie" would specify Alice and Bob once, and would specify the
 * value "mycharlie" for Charlie
 * </p>
 * 
 * <p>
 * "-acb" and "-acb mycharlie" would be invalid
 * </p>
 *
 * @since 1.0.0
 */
public class ImperioApp {

    /**
     * @param spec
     * 
     * @return
     * 
     * @throws ImperioException 
     * 
     * @since 1.0.0
     */
    public static ImperioApp generate(ImperioAppSpec spec)
            throws ImperioException {
        ImperioApp ret = new ImperioApp(spec.author, spec.copyright,
                spec.description, spec.errorHandler, spec.helpGen, spec.helpOpt,
                spec.helpAddenda, spec.invocation, spec.logger,
                spec.unknownOptionError, spec.seeAlso, spec.usageGen,
                spec.usageAddenda);
        if (spec.errorHandler == null) {
            throw new ImperioException(
                    "Error handler cannot be null. If you  want a no-op error "
                            + "handler, use " + ErrorHandlerNull.class.getName()
                            + ".");
        }
        return ret;
    }

    public final String author;
    public final String copyright;
    public final String description;
    public final ErrorHandler errorHandler;
    private final List<ExampleCommand> examples = new ArrayList<ExampleCommand>();
    private final HelpGen help;
    public final String helpAddenda;
    public final Option helpOpt;
    private String helpStr = null;
    public final String invocation;
    private final ImpLogger logger;
    private final List<OptionIndex> optionChars = new ArrayList<OptionIndex>();
    private final List<OptionIndex> optionIndeces =
            new LinkedList<OptionIndex>();
    private final List<OptionIndex> optionNames = new ArrayList<OptionIndex>();
    private final Map<OptionIndex, Option> optionsByIndeces =
            new HashMap<OptionIndex, Option>();
    public final String seeAlso;
    public final boolean unknownOptionError;
    public final String usageAddenda;
    private final UsageGen usageGen;

    private String usageStr = null;

    private ImperioApp(String author, String copyright, String description,
            ErrorHandler handler, HelpGen help, OptionHelp helpOpt,
            String helpAddenda, String invocation, ImpLogger logger,
            boolean noMatchError, String seeAlso, UsageGen usage,
            String usageAddenda) throws InternalImperioException {
        this.author = author;
        this.copyright = copyright;
        this.description = description;
        this.errorHandler = handler;
        this.help = help;
        this.helpAddenda = helpAddenda;
        this.helpOpt = helpOpt;
        this.invocation = invocation;
        this.logger = logger;
        this.unknownOptionError = noMatchError;
        this.seeAlso = seeAlso;
        this.usageAddenda = usageAddenda;
        this.usageGen = usage;
        addOption(helpOpt);
    }

    /**
     * Add an example command using the given arguments. Make sure that all
     * arguments are properly escaped and/or enclosed in quotes.
     * 
     * @param description
     * @param args
     * 
     * @since 1.0.0
     */
    public void addExample(String description, String[] args) {
        examples.add(new ExampleCommand(description, args));
    }

    /**
     * @param args
     * 
     * @since 1.0.0
     * 
     * @see #addExample(String, String[])
     */
    public void addExample(String[] args) {
        addExample(null, args);
    }

    /**
     * @param option
     * 
     * @throws InternalImperioException
     * 
     * @since 1.0.0
     */
    public void addOption(Option option) throws InternalImperioException {
        Character ch = option.character;
        String name = option.name;
        OptionIndex index = null;

        try {
            index = new OptionIndex(name, ch);

            if (containsOption(name)) {
                errorHandler.err(ErrorType.GENERIC, this,
                        "Multiple options%s use option name \'%s\'",
                        (option.auto
                                ? " (including at least one option auto-"
                                        + "generated by the option library"
                                : ""),
                        name);
                return;
            }

            if (containsOption(ch)) {
                errorHandler.err(ErrorType.GENERIC, this,
                        "Multiple options%s use option char \'%s\'",
                        (option.auto
                                ? " (including at least one option auto-"
                                        + "generated by the option library"
                                : ""),
                        ch);
                return;
            }
        } catch (ImperioException e) {
            errorHandler.err(ErrorType.IMPERIO, this,
                    new InternalImperioException(e));
            return;
        }

        if (name == null && ch == null) {
            errorHandler.err(ErrorType.GENERIC, this,
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

    /**
     * Check if the given character is in use.
     * 
     * @param character character to check
     * 
     * @return {@code true} if this app contains an option assigned to the given
     *         character, else {@code false}
     * 
     * @throws ImperioException if the given character is null
     * 
     * @since 1.0.0
     */
    public boolean containsOption(Character character) throws ImperioException {
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

    /**
     * Check if the given index maps to an option for this application.
     * 
     * @param index option index to check
     * 
     * @return {@code true} if this app contains an option assigned to the given
     *         index, else {@code false}
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public boolean containsOption(OptionIndex index) throws ImperioException {
        /*
         * We can't just check if the Map contains this index, because it's
         * a hash map, and the hashes may not match even when the indices do
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

    /**
     * Check if the given option name is in use.
     * 
     * @param name option name to check
     * 
     * @return {@code true} if this app contains an option assigned to the given
     *         long-form name, else {@code false}
     * 
     * @throws ImperioException if the given name is null
     * 
     * @since 1.0.0
     */
    public boolean containsOption(String name) throws ImperioException {
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

    /**
     * @return the number of example commands this app contains
     * 
     * @since 1.0.0
     */
    public int exampleCount() {
        return examples.size();
    }

    /**
     * @param index
     * 
     * @return the example command at the given index if it exists, else
     *         {@code null}
     * 
     * @since 1.0.0
     */
    public ExampleCommand getExample(int index) {
        if (examples.size() <= index) {
            return null;
        }
        String[] args = new String[examples.get(index).args.length];
        System.arraycopy(examples.get(index).args, 0, args, 0, args.length);
        return new ExampleCommand(examples.get(index).description, args);
    }

    /**
     * @return the help text
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public String getHelpString() throws ImperioException {
        if (helpStr == null) {
            helpStr = help.getHelp(this);
        }
        return helpStr;
    }

    /**
     * @param character the option's character
     * 
     * @return the option assigned to the given character if it exists, else
     *         {@code null}
     * 
     * @since 1.0.0
     */
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

    /**
     * @param index the option's index
     * 
     * @return the option assigned to the given index, if it exists, else
     *         {@code null}
     * 
     * @since 1.0.0
     */
    public Option getOption(OptionIndex index) {
        return optionsByIndeces.get(index);
    }

    /**
     * @param name the option's name
     * 
     * @return the option assigned to the given long-form name if it exists,
     *         else {@code null}
     *
     * @since 1.0.0
     */
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

    /**
     * @return an iterator for the indices of all the options in the current app
     * 
     * @since 1.0.0
     */
    public ListIterator<OptionIndex> getOptionIndeces() {
        return optionIndeces.listIterator();
    }

    /**
     * @return the usage text
     * 
     * @throws ImperioException
     * 
     * @since 1.0.0
     */
    public String getUsageString() throws ImperioException {
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
     * @throws InternalImperioException
     * 
     * @see #processArgs(String[], StringArrContainer)
     * 
     * @since 1.0.0
     */
    public boolean processArgs(String[] args) throws InternalImperioException {
        return processArgs(args, null);
    }

    /**
     * Process the CLI arguments.
     * 
     * @param args
     *            the CLI arguments
     * @param nonOptArgs
     *            A container whose value is set by this function to an array of
     *            the leftover non-option arguments
     * 
     * @return {@code true} for normal operation if there were no errors.
     *         {@code false} if there was an error or the help option was used.
     * 
     * @throws InternalImperioException
     *             If an error occurs and the error handler is configured to
     *             throw exceptions.
     * 
     * @since 1.0.0
     */
    public boolean processArgs(String[] args, StringArrContainer nonOptArgs)
            throws InternalImperioException {
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
                errorHandler.warn(this,
                        "Hyphen(s) without option at option %d: \'%s\'", argi,
                        arg);
                continue argloop;
            } else if (arg.startsWith("---")) {
                errorHandler.err(ErrorType.INVALID_OPTION, this,
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
                                if ((opt.type.archetype == OptionArchetype.VALUE)
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
                                            this, sw.toString());
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
                        if (unknownOptionError) {
                            errorHandler.err(ErrorType.UNKNOWN_OPTION,
                                    this,
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
                        errorHandler.warn(this, "Using deprecated option: %s",
                                opt.getInvocation());
                    }

                    try {
                        switch (opt.type) {
                        case CUSTOM_FLAG:
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
                        case FILE:
                            if (args.length <= argi + 1) {
                                errorHandler.err(ErrorType.MISSING_ARG,
                                        this,
                                        "No value provided for \'%s\'",
                                        args[argi]);
                                ret = false;
                                continue saloop;
                            }
                            if (args[argi + 1].startsWith("-")) {
                                errorHandler.err(ErrorType.INVALID_ARG, this,
                                        "Value for \'%s\' (\'%s\') begins with "
                                                + "hyphen. Possible missing "
                                                + "value.",
                                        args[argi], args[argi + 1]);
                            }
                            opt.setValue(new File(args[++argi]));
                            break;
                        case INCREMENTER:
                            opt.setValue(((Integer) opt.getValue()) + 1);
                            if (opt.linkOption != null) {
                                opt.linkOption.setValue(opt.getValue());
                            }
                            break;
                        case ARG:
                        case CUSTOM_ARG:
                        default:
                            if (opt.type.archetype == OptionArchetype.VALUE) {
                                if (args.length <= argi + 1) {
                                    errorHandler.err(ErrorType.MISSING_ARG,
                                            this,
                                            "No value provided for \'%s\'",
                                            args[argi]);
                                    ret = false;
                                    continue saloop;
                                }
                                if (args[argi + 1].startsWith("-")) {
                                    errorHandler.warn(this,
                                            "Value for \'%s\' (\'%s\') begins "
                                                    + "with hyphen. Possible "
                                                    + "missing value.",
                                            args[argi], args[argi + 1]);
                                }
                                opt.setValue(args[++argi]);
                            }
                            break;
                        }
                    } catch (ImperioException e) {
                        errorHandler.err(ErrorType.IMPERIO, this,
                                new InternalImperioException(e));
                    }
                    if (opt.callback != null) {
                        try {
                            opt.callback.callback(this, opt, oldVal);
                        } catch (ImperioException e) {
                            errorHandler.err(ErrorType.GENERIC, this,
                                    new InternalImperioException(e));
                        }
                    }
                    if (opt.equals(helpOpt)) {
                        printHelp = true;
                    }
                }
            }
        }

        if ((nonOptArgs != null) && (args != null) && (argi < args.length)) {
            logger.log("Generating non-opts array");
            String[] nonOpts = new String[args.length - argi];
            for (int i = 0; (i < nonOpts.length); i++) {
                logger.log("Adding non-opts arg %d: %s", i, args[argi + i]);
                nonOpts[i] = args[argi + i];
            }
            nonOptArgs.arr = nonOpts;
        }

        if (printHelp) {
            try {
                help.printHelp(this);
            } catch (ImperioException e) {
                errorHandler.err(ErrorType.GENERIC, this,
                        new InternalImperioException(e));
            }
            ret = false;
        } else {
            Iterator<OptionIndex> iter = getOptionIndeces();
            while (iter.hasNext()) {
                OptionIndex index = iter.next();
                Option opt = optionsByIndeces.get(index);
                if (opt.required && !opt.provided) {
                    errorHandler.err(ErrorType.MISSING_REQ_OPTION,
                            this, "Required option not given: %s",
                            opt.getInvocation());
                    ret = false;
                }
            }
        }

        return ret;
    }

}
