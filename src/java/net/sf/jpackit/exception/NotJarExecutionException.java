/*
 * Created 14.01.2005 at 11:11:16
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
package net.sf.jpackit.exception;

/**
 * Exception indicating that application was started not as jar file
 * (i.e not as 'java -jar app.jar')
 *
 * @author Ignat Aleksandrov
 * @version $Id: NotJarExecutionException.java,v 1.1 2005/01/14 08:39:11 ignath Exp $
 */
public class NotJarExecutionException extends Exception {
    public NotJarExecutionException() {

    }

    public NotJarExecutionException(String message) {
        super(message);
    }

    public NotJarExecutionException(Throwable cause) {
        super(cause);
    }

    public NotJarExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

/*
 * $Log: NotJarExecutionException.java,v $
 * Revision 1.1  2005/01/14 08:39:11  ignath
 * Added application exceptions.
 * All exceptions during jar package building are now catched and displayed to user as text error messages.
 *
 */