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

package net.sf.jpackit.test;

/**
 * Main class for testing JPackitClassLoader. This class is packed into internal.jar for
 * for testing.
 * @author Kamil K. Shamgunov 
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    public static void main(String args[]) {
        System.out.println("MAIN.class form bootstrap.jar#/lib/internal.jar is Executed!!!");
    }
}

/*
 * $Log: Main.java,v $
 * Revision 1.2  2005/01/12 09:38:07  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:12:03  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/28 08:56:39  ignath
 * no message
 *
 */
