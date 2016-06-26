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

package net.sf.jpackit.builder;

import net.sf.jpackit.config.BuildConfiguration;
import net.sf.jpackit.notification.JPackitEventProducer;

/**
 * Interface that encapsulates package building process.
 *
 * @author Kamil K. Shamgunov
 */
public interface PackageBuilder extends JPackitEventProducer {
    /**
     * Imlemetnation of this method is responsible for package building(jar,exe, class)
     *
     * @param config BuildConfiguration used for building package..
     */
    public void buildPackage(BuildConfiguration config);
}

/*
 * $Log: PackageBuilder.java,v $
 * Revision 1.4  2005/01/24 13:23:27  ignath
 * Refactored code
 *
 * Revision 1.3  2005/01/20 20:43:09  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.2  2005/01/14 11:19:06  ignath
 * Added possibility to cancel building process
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.7  2005/01/11 12:11:59  ignath
 * Added good file header and footer
 *
 * Revision 1.6  2004/12/30 09:11:38  ignath
 * Created listener and event for package builder notifications and used them in code instead of standard StatePropertyChanged listener and event.
 *
 * Revision 1.5  2004/12/29 14:22:05  ignath
 * Added notification messages (and percent) update to JarPackageBuilder
 *
 * Revision 1.4  2004/12/29 13:09:13  kamil_sham
 * Added PropertyChangeListener functionality
 *
 * Revision 1.3  2004/12/28 10:29:26  kamil_sham
 * Added two methods getState()  and getProgressInfo for progress dialog handling.
 *
 * Revision 1.2  2004/12/28 09:00:18  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */
