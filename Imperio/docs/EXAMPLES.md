# Introduction

For reference, the example applications and Eclipse projects are located here:

[projects/examples](../projects/examples)

These examples mostly revolve around implementing the `echo` Unix utility using the Imperio options library. Note that the intent is to implement GNU echo, **not** e.g. the bash version of echo.


# E1: Barebones - Create a basic app with some flags.

Project: [Echo](../projects/examples/Echo)

Package: [com.imperio.examples.echo.p01barebones](../projects/examples/Echo/src/com/imperio/examples/echo/p01barebones)

The echo.p1barebones package contains example code for creating a dummy `echo` command. This application takes a number of parameters which correspond to the parameters used by the standard echo command.

Note that this application doesn't actually do anything. If you run the Echo class, you will see no output. However, if you run it with the `--help` argument, you will get a help text showing the usage and listing the possible parameters.

## E1A - Creating Options

Options are instantiated with the `Option` class's `generate()` function and an `OptionSpec` instance. An `OptionSpec` is merely a container for all the values needed to generate an Option. Most of the defaults are acceptable most of the time. In this case, we only need to change the names of the options. Generating the option does not modify the spec in any way.

### E1A1 - Names & Characters

Option names and characters are used to uniquely identify the option.

Option names are also used as the long-form name used on the command line. For example, the "no newline" parameter's name is "no-newline" and can be specified on the command line with `--no-newline`. The format for these parameters is `--name`. Two hyphens must be used.

If two options use the same name, an error will occur during the `ImperioApp.generate()` function.

### E1A2 - Auto-Help Option

Note that there is no `--help` option created, but we still get help text when we run with this option. The Imperio library - by default - creates an implicit help option which can be used via either `--help` or `-h`.

## E1B - Creating the ImperioApp

The `ImperioApp.generate()` function is used to actually create the Imperio library's representation of the application and takes an `ImperioAppSpec` as its only parameter. The `ImperoAppSpec` is similar to the OptionSpec, but contains values needed to create an ImperioApp. Generally, a developer should at least set the `invocation` and the options list (via `addOption()`).

The base invocation string is used to create the usage text and should correspond to what the user needs to actually type to invoke the command. In this example, we use `"echo"` as the invocation, but in reality the user would need to invoke a complicate java command specifying the full package and class name of our example class.

If your application uses a startup script, it could simply use this script name as the invocation string. For example:

```java
ImperioAppSpec iaSpec = new ImperioAppSpec();
iaSpec.invocation = "startup.sh";
iaSpec.options = optionList;
ImperioApp.generate(iaSpec);
```

## E1C - Processing the arguments

The `ImperioApp.processArgs()` function is what actually processes the arguments. Typically, this will simply be the string array provided to `main()`, but any string array can be used. For example, you might want to create a copy of the array that doesn't contain some special arguments that you want to handle separately, and provide that sanitized array to `processArgs()`.

Note that the `main()` function throws `OptionException`s. In the event of an error, the Imperio library will provide some error information to an error handler. The default error handler will use this information to throw an `OptionException`. However, the library can be configured to handle errors differently (e.g. logging a message or doing nothing).

You can test out the error handling by running the app with the `-x` parameter. You should see a stacktrace complaining about `x` being an unknown option.

# E2: Enum - Represent all possible options with an enum

Project: [Echo](../projects/examples/Echo)

Package: [com.imperio.examples.echo.p02enum](../projects/examples/Echo/src/com/imperio/examples/echo/p02enum)

This example does not, strictly speaking, deal with the Imperio library. Rather, it demonstrates a recommended practice for applications using Imperio.

In the previous example, we instantiated each of our options separately and added them directly to our option list. But in many cases, these options would not be useful unless we can easily reference them later (for example, accessing the arg provided for e.g. an output file option).

The best way to do this is by using an enum. In this example, you can see that a new Parameter class has been added. The `Option` instantiations have been moved into this class, with each enum value receiving a corresponding `Option` object. The `Echo` class has been refactored to derive the option list from the `Parameter`'s values and stored `Option` objects. This also changes the order in which options get added, but this doesn't matter. Imperio sorts the options internally by name as they're added.

# E3: POSIX - Add POSIX-style options and improve the help text

Project: [Echo](../projects/examples/Echo) 

