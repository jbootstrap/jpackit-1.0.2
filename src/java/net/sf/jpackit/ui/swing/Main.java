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

package net.sf.jpackit.ui.swing;

import javax.swing.UIManager;

/**
 * Main executable file for swing UI
 *
 * @author Kamil K. Shamgunov
 */

public class Main {

    /**
     * Creates a new instance of Main
     */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
        //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
        //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");

        JFrameForm form = new JFrameForm();
        form.pack();
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

}


/*
 * $Log: Main.java,v $
 * Revision 1.7  2005/03/18 06:53:43  kamil_sham
 * Changed to version 1.0.2, added 3rdpary into binary/source distribution
 *
 * Revision 1.6  2005/02/27 10:47:03  kamil_sham
 * Fixed bug that prevented distribution of plafs in JPackIt pakckage, added jgoodies looks.
 *
 * Revision 1.5  2005/02/01 07:26:36  kamil_sham
 * Fixed bug with abnormal program termination, centered JFrame window, and many other small bugs
 *
 * Revision 1.4  2005/01/25 11:06:05  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/24 19:03:30  kamil_sham
 * Added kunststoff look and feel, removed resizing from all windows.
 *
 * Revision 1.2  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/11 12:12:04  ignath
 * Added good file header and footer
 *
 */