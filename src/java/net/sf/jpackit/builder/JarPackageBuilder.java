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

package net.sf.jpackit.builder;

import net.sf.jpackit.Constants;
import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.exception.NotJarExecutionException;
import net.sf.jpackit.notification.AbstractJPackitEventProducer;
import net.sf.jpackit.util.FileUtils;
import net.sf.jpackit.util.JarUtils;
import net.sf.jpackit.util.MiscUtils;

import java.io.*;
import java.util.*;
import java.util.jar.*;

/**
 * Class responsible for building jar packages for application
 *
 * @author Ignat Aleksandrov
 * @version $Id: JarPackageBuilder.java,v 1.11 2005/01/24 13:23:27 ignath Exp $
 */
public class JarPackageBuilder extends AbstractJPackitEventProducer implements Constants {
    /**
     * Resource Bundle for string message resources
     */
    // TODO: make it refactor friendly - do not hardcode class package in string - maybe use class?
    private ResourceBundle rb = ResourceBundle.getBundle("net.sf.jpackit.builder.message");

    /**
     * Name of manifest file attributes
     */
    private static final String MF_MAIN_CLASS = "Main-Class";

    /**
     * Main class in bootstrap application
     */
    private static final String BOOTSTRAP_MAIN_CLASS = "net.sf.jpackit.pkg.jar.Bootstrapper";

    // TODO To think out another way to set package and class name - to be refactor friendly
    /**
     * Name of packages and classes that should be included in executable jar
     */
    private static final String[] BS_PACKAGES = {"net.sf.jpackit.pkg", "net.sf.jpackit.exception"};
    private static final String[] BS_CLASSES = {"net.sf.jpackit.Constants", "net.sf.jpackit.util.JarUtils"};

    /*
        TODO It will be better to minimize number of classes required for bootstrap part.
        Maybe even code redundancy will be OK - to not include utils classes...
    */

    private BuildConfiguration config;
    private JarOutputStream jarFile;

    public void buildPackage(BuildConfiguration config) {
        this.config = config;
        try {
            buildJar();
        } catch (IOException e) {
            // TODO: make more robust logging
            e.printStackTrace();
            clean();
            fireErrorMessageChanged(rb.getString("error.io"));
        } catch (NotJarExecutionException e) {
            e.printStackTrace();
            clean();
            fireErrorMessageChanged(rb.getString("error.not.jar"));
        } catch (InterruptedException e) {
            clean();
            fireActionInterrupted();
        } catch (Throwable t) {
            t.printStackTrace();
            clean();
            fireErrorMessageChanged(rb.getString("error.unknown"));
        }
    }

    /**
     * Extract classes which are required for execution of application
     * and add them to a new jar
     *
     * @param jar currently executing jar (which contains bootstrap classes)
     */
    private void addBootstrapFiles(JarFile jar) throws IOException, InterruptedException {
        fireStateChanged(rb.getString("state.bootstrap.start"));
        fireProgressInfoChanged(1);

        for (int i = 0; i < BS_PACKAGES.length; i++) {
            String pkg = BS_PACKAGES[i];
            pkg = pkg.replace('.', '/');
            addBootstrapPackage(jar, pkg);
        }

        for (int i = 0; i < BS_CLASSES.length; i++) {
            String cl = BS_CLASSES[i];
            cl = cl.replace('.', '/') + ".class";
            addBootstrapClass(jar, cl);
        }
    }

    private void addBootstrapPackage(JarFile jar, String packagePath) throws IOException {
        Enumeration entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = (JarEntry) entries.nextElement();
            String name = entry.getName();
            if (name.startsWith(packagePath)) {
                addBootstrapEntry(jar, entry);
            }
        }
    }

    private void addBootstrapClass(JarFile jar, String pathToClass) throws IOException {
        JarEntry entry = jar.getJarEntry(pathToClass);
        addBootstrapEntry(jar, entry);
    }

    private void addBootstrapEntry(JarFile jar, JarEntry entry) throws IOException {
        String name = entry.getName();
        InputStream is = null;
        try {
            is = jar.getInputStream(entry);
            JarUtils.addFileToJar(jarFile, is, name);
        } finally {
            if (is != null)
                is.close();
        }
    }

    private void createJarFile() throws IOException, InterruptedException {
        fireStateChanged(rb.getString("state.jar.start"));
        fireProgressInfoChanged(0);

        String jarFilePath = config.getPackageFileFullPath();
        Manifest manifest = buildManifest();
        jarFile = new JarOutputStream(new FileOutputStream(jarFilePath), manifest);
    }

    private void closeJarFile() throws IOException, InterruptedException {
        jarFile.close();
        fireStateChanged(rb.getString("state.jar.end"));
        fireProgressInfoChanged(100);
    }

    /**
     * Create jar archive with necessary files and libraries
     */
    private void buildJar() throws IOException, NotJarExecutionException, InterruptedException {
        JarFile thisJar = JarUtils.getExecutingJar();
        createJarFile();

        addBootstrapFiles(thisJar);
        addLibraries();

        closeJarFile();
    }

    /**
     * Collect all manifest attributes and their values for jar archive
     *
     * @return map with attribute name (as key) and attribute value
     */
    private Map getManifestAttributes() {
        Map attrs = new HashMap();
        attrs.put(MF_MAIN_CLASS, BOOTSTRAP_MAIN_CLASS);
        attrs.put(MANIFEST_MAIN_CLASS, config.getMainClass());
        attrs.put(MANIFEST_PREDEF_ARGS, config.getPredefArgs());
        attrs.put(MANIFEST_DEFAULT_ARGS, config.getDefaultArgs());
        String encType = config.getEncType();
        attrs.put(MANIFEST_ENC_TYPE, encType);
        if (!encType.equals(BuildConfiguration.NO_ENC_TYPE))
            attrs.put(MANIFEST_PHASHER_TYPE, config.getHasherType());
        return attrs;
    }

    /**
     * Builds manifest for the jar archive with all necessary attributes
     *
     * @return manifest
     */
    private Manifest buildManifest() {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

        Map attrs = getManifestAttributes();
        Iterator it = attrs.keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            String value = (String) attrs.get(name);
            manifest.getMainAttributes().putValue(name, value);
        }

        return manifest;
    }

    /**
     * Add jar libraries to the special folder in a single jar
     */
    private void addLibraries() throws IOException, InterruptedException {
        List jars = config.getDependantJars();
        jars.add(0, config.getMainJar());
        String baseDir = FileUtils.appendFileSeparatorIfNecessary(config.getBaseDir());
        for (int i = 0; i < jars.size(); i++) {
            fireStateChanged(MiscUtils.getResourceString(rb, "state.addjar.start", jars.get(i)));
            double progress = 10d + 90d / jars.size() * i;
            fireProgressInfoChanged((int) progress);

            String location = baseDir + jars.get(i);
            File lib = new File(location);
            FileInputStream fis = new FileInputStream(lib);
            try {
                String jarPath = Constants.JAR_LIBRARIES_PATH + lib.getName();
                JarUtils.addFileToJar(jarFile, fis, jarPath);
            } finally {
                fis.close();
            }
        }
    }

    private void deleteJarFile() {
        if (jarFile == null)
            return;

        try {
            jarFile.close();
        } catch (IOException e) {
            // just ignore
        }
        String jarFilePath = config.getPackageFileFullPath();
        File jar = new File(jarFilePath);
        jar.delete();
    }

    /**
     * Process all cleaning actions necessary when building process is interrupted
     */
    private void clean() {
        deleteJarFile();
    }
}

/*
 * $Log: JarPackageBuilder.java,v $
 * Revision 1.11  2005/01/24 13:23:27  ignath
 * Refactored code
 *
 * Revision 1.10  2005/01/21 15:44:47  kamil_sham
 * Set debug level in build.xml to 'true', fixed bug in BuildConfigurationValidation's checkMainClass method, removed addition of all net.sf.jpackit.utills.* classes into final package
 *
 * Revision 1.9  2005/01/20 20:43:09  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.8  2005/01/20 14:49:31  ignath
 * Fixed bug with closing of jar file stream when the latter is null
 *
 * Revision 1.7  2005/01/20 12:39:22  ignath
 * Added "main jar" field to build configuration
 *
 * Revision 1.6  2005/01/14 11:19:06  ignath
 * Added possibility to cancel building process
 *
 * Revision 1.5  2005/01/14 08:39:06  ignath
 * Added application exceptions.
 * All exceptions during jar package building are now catched and displayed to user as text error messages.
 *
 * Revision 1.4  2005/01/13 12:25:07  ignath
 * Added checking for empty field values of BuildConfig in BuildConfigurationValidator
 *
 * Revision 1.3  2005/01/13 11:44:00  ignath
 * Moved hardcoded message strings to properties file
 *
 * Revision 1.2  2005/01/12 15:04:08  ignath
 * Added jars validation and main class validation to BuildConfigurationValidator.
 * Replaced ArrayList to interface List where it was appropriate.
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.7  2005/01/11 12:11:58  ignath
 * Added good file header and footer
 *
 * Revision 1.6  2005/01/11 09:36:09  ignath
 * Refactored build configuration type to enumeration class.
 * Fixed bug: first letter of package file extension was capitalized.
 *
 * Revision 1.5  2004/12/31 12:32:54  ignath
 * Fixed minor bugs
 *
 * Revision 1.4  2004/12/29 14:22:05  ignath
 * Added notification messages (and percent) update to JarPackageBuilder
 *
 * Revision 1.3  2004/12/29 13:09:13  kamil_sham
 * Added PropertyChangeListener functionality
 *
 * Revision 1.2  2004/12/29 12:38:10  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.1  2004/12/28 15:51:20  ignath
 * Developing of module that packages application in one executable jar file
 *
 */