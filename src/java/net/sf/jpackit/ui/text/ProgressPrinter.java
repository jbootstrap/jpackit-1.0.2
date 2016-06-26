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

package net.sf.jpackit.ui.text;

import net.sf.jpackit.notification.JPackitNotificationListener;

/**
 * This class prints all events that come from JarPackageBuilder
 *
 * @author Kamil K. Shamgunov
 */
public class ProgressPrinter implements JPackitNotificationListener {
    int progressValue = 0;

    /**
     * Creates a new instance of ProgressPrinter
     */
    public ProgressPrinter() {
    }

    public void actionInterrupted() {
        System.out.println("Cancelled");
    }

    public void errorMessageChanged(net.sf.jpackit.notification.JPackitNotificationEvent event) {
        System.out.println("Stopped with next error: " + event.getClass());
    }

    public void progressInfoChanged(net.sf.jpackit.notification.JPackitNotificationEvent event) {
        progressValue = Integer.parseInt(event.getMessage());
    }

    public void stateChanged(net.sf.jpackit.notification.JPackitNotificationEvent event) {
        System.out.println(event.getMessage() + " - " + progressValue + '%');
    }

}

/*
 * $Log: ProgressPrinter.java,v $
 * Revision 1.2  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.1  2005/01/21 13:12:00  kamil_sham
 * Added command line jar package builder
 *
 */