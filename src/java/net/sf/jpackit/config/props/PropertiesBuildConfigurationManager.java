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

package net.sf.jpackit.config.props;

import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.config.BuildConfigurationManager;
import net.sf.jpackit.util.PropertiesUtils;

import java.util.Properties;

/**
 * Build configuration manager based on standard java properties
 *
 * @author Kamil K. Shamgunov
 * @see net.sf.jpackit.config.BuildConfiguration
 */
public class PropertiesBuildConfigurationManager implements BuildConfigurationManager {

    /**
     * Creates a new instance of PropertiesBuildConfigurationManager
     */
    public PropertiesBuildConfigurationManager() {
    }

    public BuildConfiguration load(java.io.InputStream iStream) throws java.io.IOException, ClassNotFoundException {
        Properties props = new Properties();
        props.load(iStream);
        return PropertiesUtils.properties2BuildConfiguration(props);
    }

    public void save(BuildConfiguration buildConfiguration, java.io.OutputStream oStream) throws java.io.IOException {
        Properties props = PropertiesUtils.buildConfiguration2Properties(buildConfiguration);
        props.store(oStream, null);
    }

}

/*
 * $Log: PropertiesBuildConfigurationManager.java,v $
 * Revision 1.2  2005/01/24 13:23:32  ignath
 * Refactored code
 *
 * Revision 1.1  2005/01/20 13:00:33  kamil_sham
 * Added standard java properties based BuildConfiguration serialization
 *
 */