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

import sun.security.action.GetPropertyAction;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;

/**
 * File system utilities
 *
 * @author Ignat Aleksandrov
 * @version $Id: FileUtils.java,v 1.3 2005/02/22 10:02:28 ignath Exp $
 */
public final class FileUtils {
    private static String tempDir;

    private FileUtils() {

    }
    
    // TODO Delete this method. It is not used in application
    /**
     * Get the path to the directory for the temporary files (OS dependant)
     * <p/>
     * (Copied from java.io.File. Don't know why it's private there)
     *
     * @return path to temporary files directory
     */
    public static String getTempDir() {
        if (tempDir == null) {
            GetPropertyAction a = new GetPropertyAction("java.io.tmpdir");
            tempDir = ((String) AccessController.doPrivileged(a));
        }
        return tempDir;
    }

    /**
     * Adds to path the separator ('/' or '\') if it isn't present already
     *
     * @param path path
     * @return updated path
     */
    public static String appendFileSeparatorIfNecessary(String path) {
        if (!path.endsWith(File.separator))
            path += File.separator;

        return path;
    }

    /**
     * Returns the canonical pathname string of given pathname.
     *
     * @param path pathname string
     * @return canonical pathname string
     */
    public static String getCanonicalPath(String path) {
        try {
            return new File(path).getCanonicalPath();
        } catch (IOException e) {
            return path;
        }
    }
}

/*
 * $Log: FileUtils.java,v $
 * Revision 1.3  2005/02/22 10:02:28  ignath
 * All paths are shown to user as canonical paths
 *
 * Revision 1.2  2005/01/13 11:21:25  ignath
 * Moved hardcoded message strings to properties file
 *
 * Revision 1.1  2005/01/12 09:25:48  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/11 12:12:04  ignath
 * Added good file header and footer
 *
 * Revision 1.2  2004/12/29 12:38:12  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.1  2004/12/28 15:51:22  ignath
 * Developing of module that packages application in one executable jar file
 *
 */