package com.lemon.util;

import java.lang.reflect.Field;
import java.util.Enumeration;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by XiaoFeng on 2015/12/20.
 */
public class Scanner {

    private static Field dexField;

    static {
        try {
            dexField = PathClassLoader.class.getDeclaredField("mDexs");
            dexField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scan() {
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();
            DexFile[] dexs = (DexFile[]) dexField.get(classLoader);
            for (DexFile dex : dexs) {
                Enumeration<String> entries = dex.entries();
                while (entries.hasMoreElements()) {
                    String entry = entries.nextElement();
                    Class<?> entryClass = dex.loadClass(entry, classLoader);
                    System.out.println( "; class=" + entryClass.getName());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
