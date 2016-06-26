/*
 * Created 12.01.2005 at 15:39:50
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
package net.sf.jpackit.config.validator;



/**
 * Description of error occured during build configuration validation
 *
 * @author Ignat Aleksandrov
 * @version $Id: BuildConfigurationError.java,v 1.3 2005/01/13 07:35:14 kamil_sham Exp $
 */
public class BuildConfigurationError {
    /**
     * Error description
     */
    private String message;

    public BuildConfigurationError() {

    }

    public BuildConfigurationError(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String toString() {
        return message;
    }    
}


/*
 * $Log: BuildConfigurationError.java,v $
 * Revision 1.3  2005/01/13 07:35:14  kamil_sham
 * Fixed several bugs and added validation support in ui
 *
 * Revision 1.2  2005/01/12 15:04:18  ignath
 * Added jars validation and main class validation to BuildConfigurationValidator.

 * Replaced ArrayList to interface List where it was appropriate.
 *
 * Revision 1.1  2005/01/12 12:45:57  ignath
 * Started developing build configuration validator
 *
 */