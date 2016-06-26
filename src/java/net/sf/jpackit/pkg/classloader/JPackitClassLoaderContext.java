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

package net.sf.jpackit.pkg.classloader;

import java.util.Map;
import java.util.jar.JarFile;

/**
 * This interface encapsulates data access to jar files located in in package
 *
 * @author Kamil K. Shamgunov
 */

public interface JPackitClassLoaderContext {
    public java.io.InputStream getInternalJarInputStream(String jarName);

    public JarFile getCachedJarFile(String jarName);

    public void addCachedJarFile(String jarName, JarFile cachedJarFile);

    public Map getCachedJarFileMap();
}

/*
 * $Log: JPackitClassLoaderContext.java,v $
 * Revision 1.4  2005/01/24 13:23:34  ignath
 * Refactored code
 *
 * Revision 1.3  2005/01/12 10:55:27  kamil_sham
 * Fixed bug  1100772	getResourceAsStream is not working
 *
 * Revision 1.2  2005/01/12 09:38:06  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:46  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:03  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/28 09:00:19  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */