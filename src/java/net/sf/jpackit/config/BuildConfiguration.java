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

package net.sf.jpackit.config;

import java.io.File;
import java.io.IOException;
import net.sf.jpackit.util.FileUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Build configuration. Instance of this class contains build properties
 * needed by package building process. This class is serialized and
 * deserialized using standart java serialization procedure,
 * so all configuration properties stored in it should implement
 * java.io.Serializable interface. Instance of this class is loaded
 * by net.sf.jpackit.config.BuildConfigurationManager
 *
 * @author Kamil K. Shamgunov
 * @see net.sf.jpackit.config.BuildConfigurationManager
 */

public class BuildConfiguration implements Serializable {

    public static final String NO_ENC_TYPE = "no";
    public static final String AES_ENC_TYPE = "aes";
    public static final String TDES_ENC_TYPE = "3des";

    public static final String MD5_PASSWD_HASHER = "md5";
    public static final String SHA1_PASSWD_HASHER = "sha1";
    public static final String PRNG_ENC_TYPE = "prng";

    /**
     * baseDir is directory from which relative path is evaluated
     */
    private String baseDir = "";

    /**
     * mainJar stores location of main application jar
     * (i.e. jar that contains class with main method)
     */
    private String mainJar;

    /**
     * dependantJars is list of paths to dependant jars files
     * that will be included into package
     */
    private List dependantJars = new ArrayList();

    /**
     * packageName is name of package from which packages file name will be evaluated.
     * packageFileName = packageName + "." + buildType
     */
    private String packageName = "";

    /**
     * packageLocation is directory where package will be after build process.
     * The path is relative to baseDir.
     */
    private String packageLocation = "";

    /**
     * rebuildAllJarsIntoOne indicates that all required jars will be unpacked and packed into single jar.
     */
    private boolean rebuildAllJarsIntoOne;

    /**
     * buildType is build type.
     */
    private BuildType buildType;

    /**
     * encType is package encryption type. Acceptable values is:
     * BuildConfiguration.NO_ENC_TYPE
     * BuildConfiguration.AES_ENC_TYPE
     * BuildConfiguration.TDES_ENC_TYPE
     */
    private String encType = "";

    /**
     * hasherType is hash generator for passwords. Acceptable values is:
     * BuildConfiguration.MD5_PASSWD_HASHER
     * BuildConfiguration.SHA1_PASSWD_HASHER
     * BuildConfiguration.PRNG_PASSWD_HASHER
     */
    private String hasherType="";

    /**
     * mainClass is main class that will be executed when package is started.
     */
    private String mainClass = "";

    /**
     * predefArgs is list of program arguments that will be passed to main class anyway.
     */
    private String predefArgs = "";

    /**
     * defaultArgs is list of program arguments that will be passed to main class when user
     * executes package without arguments.
     */
    private String defaultArgs = "";

    /**
     * Storage for custom configuration propeties
     */
    private HashMap _map = new HashMap();

    /**
     * Creates a new instance of BuildConfiguration
     */
    public BuildConfiguration() {
    }

    /**
     * Obtain custom property by given propety name
     *
     * @param name property name
     * @return propety
     */
    public Object getProperty(String name) {
        return _map.get(name);
    }

    /**
     * Set custom property by given propety name
     *
     * @param name     property name
     * @param property property
     */
    public void setProperty(String name, java.io.Serializable property) {
        _map.put(name, property);
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getMainJar() {
        return mainJar;
    }

    public void setMainJar(String mainJar) {
        this.mainJar = mainJar;
    }

    public List getDependantJars() {
        return dependantJars;
    }

    public void setDependantJars(List dependantJars) {
        this.dependantJars = dependantJars;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageLocation() {
        return packageLocation;
    }

    public void setPackageLocation(String packageLocation) {
        this.packageLocation = packageLocation;
    }

    public boolean isRebuildAllJarsIntoOne() {
        return rebuildAllJarsIntoOne;
    }

    public void setRebuildAllJarsIntoOne(boolean rebuildAllJarsIntoOne) {
        this.rebuildAllJarsIntoOne = rebuildAllJarsIntoOne;
    }

    public BuildType getBuildType() {
        return buildType;
    }

    public void setBuildType(BuildType buildType) {
        this.buildType = buildType;
    }

    /**
     * Get displayable name of build type
     *
     * @return displayable name
     */
    public String getBuildTypeName() {
        return buildType.getName();
    }

    /**
     * Set build type by its displayable name
     *
     * @param buildTypeName build type name
     */
    // TODO Method should be removed. It's not good to request build type by displayable name
    public void setBuildTypeByName(String buildTypeName) {
        this.buildType = BuildType.getInstance(buildTypeName);
    }

    public String getEncType() {
        return encType;
    }

    public void setEncType(String encType) {
        this.encType = encType;
    }

    public String getHasherType() {
        return hasherType;
    }

    public void setHasherType(String hasherType) {
        this.hasherType = hasherType;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getPredefArgs() {
        return predefArgs;
    }

    public void setPredefArgs(String predefArgs) {
        this.predefArgs = predefArgs;
    }

    public String getDefaultArgs() {
        return defaultArgs;
    }

    public void setDefaultArgs(String defaultArgs) {
        this.defaultArgs = defaultArgs;
    }

    /**
     * @return name of package file
     */
    public String getPackageFileName() {
        if (!packageName.endsWith(buildType.getExtenstion()))
            return packageName + '.' + buildType.getExtenstion();
        else
            return packageName;
    }

    /**
     * @return full path to package file
     */
    public String getPackageFileFullPath() {
        try {
           return new File(FileUtils.appendFileSeparatorIfNecessary(baseDir) +
                FileUtils.appendFileSeparatorIfNecessary(packageLocation) + getPackageFileName()).getCanonicalPath();
        } catch(IOException e) {
            return FileUtils.appendFileSeparatorIfNecessary(baseDir) + FileUtils.appendFileSeparatorIfNecessary(packageLocation) + getPackageFileName();
        }
    }

}

/*
 * $Log: BuildConfiguration.java,v $
 * Revision 1.7  2005/02/22 14:00:19  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.6  2005/02/11 09:33:56  kamil_sham
 * Added help and about dialogs. Fixed several bugs.
 *
 * Revision 1.5  2005/01/24 13:23:33  ignath
 * Refactored code
 *
 * Revision 1.4  2005/01/20 12:39:25  ignath
 * Added "main jar" field to build configuration
 *
 * Revision 1.3  2005/01/12 15:04:25  ignath
 * Added jars validation and main class validation to BuildConfigurationValidator.
 * Replaced ArrayList to interface List where it was appropriate.
 *
 * Revision 1.2  2005/01/12 09:38:05  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.8  2005/01/11 12:12:01  ignath
 * Added good file header and footer
 *
 * Revision 1.7  2005/01/11 09:36:10  ignath
 * Refactored build configuration type to enumeration class.
 * Fixed bug: first letter of package file extension was capitalized.
 *
 * Revision 1.6  2004/12/31 12:32:55  ignath
 * Fixed minor bugs
 *
 * Revision 1.5  2004/12/29 12:38:12  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.4  2004/12/28 15:51:21  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.3  2004/12/28 09:00:19  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */