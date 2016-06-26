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

package net.sf.jpackit.pkg.classloader.url;

import net.sf.jpackit.pkg.classloader.JPackitClassLoaderContext;
import net.sf.jpackit.Constants;

import java.net.URLStreamHandlerFactory;


/**
 * JPackitURLStreamHandlerFactory is implementation of java.net.URLStreamHandlerFactory for 
 * handling URLs with jpackit:// url scheme.
 * @author Kamil K. Shamgunov 
 */

public class JPackitURLStreamHandlerFactory implements URLStreamHandlerFactory {
    private JPackitClassLoaderContext context;
    
    /** Creates a new instance of JPackitURLStreamHandlerFactory */
    public JPackitURLStreamHandlerFactory(JPackitClassLoaderContext context) {
        this.context = context;
    }

    public java.net.URLStreamHandler createURLStreamHandler(String scheme) {
        if(scheme.equals(Constants.URL_JPACKIT_PROTOCOL))
            return new JPackitURLStreamHandler(context);
        else if(scheme.equals("jar"))
            return new JPackitURLStreamHandler(context);
        else
            return null;
    }
    
}

/*
 * $Log: JPackitURLStreamHandlerFactory.java,v $
 * Revision 1.4  2005/01/19 11:46:08  kamil_sham
 * Fixed bug -1105178 JPackitUrlStreamHandler getResourceAsStream bug
 *
 * Revision 1.3  2005/01/12 10:55:30  kamil_sham
 * Fixed bug  1100772	getResourceAsStream is not working
 *
 * Revision 1.2  2005/01/12 09:38:06  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:46  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.6  2005/01/11 12:12:02  ignath
 * Added good file header and footer
 *
 * Revision 1.5  2004/12/28 15:51:22  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.4  2004/12/28 13:47:39  ignath
 * Libraries are no longer enumerated in Manifest file. Now all libraries are stored in META-INF/lib folder and there is no way to change this path.
 *
 * Revision 1.3  2004/12/28 09:00:19  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */