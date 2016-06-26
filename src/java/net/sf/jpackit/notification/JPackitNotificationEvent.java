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
 * A "PackageBuilderNotification" event gets delivered whenever a
 * package builder wants to notify about any current action or change
 *
 * @author Ignat Aleksandrov
 * @version $Id: JPackitNotificationEvent.java,v 1.1 2005/01/20 20:43:15 kamil_sham Exp $
 */
public class JPackitNotificationEvent extends java.util.EventObject {
    /**
     * notification message
     */
    private String message;

    public JPackitNotificationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

/*
 * $Log: JPackitNotificationEvent.java,v $
 * Revision 1.1  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
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