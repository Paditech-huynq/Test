package com.pshop.app;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class GlobalBus {
    private static Bus sBus;

    GlobalBus() {
    }

    static Bus getBus() {
        if (sBus == null)
            sBus = new Bus(ThreadEnforcer.ANY);
        return sBus;
    }
}
