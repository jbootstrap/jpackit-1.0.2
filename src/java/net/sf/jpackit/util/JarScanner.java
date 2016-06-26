/*
 * Copyright 2004-2005 Ignat Aleksandrov, Kamil Shamgunov
 *
 * Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.jpackit.util;

import net.sf.jpackit.Constants;
import net.sf.jpackit.notification.AbstractJPackitEventProducer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TreeClassAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Jar scanner that searches jar for classes with main methods
 *
 * @author Kamil K. Shamgunov
 */

// TODO: Move this class to another package (with more appropriate name)

public class JarScanner extends AbstractJPackitEventProducer implements Constants {
    /**
     * Resource Bundle for string message resources
     */
    // TODO: make it refactor friendly - do not hardcode class package in string - maybe use class?
    private ResourceBundle rb = ResourceBundle.getBundle("net.sf.jpackit.util.message");

    private static final String CLASS_EXTENSION = ".class";
    private static final String MAIN_METHOD_NAME = "main";
    private static final String MAIN_METHOD_DESC = "([Ljava/lang/String;)V";
    private static final int MAIN_METHOD_ACC_MASK = org.objectweb.asm.Constants.ACC_PUBLIC + org.objectweb.asm.Constants.ACC_STATIC;
    private String jarPath;
    private List executables;

    /**
     * Creates a new instance of JarScanner
     *
     * @param jarPath     full path to jar file
     * @param executables list that will be filled with  full class names represented as strings
     */

    public JarScanner(String jarPath, List executables) {
        this.jarPath = jarPath;
        this.executables = executables;
    }

    private boolean isExecutable(InputStream is) throws IOException {
        ClassReader classReader = new ClassReader(is);
        TreeClassAdapter classAdapter = new TreeClassAdapter(null);
        classReader.accept(classAdapter, true);

        List methods = classAdapter.classNode.methods;
        for (int i = 0; i < methods.size(); ++i) {
            MethodNode method = (MethodNode) methods.get(i);
            if (method.name.equals(MAIN_METHOD_NAME) &&
                    method.desc.equals(MAIN_METHOD_DESC) &&
                    (method.access & MAIN_METHOD_ACC_MASK) == MAIN_METHOD_ACC_MASK)
                return true;
        }

        return false;
    }

    private void scan() throws IOException {
        JarFile jarFile = new JarFile(jarPath);
        List classes = new ArrayList();

        Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = (JarEntry) entries.nextElement();
            String name = entry.getName();
            if (!entry.isDirectory() && name.endsWith(CLASS_EXTENSION)) {
                classes.add(entry);
            }
        }

        double onePercent = ((double) 100) / (double) classes.size();

        for (int i = 0; i < classes.size(); i++) {
            JarEntry entry = (JarEntry) classes.get(i);
            String name = entry.getName();
            name = name.substring(0, name.length() - CLASS_EXTENSION.length()).replace('/', '.');

            fireStateChanged("Processing " + name);
            InputStream is = jarFile.getInputStream(entry);
            if (isExecutable(is)) {
                executables.add(name);
            }
            try {
                fireProgressInfoChanged((int) (onePercent * i));
            } catch (InterruptedException e) {
                executables.clear();
                fireActionInterrupted();
                return;
            }
        }
        fireStateChanged("Done");
        try {
            fireProgressInfoChanged(100);
        } catch (InterruptedException e) {
            fireActionInterrupted();
            executables.clear();
            return;
        }
    }

    /**
     * Lists all executable classes that are included in given jar.
     * Class is executable if it contains correct "main" method
     */
    public void process() throws IOException {
        if (!JarUtils.checkJarValidity(jarPath)) {
            cancelAction();
            fireErrorMessageChanged(MiscUtils.getResourceString(rb, "error.bad.main.jar", jarPath));
            return;
        }

        try {
            scan();
        } catch (Throwable t) {
            t.printStackTrace();
            fireErrorMessageChanged(rb.getString("error.unknown"));
        }
    }
}

/*
 * $Log: JarScanner.java,v $
 * Revision 1.6  2005/01/27 11:58:06  ignath
 * Added error handling to JarScanner
 *
 * Revision 1.5  2005/01/27 11:44:56  ignath
 * Added error handling to JarScanner
 *
 * Revision 1.4  2005/01/24 18:32:50  kamil_sham
 * fixed build.xml to leave command line client in build directory. Fixed class file comments, added javadocs
 *
 */