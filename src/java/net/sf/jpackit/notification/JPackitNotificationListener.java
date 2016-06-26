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

package net.sf.jpackit.notification;

/**
 * You can register a PropertyChangeListener to be notified
 * of any package builder actions or state changes.
 *
 * @author Ignat Aleksandrov
 * @version $Id: JPackitNotificationListener.java,v 1.2 2005/01/24 13:23:34 ignath Exp $
 */
public interface JPackitNotificationListener {
    /**
     * This method gets called when package builder wants to inform
     * that current state was changed (i.e. new action is executing)
     *
     * @param event object describing changes
     */
    void stateChanged(JPackitNotificationEvent event);

    /**
     * This method gets called when package builder wants to inform
     * that progress was changed (percentage)
     *
     * @param event object describing new progress
     */
    void progressInfoChanged(JPackitNotificationEvent event);

    /**
     * This method gets called when package builder wants to inform
     * that error occured
     *
     * @param event object describing errors
     */
    void errorMessageChanged(JPackitNotificationEvent event);

    /**
     * This method gets called when package builder wants to inform
     * that building process was interrupted
     */
    void actionInterrupted();
}

/*
 * $Log: JPackitNotificationListener.java,v $
 * Revision 1.2  2005/01/24 13:23:34  ignath
 * Refactored code
 *
 * Revision 1.1  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.2  2005/01/14 11:19:10  ignath
 * Added possibility to cancel building process
 *
 * Revision 1.1  2005/01/12 09:25:46  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.2  2005/01/11 12:12:02  ignath
 * Added good file header and footer
 *
 * Revision 1.1  2004/12/30 09:11:39  ignath
 * Created listener and event for package builder notifications and used them in code instead of standard StatePropertyChanged listener and event.
 *
 */