Package: [com.imperio.examples.echo.p03posix](../projects/examples/Echo/src/com/imperio/examples/echo/p03posix)

So far, we've created options that map to the Unix echo command, but most of `echo`'s options are single-character options. And we've only provided long-form option names.

Imperio supports the ability to add single-character POSIX options. For more information on POSIX compliance and deviations, see the `ImperoApp` class's Javadoc. The main noteworthy feature is that when a single hyphen is used, any number of single-character options can be specified, though only the absolute last one in the argument may specify a value. The rest must be "flag" style, in that they don't take an argument.

Another deviation from `echo` is the help text. Namely, the option descriptions.

This example makes use of additional `OptionSpec` fields which allows us to specify a character and help text for each of the options. We don't provide a character for the `version` option because the `echo` command doesn't either.

Option characters must be unique within the `ImperioApp` instance, or an error will occur during the `ImperioApp.generate()` function.

We've also updated the `help` option's help text. But since Imperio treats the help option differently from standard options, this is done in `Echo.java` and requires specifying the `ImperoAppSpec.helpOpt` field. While we're at it, we also specify a `null` character to remove the ability to get the help text via the `-h` option. (This is done to more closely match the `echo` command.)

And finally, we've removed the long-form option names from `echo`'s single-character-only options.

# E4: Processed - Handling "help" and echo strings

Project: [Echo](../projects/examples/Echo) 

Package: [com.imperio.examples.echo.p04processed](../projects/examples/Echo/src/com/imperio/examples/echo/p04processed)

Now that we've updated the help text with echo's text, we should probably update our app to actually adhere to the description: "display this help and exit".

The `ImperioApp.processArgs()` function returns `false `if an error occurs or if the help text was printed. Otherwise, it returns `true`. Since we're using the default error handler, an exception will be thrown if an error occurs. Therefore, this function will only return `false` if the help text was printed. So we simply return if it returns `false`. Since this is the `main()` function, return causes us to exit.

This example also showcases how to retrieve non-option arguments. In many applications, there may be arguments which are not intended to be processed as we've been doing. In this example, the string we're supposed to echo is not an option. Imperio will cease treating arguments as options once it encounters a "`--`" with no option name **or** once it encounters an argument that does not begin with a hyphen **and** is not an argument for the previous option, whichever comes first. All arguments after this one are ignored by Imperio.

This example makes use of an `Imperio.processArgs()` overload which takes a `StringArrContainer` parameter. A `StringArrContainer` is a simple class which merely wraps around a string array. This is simply a way to return a string array back to the application since we're already returning a boolean. The array contained within this container after `processArgs()` returns contains all the arguments which were not options (or arguments for options). In this case, this is the string(s) we want to print. To emulate `echo`'s behavior, we simply print these strings out, with spaces separating the strings.

# E5: Linked Flags - Linking multiple flags to the same value

Project: [Echo](../projects/examples/Echo) 

Package: [com.imperio.examples.echo.p05linkedflags](../projects/examples/Echo/src/com/imperio/examples/echo/p05linkedflags)

The `Echo` application could add some logic to interpret the `-e` and `-E` values to see whether or not the newline should be printed. However, it is cleaner to simply use Imperio to explicitly link the two Flag types. The `OptionSpec` can be provided with an `Option` parameter using the `linkOption` field. If we provide this parameter when instantiating an `OptionType.FLAG` option, it will be interpreted as the opposite of that option. Basically, the value of one will always be the inverse of the value of the other (and their default values will also be inverses).

This is exactly the behavior we want for `-e` and `-E`. `-e` essentially flips a bit to `true` and `-E` flips the same bit to `false`.

Note that we don't actually add the "(default)" part for `-E` in this example. If an option is a flag type and its default value is `true`, Imperio will add the "(default)" part by default. Because the `-e` option's default value is `false` and the two options are linked, Imperio sets the `-E` default value to `true`.

We've added a print statement to indicate whether backslash escaping has been enabled or disabled. This also demonstrates how to access the post-processing value of the options.

You can test the functionality by running this example with several sets of options:

1. No options, `-E`, and `-eE` should all give this output:  
 `backslash escapes disabled (false, !true)`
1. `-e` and `-Ee` should both give this output:  
 `backslash escapes enabled (true, !false)`

