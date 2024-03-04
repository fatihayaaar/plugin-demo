package com.fayardev.plugindemo.loader.classremover;

import java.lang.instrument.Instrumentation;

public class MyAgent {
    private static volatile Instrumentation globalInstrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static Instrumentation getInstrumentation() {
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent not initialized properly");
        }
        return globalInstrumentation;
    }
}
