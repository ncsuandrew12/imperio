package com.imperio.examples.rush;

import com.imperio.ImperioApp;
import com.imperio.Option;
import com.imperio.OptionCallback;

public class VerbCallback implements OptionCallback {

    @Override
    public void callback(ImperioApp impApp, Option opt, Object oldVal) {
        System.out.printf("Incremented verbosity from %d to %d.\n", oldVal,
                opt.getValue());
    }

}