# E6: Help Addenda - Adding additional help text

Project: [Echo](../projects/examples/Echo) 

Package: [com.options.examples.echo.p06helpaddenda](../projects/examples/Echo/src/com/options/examples/echo/p06helpaddenda)

The help text so far has been missing a lot of information included in echo's documentation. This example adds a little bit of the missing documentation to demonstrate how you can append additional documentation at the end of the help text simply by setting the `ImperioAppSpec.helpAddenda` field.

# E7: Help - Full customization

Project: [Echo](../projects/examples/Echo) 

Package: [com.options.examples.echo.p07help](../projects/examples/Echo/src/com/options/examples/echo/p07help)

Previous examples have shown how to make tweaks to the automagically-generated help text. It is also possible to go the nuclear route and completely replace the help-text-generation code with your own code. To do so, simply set the `ImperioAppSpec.helpGen` field.

In this example, we implement a custom `Help` class. The class must extend Imperio's `HelpGen` class. A `HelpGen` implementation must implement the `void printHelp(PrintStream ps, ImperioApp impApp)` function. In this example, we use the `impApp` object as a reference to derive the help text. It is also perfectly valid to ignore the `impApp` parameter and simply create help text from e.g. hard-coded string literals or data read in from a file.

We also implement a custom `Usage` class. The class must extend Imperio's `UsageGen` class. A `UsageGen` implementation must implement the `void printUsage(PrintStream ps, ImperioApp impApp)` function.

# E8: Echo - Full implementation

Project: [Echo](../projects/examples/Echo)

Package: [com.options.examples.echo.p08complete](../projects/examples/Echo/src/com/options/examples/echo/p08complete)

In this example, the final code for `Echo` matches the behavior of GNU echo almost exactly.

When `-e` is enabled, GNU echo is unable to properly encode/decode some non-ASCII octal and hex strings. Our example program (or rather, the JRE) has more advanced encoding/decoding.

Note that this example is more of an exercise in duplicating GNU echo, and doesn't really use any new Imperio features. But it serves as an example of a slightly more complex program.

The following code can be added at the top of the run function to fully test the `-e` parameter:

```java
args = new String[] {
        "-e",
        "newline \\\\\\\\n:",
        "\\n",
        "\\nthree quotes:",
        "\\\"\\\"\\\"",
        "\\n\\nmid-string tab:",
        "te\\txt",
        "\\n\\nbackslash:",
        "\\",
        "\\n\\ndouble-to-single-backslash:",
        "\\\\",
        "\\n\\nalert/bell:",
        "alert\\abell",
        "\\n\\nescape:",
        "esc\\eape",
        "\\n\\nbackspace:",
        "unfinished\\b",
        "\\n\\nform feed:",
        "form\\ffeed",
        "\\n\\ncarriage return:",
        "\\ncarriage\\rcarriage return",
        "\\n\\nvertical tab: vertical\\vtab",
        "\\n\\noctal: 107 162 157 157 164 41 041 1234 300 \\\\\\0Z 1 => Groot!!S4ÀZ",
        "\\n\\0107\\0162\\0157\\0157\\0164\\041\\0041\\01234\\0300\\0Z\\01",
        "\\n\\noctal: \\\\\\\\0Z => Z",
        "\\n\\0Z",
        "\\n\\noctal: \\\\\\\\0 => ",
        "\\n\\0",
        "\\n\\nhex: 47 72 6F 6f 74 21 9 2d 09 40 c0 \\\\\\xZ => Groot!\t-\t@À\\xZ",
        "\\n\\x47\\x72\\x6F\\x6f\\x74\\x21\\x9\\x2d\\x09\\x40\\xc0\\xZ",
        "\\n\\nhex: \\\\\\xZ => \\xZ",
        "\\n\\xZ",
        "\\n\\nhex: \\\\\\x => \\x",
        "\\n\\x",
        "\\n\\nspaced   out      text",
        "\\n\\nend\\n\\cthis should not print!",
        "this should not print!" };
```

Or, alternatively, you could run this command from a shell (assuming you export [Echo](../projects/examples/Echo/src/com/imperio/examples/echo/p08complete/Echo.java) to a runnable Jar file):

