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

package net.sf.jpackit.pkg.jar;

import net.sf.jpackit.pkg.classloader.AbstractJPackitClassloaderContext;

/**
 * JPackitClassloaderContext implementation for obtaining jars InputStream
 * from main jar file.
 *
 * @author Kamil K. Shamgunov
 */

public class InternalJarJPackitClassloaderContext extends AbstractJPackitClassloaderContext {

    /**
     * Creates a new instance of InternalJarsJPackitClassloaderContext
     */
    public InternalJarJPackitClassloaderContext() {
    }

    public java.io.InputStream getInternalJarInputStream(String jarName) {
        java.io.InputStream is = getClass().getClassLoader().getResourceAsStream(jarName);
        return is;
    }
}

/*
 * $Log: InternalJarJPackitClassloaderContext.java,v $
 * Revision 1.5  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.4  2005/01/21 13:22:58  kamil_sham
 * Changed direct jar file access to getResourceAsStream
 *
 * Revision 1.3  2005/01/12 10:55:31  kamil_sham
 * Fixed bug  1100772	getResourceAsStream is not working
 *
 * Revision 1.2  2005/01/12 09:38:07  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.5  2005/01/11 12:12:03  ignath
 * Added good file header and footer
 *
 * Revision 1.4  2005/01/05 13:59:26  kamil_sham
 * Fixed bug 1096368 - Strange bug in JPackitURLConnection
 *
 * Revision 1.3  2004/12/28 09:00:20  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */