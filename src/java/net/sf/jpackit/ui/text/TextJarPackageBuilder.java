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

package net.sf.jpackit.ui.text;

import net.sf.jpackit.builder.JarPackageBuilder;
import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.config.props.PropertiesBuildConfigurationManager;
import net.sf.jpackit.config.validator.BuildConfigurationError;
import net.sf.jpackit.config.validator.BuildConfigurationValidator;
import net.sf.jpackit.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * Text based jar package builder
 *
 * @author Kamil K. Shamgunov
 */

public class TextJarPackageBuilder {
    /**
     * Creates a new instance of TextJarPackageBuilder
     */
    public TextJarPackageBuilder() {
    }

    public static void main(String args[]) {
        String configPath;
        String baseDir = null;
        String packageName = null;
        String mainJar = null;

        if (args.length == 1) {
            configPath = args[0];
        } else if (args.length == 4) {
            configPath = args[0];
            baseDir = args[1];
            packageName = args[2];
            mainJar = args[3];
        } else {
            printUsage();
            return;
        }

        try {
            PropertiesBuildConfigurationManager pbcm = new PropertiesBuildConfigurationManager();
            File file = new File(configPath);
            if (!file.exists()) {
                System.out.println("File " + file.getAbsolutePath() + " is not found");
                return;
            }
            if (!file.canRead()) {
                System.out.println("File " + file.getAbsolutePath() + " coudn't be read.");
                return;
            }

            BuildConfiguration bc;
            try {
                FileInputStream fis = new FileInputStream(file);
                bc = pbcm.load(fis);
            } catch (Exception e) {
                System.out.println("Confiruration cannot be initialized.");
                return;
            }

            if (bc == null) {
                System.out.println("Confiruration cannot be initialized.");
                return;
            }

            if (args.length == 4) {
                System.out.println("Base dir: " + baseDir);
                bc.setBaseDir(baseDir);
                System.out.println("Package name: " + packageName);
                bc.setPackageName(packageName);
                System.out.println("Main Jar: " + mainJar);
                bc.setMainJar(mainJar);
            }

            BuildConfigurationError[] errors = BuildConfigurationValidator.validate(bc);
            if (errors.length != 0) {
                System.out.println("Cannot build package due to next errors:");
                for (int i = 0; i < errors.length; i++) {
                    System.out.println(errors[i]);
                }
                return;
            }

            JarPackageBuilder pb = new JarPackageBuilder();
            pb.addNotificationListener(new ProgressPrinter());
            pb.buildPackage(bc);
            System.out.println("Done - 100%");
            System.out.println("Package saved in " + FileUtils.appendFileSeparatorIfNecessary(bc.getBaseDir()) + FileUtils.appendFileSeparatorIfNecessary(bc.getPackageLocation()) + bc.getPackageFileName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown error occured.");
        }
    }

    public static void printUsage() {
        System.out.println("Usage: text-jar-builder-<version>.jar config_file [base_dir package_name main_jar]");
    }
}

/*
 * $Log: TextJarPackageBuilder.java,v $
 * Revision 1.4  2005/01/24 13:23:36  ignath
 * Refactored code
 *
 * Revision 1.3  2005/01/24 07:10:18  kamil_sham
 * Added support for default ars and predefined args, fixed NPE in Validator, changed build.xml to build jar_test package using commandline jar builder
 *
 * Revision 1.2  2005/01/21 15:21:24  kamil_sham
 * Fixed several bugs in TesJarPackageBuilder, added command line arguments that helps to build packages from ant. Fixed build.xml to build swing ui into jpackit package.
 *
 * Revision 1.1  2005/01/21 13:12:00  kamil_sham
 * Added command line jar package builder
 *
 */