```
java -jar Echo.jar -e \
'newline \\\\n:' \
    \\n \
"\\nthree quotes:" \
    \"\"\" \
"\\n\\nmid-string tab:" \
    te\\txt \
\\n\\nbackslash: \
    \\ \
\\n\\ndouble-to-single-backslash: \
    \\\\ \
\\n\\nalert/bell: \
    alert\\abell \
\\n\\nescape: \
    esc\\eape \
\\n\\nbackspace: \
    unfinished\\b \
"\\n\\nform feed:" \
    form\\ffeed \
"\\n\\ncarriage return:" \
    "\\ncarriage\\rcarriage return" \
"\\n\\nvertical tab: \
    vertical\\vtab" \
'\n\noctal: 107 162 157 157 164 41 041 1234 300 \\\\0Z 1 => Groot!!S4Ã\0Z' \
    \\n\\0107\\0162\\0157\\0157\\0164\\041\\0041\\01234\\0300\\0Z\\01 \
'\n\noctal: \\\\0Z => Z' \
    \\n\\0Z \
'\n\noctal: \\\\0 => ' \
    \\n\\0 \
'\n\nhex: 47 52 6F 6f 74 21 9 2d 09 40 c0 \\\\xZ => Groot!-@Ã\\xZ' \
    \\n\\x47\\x52\\x6F\\x6f\\x74\\x21\\x9\\x2d\\x09\\x40\\xc0\\xZ \
'\n\nhex: \\\\xZ => \\xZ' \
    \\n\\xZ \
'\n\nhex: \\\\x => \\x' \
    \\n\\x \
'\n\nspaced   out      text' \
    '\n\nend\n\cthis should not print!' \
'this should not print!'
```

Either one should produce output similar to this:

```
newline \\n: 
 
three quotes: """ 

mid-string tab: te      xt 

backslash: \ 

double-to-single-backslash: \ 

alert/bell: alertbell 

escape: escpe 

backspace: unfinishe 

form feed: form
               feed 

carriage return: 
carriage return 

vertical tab: vertical
                      tab 

octal: 107 162 157 157 164 41 041 1234 300 \Z 1 => Groot!!S4ÀZ 
Groot!!S4ÀZ 

octal: \\0Z => Z 
Z 

octal: \\0 =>  
 

hex: 47 72 6F 6f 74 21 9 2d 09 40 c0 \\xZ => Groot!     -       @À\xZ 
Groot!  -       @À\xZ 

hex: \\xZ => \xZ 
\xZ 

hex: \\x => \x 
\x 

spaced   out      text 

end
```

(The output will be slightly different if running from Eclipse.)

Use /bin/echo to verify that this is correct:

```
/bin/echo -e \
'newline \\\\n:' \
    \\n \
"\\nthree quotes:" \
    \"\"\" \
"\\n\\nmid-string tab:" \
    te\\txt \
\\n\\nbackslash: \
    \\ \
\\n\\ndouble-to-single-backslash: \
    \\\\ \
\\n\\nalert/bell: \
    alert\\abell \
\\n\\nescape: \
    esc\\eape \
\\n\\nbackspace: \
    unfinished\\b \
"\\n\\nform feed:" \
    form\\ffeed \
"\\n\\ncarriage return:" \
    "\\ncarriage\\rcarriage return" \
"\\n\\nvertical tab: \
    vertical\\vtab" \
'\n\noctal: 107 162 157 157 164 41 041 1234 300 \\\\0Z 1 => Groot!!S4Ã\0Z' \
    \\n\\0107\\0162\\0157\\0157\\0164\\041\\0041\\01234\\0300\\0Z\\01 \
'\n\noctal: \\\\0Z => Z' \
    \\n\\0Z \
'\n\noctal: \\\\0 => ' \
    \\n\\0 \
'\n\nhex: 47 52 6F 6f 74 21 9 2d 09 40 c0 \\\\xZ => Groot!-@Ã\\xZ' \
    \\n\\x47\\x52\\x6F\\x6f\\x74\\x21\\x9\\x2d\\x09\\x40\\xc0\\xZ \
'\n\nhex: \\\\xZ => \\xZ' \
    \\n\\xZ \
'\n\nhex: \\\\x => \\x' \
    \\n\\x \
'\n\nspaced   out      text' \
    '\n\nend\n\cthis should not print!' \
'this should not print!'
````

This should produce output similar to this:

```
newline \\n: 
 
