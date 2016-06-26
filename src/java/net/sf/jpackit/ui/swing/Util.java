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

package net.sf.jpackit.ui.swing;

/**
 * Utility class that contains static functions needed in different places of
 * project.
 *
 * @author Kamil K. Shamgunov
 */

public class Util {

    /**
     * Creates a new instance of Util
     */
    private Util() {
    }

    public static String calculateRelativePath(String basePath, String fullPath) {
        basePath = basePath.replaceAll("\\\\", "/");
        fullPath = fullPath.replaceAll("\\\\", "/");
        String calculatedPath = fullPath.replaceAll(basePath, "");
        if (calculatedPath.length() > 1) {
            if (calculatedPath.startsWith("/"))
                return calculatedPath.substring(1);
            else
                return calculatedPath;
        } else if (calculatedPath.length() <= 1 && (calculatedPath.equals("/") || calculatedPath.equals(""))) {
            return ".";
        } else {
            throw new RuntimeException("Something strange in calculateRelativePath. basePath: " + basePath + " fullPath: " + fullPath + " calculatedPath is: " + calculatedPath);
        }
    }

    public static boolean belongs(String fullPath, String basePath) {
        return fullPath.startsWith(basePath);
    }

}


/*
 * $Log: Util.java,v $
 * Revision 1.3  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.2  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:04  ignath
 * Added good file header and footer
 *
 */