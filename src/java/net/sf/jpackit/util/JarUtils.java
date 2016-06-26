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

import java.net.URISyntaxException;
import java.net.URLDecoder;
import net.sf.jpackit.exception.NotJarExecutionException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * Misc utilities to work with jar archives.
 *
 * @author Ignat Aleksandrov
 * @version $Id: JarUtils.java,v 1.10 2005/02/23 10:21:21 kamil_sham Exp $
 */
public final class JarUtils {

    private JarUtils() {

    }

    /**
     * Tries to determine jar file that is used to run the application.
     *
     * @return currently executing jar file,
     *         or throws an exception if application was not started as "java -jar our.jar" or
     *         some error occured
     */
    public static JarFile getExecutingJar() throws IOException, NotJarExecutionException {
        ClassLoader classLoader = JarUtils.class.getClassLoader();

        if (classLoader instanceof URLClassLoader) {
            URLClassLoader ucl = (URLClassLoader) classLoader;
            if (ucl.getURLs().length == 1 && ucl.getURLs()[0].toString().toLowerCase().indexOf(".jar") != -1) {
                //this is jar file executtion, processing
                File file = new File(URLDecoder.decode(ucl.getURLs()[0].getFile(), System.getProperty("file.encoding")));
                if (file.exists() && file.canRead()) {
                    return new JarFile(file);
                } else {
                    throw new NotJarExecutionException("Couldn't read jar file: '" + file.getAbsoluteFile() + "'. It doesn't exists, or cannot be read.");
                }
            } else {
                throw new NotJarExecutionException("URL class loader is based on more then one jar or is based on non-jar file.");
            }
        } else {
            throw new NotJarExecutionException("Class loader is not URLClassLoader");
        }

    }

    /**
     * Add file to jar with given path
     *
     * @param jar  output stream to write to jar file
     * @param file input stream to read from added file
     * @param path internal (inside jar) path, where new file will be placed
     */
    public static void addFileToJar(JarOutputStream jar, InputStream file, String path) throws IOException {
        byte[] buffer = new byte[8192];
        JarEntry entry = new JarEntry(path);
        jar.putNextEntry(entry);

        int n;
        while ((n = file.read(buffer)) > 0) {
            jar.write(buffer, 0, n);
        }
    }

    /**
     * Checks that file is valid jar archive
     *
     * @param path full path to file
     * @return true, if file is valid jar, false otherwise
     */
    public static boolean checkJarValidity(String path) {
        try {
            JarFile jar = new JarFile(path);
            // just to check that archive is valid
            jar.size();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

/*
 * $Log: JarUtils.java,v $
 * Revision 1.10  2005/02/23 10:21:21  kamil_sham
 * Changed to version 1.0.1, fixed bug with empty spaces in path, reported by Riyad Kalla.
 *
 * Revision 1.9  2005/01/27 11:44:56  ignath
 * Added error handling to JarScanner
 *
 * Revision 1.8  2005/01/24 13:23:36  ignath
 * Refactored code
 *
 * Revision 1.7  2005/01/21 14:20:18  ignath
 * Changed main class search in JarScanner to ASM implementation (to avoid NoClassDefFoundError with loadClass)
 *
 * Revision 1.6  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.5  2005/01/20 14:38:33  ignath
 * Added function to search for executable classes in jar
 *
 * Revision 1.4  2005/01/14 08:39:11  ignath
 * Added application exceptions.
 * All exceptions during jar package building are now catched and displayed to user as text error messages.
 *
 * Revision 1.3  2005/01/13 11:21:25  ignath
 * Moved hardcoded message strings to properties file
 *
 * Revision 1.2  2005/01/12 15:04:26  ignath
 * Added jars validation and main class validation to BuildConfigurationValidator.
 * Replaced ArrayList to interface List where it was appropriate.
 *
 * Revision 1.1  2005/01/12 09:25:48  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:04  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/31 12:32:55  ignath
 * Fixed minor bugs
 *
 * Revision 1.2  2004/12/29 12:38:12  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.1  2004/12/28 15:51:22  ignath
 * Developing of module that packages application in one executable jar file
 *
 */