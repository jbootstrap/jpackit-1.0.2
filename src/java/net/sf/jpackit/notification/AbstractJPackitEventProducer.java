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


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Abstract implementation of JPackitEventProducer that implements PropertyChangeListenets
 * functionality.
 *
 * @author Kamil K. Shamgunov
 * @version $Id: AbstractJPackitEventProducer.java,v 1.2 2005/01/24 13:23:33 ignath Exp $
 */

public abstract class AbstractJPackitEventProducer implements JPackitEventProducer {
    ArrayList notificationListenersList = new ArrayList();
    private boolean cancelFlag = false;

    /**
     * Creates a new instance of AbstractPackageBuilder
     */
    public AbstractJPackitEventProducer() {
    }

    public void addNotificationListener(JPackitNotificationListener listener) {
        notificationListenersList.add(listener);
    }

    public void removeNotificationListener(JPackitNotificationListener listener) {
        notificationListenersList.remove(listener);
    }

    public void fireStateChanged(JPackitNotificationEvent notificationEvent) {
        for (Iterator it = notificationListenersList.listIterator(); it.hasNext();) {
            ((JPackitNotificationListener) it.next()).stateChanged(notificationEvent);
        }
    }

    public void fireProgressInfoChanged(JPackitNotificationEvent notificationEvent) {
        for (Iterator it = notificationListenersList.listIterator(); it.hasNext();) {
            ((JPackitNotificationListener) it.next()).progressInfoChanged(notificationEvent);
        }
    }

    public void fireErrorMessageChanged(JPackitNotificationEvent notificationEvent) {
        for (Iterator it = notificationListenersList.listIterator(); it.hasNext();) {
            ((JPackitNotificationListener) it.next()).errorMessageChanged(notificationEvent);
        }
    }

    public void fireActionInterrupted() {
        for (Iterator it = notificationListenersList.listIterator(); it.hasNext();) {
            ((JPackitNotificationListener) it.next()).actionInterrupted();
        }
    }

    public void fireStateChanged(String newState) {
        fireStateChanged(new JPackitNotificationEvent(this, newState));
    }

    public void fireProgressInfoChanged(int newProgressInfo) throws InterruptedException {
        fireProgressInfoChanged(new JPackitNotificationEvent(this, String.valueOf(newProgressInfo)));
        if (isCancelled())
            throw new InterruptedException();
    }

    public void fireErrorMessageChanged(String newErrorMessage) {
        fireErrorMessageChanged(new JPackitNotificationEvent(this, newErrorMessage));
    }

    public void cancelAction() {
        cancelFlag = true;
    }

    public boolean isCancelled() {
        return cancelFlag;
    }

}

/*
 * $Log: AbstractJPackitEventProducer.java,v $
 * Revision 1.2  2005/01/24 13:23:33  ignath
 * Refactored code
 *
 * Revision 1.1  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.2  2005/01/14 11:19:06  ignath
 * Added possibility to cancel building process
 *
 * Revision 1.1  2005/01/12 09:25:45  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/11 12:11:58  ignath
 * Added good file header and footer
 *
 * Revision 1.3  2004/12/30 09:11:38  ignath
 * Created listener and event for package builder notifications and used them in code instead of standard StatePropertyChanged listener and event.
 *
 * Revision 1.2  2004/12/29 14:22:05  ignath
 * Added notification messages (and percent) update to JarPackageBuilder
 *
 * Revision 1.1  2004/12/29 13:09:13  kamil_sham
 * Added PropertyChangeListener functionality
 *
 */
