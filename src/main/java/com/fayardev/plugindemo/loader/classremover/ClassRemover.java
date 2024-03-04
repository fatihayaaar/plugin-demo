package com.fayardev.plugindemo.loader.classremover;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class ClassRemover {
    public static void unloadClass(Class<?> clazz) {
        try {
            Instrumentation instrumentation = MyAgent.getInstrumentation();
            byte[] classBytecode = MyClassTransformer.getClassBytecode(clazz);

            ClassDefinition classDefinition = new ClassDefinition(clazz, classBytecode);
            instrumentation.redefineClasses(classDefinition);

            System.out.println("Class unloaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
