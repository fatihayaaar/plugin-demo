package com.fayardev.plugindemo.loader.classremover;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class MyClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        return new byte[0];
    }

    public static byte[] getClassBytecode(Class<?> clazz) {
        return new byte[0];
    }
}
