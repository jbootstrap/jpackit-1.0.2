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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import net.sf.jpackit.Constants;
import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.config.BuildType;


/**
 * Utils needed by properties based BuildConfiguration serialozation
 * @see net.sf.jpackit.config.BuildConfiguration
 * @author Kamil K. Shamgunov 
 */

public class PropertiesUtils implements Constants {
    
    private PropertiesUtils() {
    }
    
    public static Properties buildConfiguration2Properties(BuildConfiguration bc) {
        Properties props = new Properties();
        props.setProperty(KEY_BASE_DIR, bc.getBaseDir());
        props.setProperty(KEY_DEPENDANT_JARS, list2String(bc.getDependantJars()));
        props.setProperty(KEY_MAIN_JAR, bc.getMainJar());
        props.setProperty(KEY_PACKAGE_LOCATION, bc.getPackageLocation());
        props.setProperty(KEY_PACKAGE_NAME, bc.getPackageName());
        props.setProperty(KEY_BUILD_TYPE, bc.getBuildType().getName());
        props.setProperty(KEY_MAIN_CLASS, bc.getMainClass());
        props.setProperty(KEY_ENC_TYPE, bc.getEncType());
        props.setProperty(KEY_HASHER_TYPE, bc.getHasherType());
        props.setProperty(KEY_DEFAULT_ARGS, bc.getDefaultArgs());
        props.setProperty(KEY_PREDEF_ARGS, bc.getPredefArgs());
        return props;
    }
    
    public static BuildConfiguration properties2BuildConfiguration(Properties props) {
        BuildConfiguration bc = new BuildConfiguration();
        bc.setBaseDir(props.getProperty(KEY_BASE_DIR));
        bc.setDependantJars(string2List(props.getProperty(KEY_DEPENDANT_JARS)));
        bc.setMainJar(props.getProperty(KEY_MAIN_JAR));
        bc.setPackageLocation(props.getProperty(KEY_PACKAGE_LOCATION));
        bc.setPackageName(props.getProperty(KEY_PACKAGE_NAME));
        bc.setBuildType(BuildType.getInstance(props.getProperty(KEY_BUILD_TYPE)));
        bc.setMainClass(props.getProperty(KEY_MAIN_CLASS));
        bc.setEncType(props.getProperty(KEY_ENC_TYPE));
        bc.setHasherType(props.getProperty(KEY_HASHER_TYPE));
        bc.setDefaultArgs(props.getProperty(KEY_DEFAULT_ARGS));
        bc.setPredefArgs(props.getProperty(KEY_PREDEF_ARGS));
        return bc;
    }
    
    public static String list2String(List list) {
        StringBuffer sb = new StringBuffer();
        for(Iterator it = list.iterator();it.hasNext();) {
            sb.append((String)it.next());
            if(it.hasNext())
                sb.append(", ");
        }
        return sb.toString();
    }
    
    public static List string2List(String string) {
        if(string !=null && string.length()>0) {
            ArrayList list = new ArrayList();
            StringTokenizer tokenizer = new StringTokenizer(string, ",");
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                list.add(token.trim());
            }
            return list;
        }
        else {
            return new ArrayList();
        }
    }
    
}
/*
 * $Log: PropertiesUtils.java,v $
 * Revision 1.2  2005/01/20 13:10:13  kamil_sham
 * Added dependantJars and mainJar serialization
 *
 * Revision 1.1  2005/01/20 13:00:33  kamil_sham
 * Added standard java properties based BuildConfiguration serialization
 *
 */