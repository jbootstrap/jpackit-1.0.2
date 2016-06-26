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

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Enumeration for available build types
 *
 * @author Ignat Aleksandrov
 * @version $Id: BuildType.java,v 1.3 2005/01/24 13:23:33 ignath Exp $
 */
public final class BuildType implements Serializable {
    /**
     * Build package as jar file
     */
    public static final BuildType JAR = new BuildType(0);

    /**
     * Build package as single class file
     */
    public static final BuildType CLASS = new BuildType(1);

    /**
     * Build package as Windows executable file
     */
    public static final BuildType EXE = new BuildType(2);

    /**
     * All types in order
     */
    private static final BuildType[] TYPES = {JAR, CLASS, EXE};

    /**
     * Visible names of build types
     */
    private static final String[] NAMES = {"Jar", "Class", "Exe"};

    /**
     * File extensions associated with each build type
     */
    private static final String[] EXTENSIONS = {"jar", "class", "exe"};

    /**
     * build type
     */
    private final int buildType;

    /**
     * Constructor is private to emulate enumeration type in Java < 1.5
     */
    private BuildType(int buildType) {
        this.buildType = buildType;
    }

    /**
     * Get BuildType instance according to build type name
     *
     * @param name name of build type
     * @return instance of BuildType corresponding to its name
     * @throws IllegalArgumentException if there is no build type with specified name
     */
    // TODO This method should be substituted with getInstance(int)
    public static BuildType getInstance(String name) {
        for (int i = 0; i < NAMES.length; i++) {
            if (name.equals(NAMES[i]))
                return TYPES[i];
        }

        throw new IllegalArgumentException("Invalid build type name: '" + name + '\'');
    }

    /**
     * Get displayable name of current build type.
     *
     * @return name
     */
    public String getName() {
        if (buildType < 0 || buildType >= NAMES.length)
            throw new IllegalStateException("Invalid build type number: " + buildType);

        return NAMES[buildType];
    }

    /**
     * Get file extension for package file created with current build type
     *
     * @return extnsion
     */
    public String getExtenstion() {
        if (buildType < 0 || buildType >= EXTENSIONS.length)
            throw new IllegalStateException("Invalid build type number: " + buildType);

        return EXTENSIONS[buildType];
    }

    /**
     * We need to override this method to guarantee type-safe enumerations behaviour after deserialization
     *
     * @return matched BuildType
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        if (buildType < 0 || buildType >= TYPES.length)
            throw new IllegalStateException("Invalid build type number: " + buildType);

        return TYPES[buildType];
    }
}

/*
 * $Log: BuildType.java,v $
 * Revision 1.3  2005/01/24 13:23:33  ignath
 * Refactored code
 *
 * Revision 1.2  2005/01/12 15:55:28  ignath
 * Refactored enumeration implementation
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:37:55  kamil_sham
 * Fixed javadoc comments to avoid javadoc building warnings
 *
 * Revision 1.3  2005/01/11 12:12:02  ignath
 * Added good file header and footer
 *
 * Revision 1.2  2005/01/11 10:55:18  ignath
 * Made BuildType serializable.
 *
 * Revision 1.1  2005/01/11 09:36:10  ignath
 * Refactored build configuration type to enumeration class.
 * Fixed bug: first letter of package file extension was capitalized.
 *
 */