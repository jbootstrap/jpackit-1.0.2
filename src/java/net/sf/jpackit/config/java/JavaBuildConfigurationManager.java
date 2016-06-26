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

package net.sf.jpackit.config.java;

import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.config.BuildConfigurationManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Build configuration manager based on java standart serialization
 * @see net.sf.jpackit.config.BuildConfiguration
 * @author Kamil K. Shamgunov 
 */
public class JavaBuildConfigurationManager implements BuildConfigurationManager {
    
    /** Creates a new instance of JavaBuildConfigurationManager */
    public JavaBuildConfigurationManager() {
    }

    public BuildConfiguration load(java.io.InputStream iStream) throws java.io.IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(iStream);
        return (BuildConfiguration) ois.readObject();
    }

    public void save(BuildConfiguration buildConfiguration, java.io.OutputStream oStream) throws java.io.IOException {
        ObjectOutputStream oos = new ObjectOutputStream(oStream);
        oos.writeObject(buildConfiguration);
        oos.flush();
    }
    
}

/*
 * $Log: JavaBuildConfigurationManager.java,v $
 * Revision 1.2  2005/01/12 09:38:05  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:00  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/28 09:00:18  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */