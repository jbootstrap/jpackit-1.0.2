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
 * Interface that encapsulates event producing for notification framework.
 *
 * @author Kamil K. Shamgunov
 */
public interface JPackitEventProducer {
    /**
     * Adds JPackitNotificationListener to JPackitEventProducer so it can pass information
     * about generation process.
     *
     * @param listener JPackitNotificationListener to add..
     */
    public void addNotificationListener(JPackitNotificationListener listener);

    /**
     * Removes JPackitNotificationListener
     *
     * @param listener JPackitNotificationListener to remove.
     */
    public void removeNotificationListener(JPackitNotificationListener listener);

    /**
     * Notifies all registered JPackitNotificationListener that state changed
     *
     * @param event JPackitNotificationEvent to pass to listeners.
     */
    public void fireStateChanged(JPackitNotificationEvent event);

    /**
     * Notifies all registered JPackitNotificationListener that progress info changed
     *
     * @param event JPackitNotificationEvent to pass to listeners.
     */
    public void fireProgressInfoChanged(JPackitNotificationEvent event);

    /**
     * Notifies all registered JPackitNotificationListener that error occured
     *
     * @param event JPackitNotificationEvent to pass to listeners.
     */
    public void fireErrorMessageChanged(JPackitNotificationEvent event);

    /**
     * Notifies all registered JPackitNotificationListener that build process was interrupted
     */
    public void fireActionInterrupted();

    /**
     * Notifies JPackitEventProducer that current operation should be interrupted
     */
    public void cancelAction();
}

/*
 * $Log: JPackitEventProducer.java,v $
 * Revision 1.2  2005/01/24 13:23:34  ignath
 * Refactored code
 *
 * Revision 1.1  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 */