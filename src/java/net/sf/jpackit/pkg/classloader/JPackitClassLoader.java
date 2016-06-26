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

import net.sf.jpackit.pkg.classloader.url.JPackitURLStreamHandlerFactory;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * ClassLoader implementation, this custom classloader implementation  
 * works with jars stored in internal structure of package.
 * @author Kamil K. Shamgunov 
 */
public class JPackitClassLoader extends URLClassLoader {
    
    /** Creates a new instance of JPackitClassLoader */
    public JPackitClassLoader(URL[] urls) {
        super(urls);
    }

    /** Creates a new instance of JPackitClassLoader */
    public JPackitClassLoader(URL[] urls, ClassLoader parent, JPackitURLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }
    
    /** Creates a new instance of JPackitClassLoader */
    public JPackitClassLoader(URL[] urls, ClassLoader parentClassLoader) {
        super(urls, parentClassLoader);
    }
}

/*
 * $Log: JPackitClassLoader.java,v $
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