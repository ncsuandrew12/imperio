package com.imperio;

public class ErrorType {

    public static final ErrorType GENERIC = new ErrorType(1, "Generic error.");
    public static final ErrorType INVALID_OPTION =
            new ErrorType(2, "Invalid option.");
    public static final ErrorType OPTION_PARSING =
            new ErrorType(3, "Unable to parse option.");
    public static final ErrorType UNKNOWN_OPTION =
            new ErrorType(4, "Unknown option.");
    public static final ErrorType MISSING_ARG = new ErrorType(5,
            "Missing argument for " + OptionType.ARG + " option.");
    public static final ErrorType MISSING_REQ_OPTION =
            new ErrorType(6, "Missing required option.");
    public static final ErrorType IMPERIO =
            new ErrorType(255, "Error in Imperio library.");

    public final int code;
    public final String description;
    public final String msg;

    public ErrorType(int code, String description) {
        this.code = code;
        this.description = description;
        this.msg =
                String.format("Error 0x%02x=%d: %s", code, code, description);
    }

}