three quotes: """ 

mid-string tab: te      xt 

backslash: \ 

double-to-single-backslash: \ 

alert/bell: alertbell 

escape: escpe 

backspace: unfinishe 

form feed: form
               feed 

carriage return: 
carriage return 

vertical tab:     vertical
                          tab 

octal: 107 162 157 157 164 41 041 1234 300 \\0Z 1 => Groot!!S4ÃZ 
Groot!!S4�Z 

octal: \\0Z => Z 
Z 

octal: \\0 =>  
 

hex: 47 52 6F 6f 74 21 9 2d 09 40 c0 \\xZ => Groot!-@Ã\xZ 
GRoot!  -       @�\xZ 

hex: \\xZ => \xZ 
\xZ 

hex: \\x => \x 
\x 

spaced   out      text 

end
```

# E9: Rush - Miscellaneous advanced features

Project: [Rush](../projects/examples/Rush)

Package: [com.options.examples.rush](../projects/examples/Rush/src/com/options/examples/rush)

The `Echo` command doesn't make full use of the available Imperio features, so this example is an app that doesn't do much, but does exhibit some of the more complex functionality Imperio supports.

Aside from running the example code directly from Eclipse, the easiest way to run it is to export Rush as a runnable jar (right click on the Rush project -> Export -> Java / Runnable Jar File).

`java -jar Rush.jar -h`

## Custom Error Handler

This Rush app demonstrates using a custom `ErrorHandler` to deal with errors encountered when processing the options. Simply set the `ImperioAppSpec.errorHandler` field.

## Logger

This app demonstrates enabling Imperio logging by simply setting the `ImperoAppSpec.logger` field to an instance of the included `ImpLoggerPrint` class. `ImpLoggerPrint` is a simple class that just prints whatever it's given to `stdout` and adds a newline for each log message. If given a `Throwable`, it logs a full stacktrace to `stdout`.

## Character-focused Usage

This app uses the built-in `UsageGenChars` usage generator, instead of the default `UsageGenNames`. As the name implies, the difference is that `UsageGenChars` includes the options' characters in the usage string, only including names if an option doesn't have a character. `UsageGenNames` does the opposite, prioritizing names over characters.

## Non-Flag Options

This app also demonstrates the use of non-flag options. The `LAST_LINE` (`--last-line`) option is an example of an `ARG` option, which takes an argument. The `VERBOSITY` (`--verbose`) option is an example of an `INCREMENTER` option, which maintains an integer value which is incremented by one each time the option is specified. The `QUIET` (`--quiet`) option is an example of a `DECREMENTER` option, which does the opposite and reduces the integer value each time the option is specified. The `SECOND_VERSE` (`-2`) option simply serves to show that options don't have to be letters. The `ARG_I`, `FLAG_E`, and `FLAG_U` options merely exist to showcase some error handling.

### Linked Incrementer/Decrementer Options

Quiet and verbose are also an example of a linked pair of `INCREMENETER`/`DECREMENTER` options. Unlike linked `FLAG` options, each of which sets some internal boolean to the opposite of the other, linked `INCREMENTER`/`DECREMENTER` options perform their normal function, and then override the linked option's value with the new value. Put simply, this means the internal integers of the two linked options always have the same value.

# Deprecated Options

Quiet is also an example of a deprecated option. By default, deprecated options don't show up in the usage string, and are tagged with `"(DEPRECATED)"` in the help text. They are still processed normally.

# Option Callbacks

Quiet and verbose also demonstrate the callback functionality. If the `OptionSpec.callback` field is set, then whenever the option is processed, the `OptionCallback.callback()` function will be invoked. It will be provided with the app, the option (with the new value after processing the option), and the old value that the option held before this instance of the option was processed. Application developers can implement the `OptionCallback` interface to do virtually anything when an option is encountered.

# Argument Fix Suggestions

If you try running this app with the `-quiet` argument, you'll see some of Imperio's more helpful error handling. Because `-i` takes an argument, the error message will suggest that you try `-I <arg> -quet` instead. It will also suggest that you try `--quiet`, supposing that the missing hyphen is simply a typo. Since `-t` is not a valid option, another error message will appear which also suggests trying `--quiet`.

