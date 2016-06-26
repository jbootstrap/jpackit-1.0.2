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

package net.sf.jpackit;


/**
 * Central point of using JPackIt API. It loads BuildConfiguration and
 * desides what logic to apply.
 *
 * @author Kamil K. Shamgunov
 */

public class Main {

    /**
     * Creates a new instance of Main
     */
    public Main() {
    }

    public static void main(String[] args) {
    }

}

/*
 * $Log: Main.java,v $
 * Revision 1.4  2005/01/24 13:23:36  ignath
 * Refactored code
 *
 * Revision 1.3  2005/01/21 14:20:27  ignath
 * Changed main class search in JarScanner to ASM implementation (to avoid NoClassDefFoundError with loadClass)
 *
 * Revision 1.2  2005/01/14 11:19:11  ignath
 * Added possibility to cancel building process
 *
 * Revision 1.1  2005/01/12 09:25:44  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.9  2005/01/11 12:12:05  ignath
 * Added good file header and footer
 *
 * Revision 1.8  2005/01/11 09:36:11  ignath
 * Refactored build configuration type to enumeration class.
 * Fixed bug: first letter of package file extension was capitalized.
 *
 * Revision 1.7  2004/12/30 09:11:39  ignath
 * Created listener and event for package builder notifications and used them in code instead of standard StatePropertyChanged listener and event.
 *
 * Revision 1.6  2004/12/29 14:22:07  ignath
 * Added notification messages (and percent) update to JarPackageBuilder
 *
 * Revision 1.5  2004/12/29 12:38:12  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.4  2004/12/28 15:51:23  ignath
 * Developing of module that packages application in one executable jar file
 *
 * Revision 1.3  2004/12/28 09:00:20  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */