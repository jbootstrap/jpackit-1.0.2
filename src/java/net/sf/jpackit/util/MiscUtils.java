/*
 * Created 13.01.2005 at 12:57:31
 * ====================================================================
 *
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.StringBuffer;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Miscellaneous utility functions used in application
 *
 * @author Ignat Aleksandrov
 * @version $Id: MiscUtils.java,v 1.5 2005/02/11 09:33:57 kamil_sham Exp $
 */
public final class MiscUtils {
    private MiscUtils() {

    }

    /**
     * Get formatted string from resource bundle
     *
     * @param rb     resource bundle
     * @param key    key of string in properties file
     * @param param1 first parameter
     * @return formatted string
     */
    public static String getResourceString(ResourceBundle rb, String key, Object param1) {
        String raw = rb.getString(key);
        Object params[] = new Object[1];
        params[0] = param1;
        String formatted = MessageFormat.format(raw, params);

        return formatted;
    }

    /**
     * Checks whether string is null or empty
     *
     * @param string tested string
     * @return true if string is null or is empty (""), false otherwise
     */
    public static boolean isEmpty(String string) {
        return (string == null || string.equals(""));
    }
    
    /**
     * Returns resource in string representation
     *
     * @param resource name
     * @return string representation of resource
     */
    public static String getResourceAsString(String name) {
        try {
            InputStreamReader is = new InputStreamReader(MiscUtils.class.getClassLoader().getResourceAsStream(name));
            StringWriter result = new StringWriter();
            if(is!=null) {
                char[] cbuf = new char[256];
                int len;
                while((len=is.read(cbuf))!=-1) {
                    result.write(cbuf,0,len);
                }
            }
            return result.toString();
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

/*
 * $Log: MiscUtils.java,v $
 * Revision 1.5  2005/02/11 09:33:57  kamil_sham
 * Added help and about dialogs. Fixed several bugs.
 *
 * Revision 1.4  2005/01/24 13:23:36  ignath
 * Refactored code
 *
 * Revision 1.3  2005/01/24 07:10:19  kamil_sham
 * Added support for default ars and predefined args, fixed NPE in Validator, changed build.xml to build jar_test package using commandline jar builder
 *
 * Revision 1.2  2005/01/13 12:25:20  ignath
 * Added checking for empty field values of BuildConfig in BuildConfigurationValidator
 *
 * Revision 1.1  2005/01/13 11:21:25  ignath
 * Moved hardcoded message strings to properties file
 *
 */