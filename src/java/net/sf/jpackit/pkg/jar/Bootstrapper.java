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

package net.sf.jpackit.pkg.jar;

import net.sf.jpackit.Constants;
import net.sf.jpackit.pkg.classloader.JPackitClassLoader;
import net.sf.jpackit.pkg.classloader.url.JPackitURLStreamHandlerFactory;
import net.sf.jpackit.util.JarUtils;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Bootstrapper for jar pkg, it loads all jars and ecexutes application
 *
 * @author Kamil K. Shamgunov
 */

public class Bootstrapper implements Constants {
    private JarFile currentFile;
    private URL[] urls;
    private String mainClassName;
    private String[] defaultArgs;
    private String[] predefArgs;
//    private String encType;
//    private String pHasherType;
    private String[] args;
    private InternalJarJPackitClassloaderContext context;
    JPackitClassLoader classLoader;


    /**
     * Creates a new instance of Bootstrapper
     */
    public Bootstrapper(String args[]) throws Exception {
        this.args = args;

        //this is jar file executtion, processing
        currentFile = JarUtils.getExecutingJar();
        context = new InternalJarJPackitClassloaderContext();
        //adding cleaner task
        Runtime.getRuntime().addShutdownHook(new Cleaner(context));
        URL.setURLStreamHandlerFactory(new JPackitURLStreamHandlerFactory(context));
        listLibrariesUrls();
        parseManifest();
        executeClass();
    }

    public static void main(String args[]) throws Exception {
        new Bootstrapper(args);
    }

    private void parseManifest() throws Exception {
        Manifest manifest = currentFile.getManifest();
        mainClassName = manifest.getMainAttributes().getValue(Bootstrapper.MANIFEST_MAIN_CLASS);
        String predefArgsStr = manifest.getMainAttributes().getValue(Bootstrapper.MANIFEST_PREDEF_ARGS);
        String defaultArgsStr = manifest.getMainAttributes().getValue(Bootstrapper.MANIFEST_DEFAULT_ARGS);
        predefArgs = parseCommandLine(predefArgsStr);
        defaultArgs = parseCommandLine(defaultArgsStr);
                
//        encType  = manifest.getMainAttributes().getValue(Bootstrapper.MANIFEST_ENC_TYPE);
//        pHasherType = manifest.getMainAttributes().getValue(Bootstrapper.MANIFEST_PHASHER_TYPE);
        urls = listLibrariesUrls();
    }

    private void executeClass() throws Exception {
        classLoader = new JPackitClassLoader(urls);
        Thread.currentThread().setContextClassLoader(classLoader);
        Class mainClass;
        try {
            mainClass = classLoader.loadClass(mainClassName);
        } catch (Exception e) {
            throw new RuntimeException("Class " + mainClassName + " is not found in package.");
        }

        Method method;
        try {
            method = mainClass.getMethod("main", new Class[]{String[].class});
        } catch (Exception e) {
            throw new RuntimeException("Class " + mainClassName + " doesn't have main(String args[]) method.");
        }

        if (method != null) {
            String[] finalArgs;
            if (args == null || args.length == 0)
                finalArgs = mergeCommandLine(predefArgs, defaultArgs);
            else
                finalArgs = mergeCommandLine(predefArgs, args);
            method.invoke(null, new Object[]{finalArgs});
        } else {
            throw new RuntimeException("Class " + mainClassName + " doesn't have main(String args[]) method.");
        }
    }

    /**
     * Returns urls of libraries which are used in application
     * (including library with application code itself)
     *
     * @return urls of libraries
     */
    private URL[] listLibrariesUrls() throws MalformedURLException {
        ArrayList urls = new ArrayList();
        // Don't know the better way to list files in one directory from jar archive
        // then to list all and search by prefix :(
        Enumeration entries = currentFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = (JarEntry) entries.nextElement();
            String name = entry.getName();
            if (name.startsWith(JAR_LIBRARIES_PATH) && !entry.isDirectory() && isEntryJarFile(entry)) {
                String url = new StringBuffer(Constants.URL_JPACKIT_PROTOCOL).append(":///").
                        append(name).toString();
                urls.add(new URL(url));
            }
        }

        return (URL[]) urls.toArray(new URL[0]);
    }

    /**
     * Checks whether given jar entry represents valid jar file
     *
     * @param entry jar entry
     * @return true, if entry is valid jar archive, false otherwise
     */
    private boolean isEntryJarFile(JarEntry entry) {
        String name = entry.getName();
        // TODO: think out better way to check whether entry is real jar archive
        return (name.toLowerCase().endsWith(".jar"));
    }

    /**
     * Converts commandline from string to String[]
     *
     * @param str with commandline
     * @return array of command line options or null
     */
    public static String[] parseCommandLine(String str) {
        if (str == null || str.length() == 0)
            return null;
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        String args[] = new String[tokenizer.countTokens()];

        for (int i = 0; i < tokenizer.countTokens(); i++) {
            args[i] = tokenizer.nextToken();
        }
        return args;

    }

    /**
     * Merges two String[] arrays into one
     *
     * @param str1 string with commandline
     * @param str2 string with commandline
     * @return merged arrays
     */
    public static String[] mergeCommandLine(String[] str1, String[] str2) {
        if (str1 == null || str1.length == 0) {
            if (str2 == null || str2.length == 0)
                return new String[0];
            else {
                return str2;
            }
        } else {
            if (str2 == null || str2.length == 0)
                return str1;
            else {
                String[] arr = new String[str1.length + str2.length];
                System.arraycopy(str1, 0, arr, 0, str1.length);
                System.arraycopy(str2, 0, arr, str1.length, str2.length);
                return arr;
            }
        }
    }

}

/*
 * $Log: Bootstrapper.java,v $
 * Revision 1.7  2005/02/27 10:47:03  kamil_sham
 * Fixed bug that prevented distribution of plafs in JPackIt pakckage, added jgoodies looks.
 *
 * Revision 1.6  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.5  2005/01/24 07:10:17  kamil_sham
 * Added support for default ars and predefined args, fixed NPE in Validator, changed build.xml to build jar_test package using commandline jar builder
 *
 * Revision 1.4  2005/01/21 13:22:58  kamil_sham
 * Changed direct jar file access to getResourceAsStream
 *
 * Revision 1.3  2005/01/12 12:51:32  kamil_sham
 * Adeed cleaner task
 *
 * Revision 1.2  2005/01/12 09:38:07  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.9  2005/01/11 12:12:03  ignath
 * Added good file header and footer
 *
 * Revision 1.8  2005/01/11 10:12:25  ignath
 * Files which are loaded from META-INF/lib are verified whether they are .jar files or not.
 *
 * Revision 1.7  2004/12/29 12:38:12  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.6  2004/12/28 15:51:22  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.5  2004/12/28 13:47:40  ignath
 * Libraries are no longer enumerated in Manifest file. Now all libraries are stored in META-INF/lib folder and there is no way to change this path.
 *
 * Revision 1.4  2004/12/28 09:00:19  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */