package com.imperio;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ListIterator;

public class UsageGenChars extends UsageGen {

    @Override
    public void printUsage(PrintStream ps, ImperioApp impApp)
            throws OptionException {
        boolean foundReqFlags = false;
        boolean foundOptFlags = false;
        StringWriter optArgs = null;
        StringWriter optFlags = null;
        StringWriter reqArgs = null;
        StringWriter reqFlags = null;

        optArgs = new StringWriter();
        optFlags = new StringWriter();
        reqArgs = new StringWriter();
        reqFlags = new StringWriter();

        ListIterator<OptionIndex> idxIter = impApp.getOptionIndeces();
        while (idxIter.hasNext()) {
            OptionIndex index = idxIter.next();

            Option arg = impApp.getOption(index);

            if (arg.deprecated) {
                continue;
            }
            if (arg.required) {
                switch (arg.type) {
                case ARG:
                    if (arg.character != null) {
                        reqArgs.write(" -" + arg.character + " <"
                                + arg.valPlaceholder + ">");
                    } else {
                        reqArgs.write(" --" + arg.name + " <"
                                + arg.valPlaceholder + ">");
                    }
                    break;
                case CUSTOM:
                case FLAG:
                case INCREMENTER:
                    // Will probably never happen
                    if (arg.character != null) {
                        if (!foundReqFlags) {
                            foundReqFlags = true;
                            reqFlags.write(" -");
                        }
                        reqFlags.write(arg.character);
                    } else {
                        reqArgs.write(" --" + arg.name);
                    }
                    break;
                default:
                    throw new OptionException(
                            "Unknown option type " + arg.type);
                }
            } else {
                switch (arg.type) {
                case ARG:
                    if (arg.character != null) {
                        optArgs.write(" [-" + arg.character + " <"
                                + arg.valPlaceholder + ">]");
                    } else {
                        optArgs.write(" [--" + arg.name + " <"
                                + arg.valPlaceholder + ">]");
                    }
                    break;
                case CUSTOM:
                case FLAG:
                case INCREMENTER:
                    if (arg.character != null) {
                        if (!foundOptFlags) {
                            foundOptFlags = true;
                            optFlags.write(" [-");
                        }
                        optFlags.write(arg.character);
                    } else {
                        optArgs.write(" [--" + arg.name + "]");
                    }
                    break;
                default:
                    throw new OptionException(
                            "Unknown option type " + arg.type);
                }
            }
        }
        if (foundOptFlags) {
            optFlags.write("]");
        }
        if (foundReqFlags) {
            reqFlags.write("]");
        }

        ps.println();
        ps.print(impApp.invocation);
        ps.print(reqFlags.toString() + reqArgs.toString() + optFlags.toString()
                + optArgs.toString());
        if (impApp.usageAddenda != null) {
            ps.print(impApp.usageAddenda);
        }
        ps.println();
    }

}
