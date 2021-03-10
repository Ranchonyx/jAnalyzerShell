package com.ranchonyx;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class CommandList {
    private final HashMap<String, Command> commandHashMap = new HashMap<String, Command>();

    private URLClassLoader cl;
    private Vector<Class> classes = new Vector<Class>();
    private Class clazz;
    private Enumeration enumeration;
    private String bytecode = "";

    public CommandList() {

        //Simple commands
        commandHashMap.put("print", args -> {
                System.out.printf(">%s", args[0]);
        });
        commandHashMap.put("quit", args -> {
            Main.running = false;
            System.err.println("Quitting.");
        });
        commandHashMap.put("clear", args -> {
            if (System.getProperty("os.name").contains("Windows")) {
                for(int i = 0; i < 50; i++) {
                    System.out.println("\b");
                    System.out.flush();
                }
            }
            else {
                System.out.print("\033\143");
            }
        });
        commandHashMap.put("help", args -> {
            System.out.println("" +
                    "print <arg>: prints <arg>\n" +
                    "quit: closes the application\n" +
                    "clear: clears screen\n" +
                    "help: this. literally this text\n" +
                    "jar <arg: normal windows file path to jar>: load jar file\n" +
                    "classes: display all classes inside loaded jar file\n" +
                    "class: select a class to analyze\n" +
                    "fields: display all fields in selected class\n" +
                    "annotations: display all annotations in selected class\n" +
                    "methods: display all method headers in selected class\n" +
                    "bytecode: disply bytecode size and plaintext\n" +
                    "saveBytecode: doesn't work properly, do not use\n");
        });

        //Select stuff commands commands
        commandHashMap.put("jar", args -> {
            try {

            if(args.length == 0) {
                System.err.println("[Error] No jar URL");
            } else {

                JarFile jar = new JarFile(args[0]);
                enumeration = jar.entries();

                cl = URLClassLoader.newInstance(new URL[]{new URL("jar:file:" + args[0] + "!/")});
                String name = jar.getName().substring(jar.getName().lastIndexOf("\\")+1);
                System.out.printf("Loaded jar: %s\n", name);

                while (enumeration.hasMoreElements()) {
                    JarEntry entry = (JarEntry) enumeration.nextElement();
                    if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                        continue;
                    }
                    String className = entry.getName().substring(0, entry.getName().length() - 6);
                    className = className.replace('/', '.');
                    classes.add(cl.loadClass(className));

                }

                adjustPrompt("[JAR]: "+name);

            }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        commandHashMap.put("class", args -> {
            for(Class c : classes) {
                if(c.getName().equals(args[0])) {
                    clazz = c;
                    System.out.println("Selected class.");
                    adjustPrompt(String.format("[CLASS]: %s", c.toString()));
                }
            }
        });

        //List stuff in selected stuff
        commandHashMap.put("classes", args -> {
            for(Class c : classes) {
                System.out.printf("Class: %s\n", c.getName());
            }
        });
        commandHashMap.put("fields", args -> {
            for(Field field : clazz.getFields()) {
                field.setAccessible(true);
                System.out.printf("Field: %s\n", field.toString());
            }
        });
        commandHashMap.put("annotations", args -> {
            for(Annotation annotation : clazz.getAnnotations()) {
                System.out.printf("Field: %s\n", annotation.toString());
            }
        });
        commandHashMap.put("methods", args -> {
            for(Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(true);
                System.out.printf("Field: %s\n", method.toString());
            }
        });

        //Misc stuff with loaded stuff
        commandHashMap.put("bytecode", args -> {

            try {
                InputStream resourceStream = clazz.getResourceAsStream('/'+clazz.getName().replace('.', '/')+".class");
                System.out.printf("Resource available: %b\n", resourceStream.available());
                bytecode = new BufferedReader(new InputStreamReader(resourceStream)).lines().parallel().collect(Collectors.joining("\n"));
                System.out.printf("Resource size: %d bytes\n",bytecode.length());
                System.out.println(String.format("---------Bytecode---------\n%s\n---------Bytecode---------", bytecode));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        commandHashMap.put("saveBytecode", args -> {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(args[0]+".class"));

                writer.write(bytecode);
                writer.close();
                System.out.printf("Saved to %s\n", args[0]+".class");

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public HashMap<String, Command> getCommandHashMap() {
        return commandHashMap;
    }
    private void adjustPrompt(String p) {
        Main.promptPrefix = p;
    }
}
