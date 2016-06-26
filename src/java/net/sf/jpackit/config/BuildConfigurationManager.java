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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Build configuration manager defines interface to save and load BuildConfiguration 
 * @see net.sf.jpackit.config.BuildConfiguration
 * @author Kamil K. Shamgunov 
 */
public interface BuildConfigurationManager {
    /**
     * serialize BuildConfiguration into given OutputStream
     * @param buildConfiguration BuildConfiguration instance to save
     * @param oStream OutputStream to which write serialized BuildConfiguration
     * @throws java.io.IOException 
     */
    public void save(BuildConfiguration buildConfiguration, OutputStream oStream) throws IOException;
    /**
     * Load build configuration from given InputStream
     * @param iStream InputStream from where BuildConfiguration will be read.
     * @throws java.io.IOException 
     * @throws java.lang.ClassNotFoundException 
     * @return BuildConfiguration that is loaded from InputStream
     */
    public BuildConfiguration load(InputStream iStream) throws IOException, ClassNotFoundException;
}

/*
 * $Log: BuildConfigurationManager.java,v $
 * Revision 1.2  2005/01/12 09:38:05  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:01  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/28 09:00:19  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */