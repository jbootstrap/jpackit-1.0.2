/*
 * Created 12.01.2005 at 15:39:26
 * ====================================================================
 *
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
package net.sf.jpackit.config.validator;

import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.util.FileUtils;
import net.sf.jpackit.util.JarUtils;
import net.sf.jpackit.util.MiscUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Validator that checks data stored in BuildConfiguration instance
 *
 * @author Ignat Aleksandrov
 * @version $Id: BuildConfigurationValidator.java,v 1.20 2005/02/22 13:51:51 ignath Exp $
 */
public class BuildConfigurationValidator {
    /**
     * Resource Bundle for string message resources
     */
    // TODO: make it refactor friendly - do not hardcode class package in string - maybe use class?
    private static ResourceBundle rb = ResourceBundle.getBundle("net.sf.jpackit.config.validator.message");

    private BuildConfigurationValidator() {

    }

    /**
     * Validates build configuration
     *
     * @param bc build configuration instance
     * @return array with build configuration errors or empty array if no errors were found
     */
    public static BuildConfigurationError[] validate(BuildConfiguration bc) {
        List errors = new ArrayList();

        errors.addAll(checkObligatoryFields(bc));

        BuildConfigurationError mainJarError = checkMainJarValidity(bc);
        if (mainJarError != null)
            errors.add(mainJarError);

        errors.addAll(checkDependantJarsValidity(bc));

        errors.addAll(checkForDuplicateNames(bc));

        BuildConfigurationError error;

        if (mainJarError == null) {
            error = checkMainClass(bc);
            if (error != null)
                errors.add(error);
        }

        return (BuildConfigurationError[]) errors.toArray(new BuildConfigurationError[0]);
    }

    /**
     * Checks that main jar file represents valid readable java archive
     *
     * @param bc
     * @return list of found errors
     */
    private static List checkDependantJarsValidity(BuildConfiguration bc) {
        List errors = new ArrayList();

        List paths = buildDependantJarPaths(bc);
        for (int i = 0; i < paths.size(); i++) {
            String path = (String) paths.get(i);
            BuildConfigurationError error = checkDependantJarValidity(path);
            if (error != null)
                errors.add(error);
        }

        return errors;
    }

    /**
     * Checks that every jar file represents valid readable java archive
     *
     * @param bc
     * @return found error or null if no error occured
     */
    private static BuildConfigurationError checkMainJarValidity(BuildConfiguration bc) {
        if (MiscUtils.isEmpty(bc.getMainJar()))
            return null;
        String path = FileUtils.appendFileSeparatorIfNecessary(bc.getBaseDir()) + bc.getMainJar();
        BuildConfigurationError error = checkMainJarValidity(path);

        return error;
    }

    private static BuildConfigurationError checkMainJarValidity(String path) {
        if (!JarUtils.checkJarValidity(path))
            return createError("error.bad.main.jar", FileUtils.getCanonicalPath(path));

        return null;
    }

    private static BuildConfigurationError checkDependantJarValidity(String path) {
        if (!JarUtils.checkJarValidity(path))
            return createError("error.bad.dependant.jar", FileUtils.getCanonicalPath(path));

        return null;
    }

    private static List buildDependantJarPaths(BuildConfiguration bc) {
        List jars = bc.getDependantJars();
        List paths = new ArrayList();

        for (int i = 0; i < jars.size(); i++) {
            paths.add(FileUtils.appendFileSeparatorIfNecessary(bc.getBaseDir()) + jars.get(i));
        }
        return paths;
    }

