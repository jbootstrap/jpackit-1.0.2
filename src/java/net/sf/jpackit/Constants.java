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

package net.sf.jpackit;

/**
 * All public constants used in application
 *
 * @author Ignat Aleksandrov
 * @version $Id: Constants.java,v 1.3 2005/01/20 13:10:07 kamil_sham Exp $
 */
public interface Constants {

    // internal protocol used to locate resources inside package
    public static final String URL_JPACKIT_PROTOCOL="jpackit";

    /**
     * Name of manifest file attributes
     */
    public static final String MANIFEST_MAIN_CLASS="JPackIt-Main-Class";
    public static final String MANIFEST_PREDEF_ARGS="JPackIt-PredefArgs";
    public static final String MANIFEST_DEFAULT_ARGS="JPackIt-DefaultArgs";
    public static final String MANIFEST_ENC_TYPE="JPackIt-Enc-Type";
    public static final String MANIFEST_PHASHER_TYPE="JPackIt-PHasher-Type";

    /**
     *  Path to folder inside jar file which contains all libraries necessary for execution
     * (including jar with application code
     * */
    public static final String JAR_LIBRARIES_PATH = "META-INF/lib/";

 
    public static final String KEY_BASE_DIR="jpackit.baseDir";
    public static final String KEY_DEPENDANT_JARS="jpackit.dependantJars";
    public static final String KEY_MAIN_JAR="jpackit.mainJar";
    public static final String KEY_PACKAGE_NAME="jpackit.packageName";
    public static final String KEY_PACKAGE_LOCATION="jpackit.packageLocation";
    public static final String KEY_BUILD_TYPE="jpackit.buldType";
    public static final String KEY_ENC_TYPE="jpackit.encType";
    public static final String KEY_HASHER_TYPE="jpackit.hasherType";
    public static final String KEY_MAIN_CLASS="jpackit.mainClass";    
    public static final String KEY_PREDEF_ARGS="jpackit.predefArgs";
    public static final String KEY_DEFAULT_ARGS="jpackit.defaultArgs";
}

/*
 * $Log: Constants.java,v $
 * Revision 1.3  2005/01/20 13:10:07  kamil_sham
 * Added dependantJars and mainJar serialization
 *
 * Revision 1.2  2005/01/20 13:00:33  kamil_sham
 * Added standard java properties based BuildConfiguration serialization
 *
 * Revision 1.1  2005/01/12 09:25:44  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:05  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/29 12:38:12  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.2  2004/12/28 15:51:23  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.1  2004/12/28 13:47:40  ignath
 * Libraries are no longer enumerated in Manifest file. Now all libraries are stored in META-INF/lib folder and there is no way to change this path.
 *
 */