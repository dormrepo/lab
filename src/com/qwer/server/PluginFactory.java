package com.qwer.server;

import com.qwer.API.Plugin;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class PluginFactory {

    static ArrayList<Plugin> getPlugins() {
        ArrayList<Plugin> rez = new ArrayList<>();
        File pluginDir = new File("plugins");
        File[] jars = pluginDir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".jar");
            }

        });

        assert jars != null;

        for (File jar : jars) {
            try {
                URL jarURL = jar.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                JarFile jf = new JarFile(jar);
                Enumeration<JarEntry> entries = jf.entries();
                while (entries.hasMoreElements()) {
                    String e = entries.nextElement().getName();
                    if (!e.endsWith(".class")) continue;
                    e = e.replaceAll("/", ".");
                    e = e.replaceAll(".class", "");
                    Class<?> plugCan = classLoader.loadClass(e);
                    Class<?>[] interfaces = plugCan.getInterfaces();
                    for (Class interf : interfaces) {
                        if (interf.getName().endsWith(".Plugin")) {
                            Class c = classLoader.loadClass(plugCan.getName());
                            Object inst = c.getDeclaredConstructor().newInstance();
                            rez.add((Plugin) inst);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        return rez;
    }
}