    /**
     * Checks whether main jar file contain main class and that this class is really executable
     *
     * @param bc
     * @return
     */
    private static BuildConfigurationError checkMainClass(BuildConfiguration bc) {
        if (MiscUtils.isEmpty(bc.getMainJar()) || MiscUtils.isEmpty(bc.getMainClass()))
            return null;

        List paths = buildDependantJarPaths(bc);
        URL urls[] = new URL[paths.size() + 1];
        try {
            urls[0] = new URL("file:" + FileUtils.appendFileSeparatorIfNecessary(bc.getBaseDir()) + bc.getMainJar());
            for (int i = 1; i < urls.length; i++) {
                urls[i] = new URL("file:" + paths.get(i - 1));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ClassLoader classLoader = new URLClassLoader(urls);
        Class mainClass;
        Method mainMethod;
        try {
            mainClass = classLoader.loadClass(bc.getMainClass());
            mainMethod = mainClass.getMethod("main", new Class[]{String[].class});
        } catch (ClassNotFoundException e) {
            return createError("error.class.not.found", bc.getMainClass());
        } catch (NoClassDefFoundError e) {
            return createError("error.dependancy.not.found", e.getMessage());
        } catch (NoSuchMethodException e) {
            return createError("error.main.method.not.found", bc.getMainClass());
        }

        if (mainMethod == null)
            return createError("error.main.method.not.found", bc.getMainClass());

        return null;
    }

    /**
     * Checks that obligatory fields in build configuration are not empty
     *
     * @param bc build configuration
     * @return list of found errors
     */
    private static List checkObligatoryFields(BuildConfiguration bc) {
        List errors = new ArrayList();
        if (MiscUtils.isEmpty(bc.getBaseDir())) {
            errors.add(createError("error.basedir.empty"));
        }
        if (MiscUtils.isEmpty(bc.getMainJar())) {
            errors.add(createError("error.main.jar.empty"));
        }
        if (MiscUtils.isEmpty(bc.getMainClass())) {
            errors.add(createError("error.main.class.empty"));
        }
        if (MiscUtils.isEmpty(bc.getPackageLocation())) {
            errors.add(createError("error.package.location.empty"));
        }
        if (MiscUtils.isEmpty(bc.getPackageName())) {
            errors.add(createError("error.package.name.empty"));
        }
        return errors;
    }

    private static BuildConfigurationError createError(String key) {
        String message = rb.getString(key);
        return new BuildConfigurationError(message);
    }

    private static BuildConfigurationError createError(String key, Object param1) {
        String message = MiscUtils.getResourceString(rb, key, param1);
        return new BuildConfigurationError(message);
    }

    private static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }

    /**
     * Checks that dependant jars and main jar all have unique names.
     * There should not be two jars with equal names (even in different directories)
     *
     * @param bc build configuration
     * @return list of found errors
     */
    private static List checkForDuplicateNames(BuildConfiguration bc) {
        List errors = new ArrayList();
        List dependants = bc.getDependantJars();
        if (dependants.size() == 0)
            return errors;

        Map names = new HashMap();
        String name = (String) dependants.get(0);
        name = getFileName(name);

        names.put(name, "");

        for (int i = 1; i < dependants.size(); i++) {
            name = (String) dependants.get(i);
            name = getFileName(name);
            if (names.containsKey(name))
                errors.add(createError("error.dependancy.duplicate.name", name));
            else
                names.put(name, "");
        }

        String mainName = getFileName(bc.getMainJar());
        if (names.containsKey(mainName))
            errors.add(createError("error.main.duplicate.name", mainName));

        return errors;
    }
}

/*
 * $Log: BuildConfigurationValidator.java,v $
 * Revision 1.20  2005/02/22 13:51:51  ignath
 * Fixed bug in checkForDuplicateNames()
 *
 * Revision 1.19  2005/02/22 10:02:11  ignath
 * All paths are shown to user as canonical paths
 *
 * Revision 1.18  2005/01/28 11:29:29  ignath
 * Fixed NullPointer exception for empty configuration
 *
 * Revision 1.17  2005/01/28 07:18:23  kamil_sham
 * Fixed bug that prevented BuildConfigurationValidator to work on linux
 *
 * Revision 1.16  2005/01/27 11:44:54  ignath
 * Added error handling to JarScanner
 *
 * Revision 1.15  2005/01/27 11:01:04  ignath
 * Added new validation: names of all jars (including main jar) should be different.
 *
 * Revision 1.14  2005/01/25 15:26:40  ignath
 * Added new validation condition: main jar should not be included in list of dependant jars
 *
 * Revision 1.13  2005/01/24 13:23:33  ignath
 * Refactored code
 *
 * Revision 1.12  2005/01/24 07:10:17  kamil_sham
 * Added support for default ars and predefined args, fixed NPE in Validator, changed build.xml to build jar_test package using commandline jar builder
 *
 * Revision 1.11  2005/01/21 15:44:47  kamil_sham
 * Set debug level in build.xml to 'true', fixed bug in BuildConfigurationValidation's checkMainClass method, removed addition of all net.sf.jpackit.utills.* classes into final package
 *
 * Revision 1.10  2005/01/21 14:41:08  ignath
 * Changed main class search for BuildConfigurationValidator using all dependant jars
 *
 * Revision 1.9  2005/01/20 20:58:51  kamil_sham
 * Fixed bug in checkMainClass
 *
 * Revision 1.8  2005/01/20 14:37:34  ignath
 * Fixed "main jar" field validation
 *
 * Revision 1.7  2005/01/20 12:39:25  ignath
 * Added "main jar" field to build configuration
 *
 * Revision 1.6  2005/01/14 08:39:10  ignath
 * Added application exceptions.
 * All exceptions during jar package building are now catched and displayed to user as text error messages.
 *
 * Revision 1.5  2005/01/13 15:28:37  kamil_sham
 * Fixed bug with retrieving manifest for checking jar file validity, not all jar files has manifests.
 *
 * Revision 1.4  2005/01/13 12:25:20  ignath
 * Added checking for empty field values of BuildConfig in BuildConfigurationValidator
 *
 * Revision 1.3  2005/01/13 11:21:22  ignath
 * Moved hardcoded message strings to properties file
 *
 * Revision 1.2  2005/01/12 15:04:25  ignath
 * Added jars validation and main class validation to BuildConfigurationValidator.
 * Replaced ArrayList to interface List where it was appropriate.
 *
 * Revision 1.1  2005/01/12 12:45:57  ignath
 * Started developing build configuration validator
 *
 */