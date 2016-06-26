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

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * Safe file chooser in conjuction with RelativeFileSystemView is responsible for
 * relative file choosing capability.
 *
 * @author Kamil K. Shamgunov
 */

public class SafeFileChooser extends JFileChooser {
    private File baseDirFile;

    /**
     * Creates a new instance of SafeFileChooser
     */
    public SafeFileChooser(File baseDir, FileSystemView fsView) {
        super(baseDir, fsView);
        this.baseDirFile = baseDir;
    }

    public void setCurrentDirectory(File dir) {
        if (dir != null)
            if (baseDirFile == null || (Util.belongs(dir.getPath(), baseDirFile.getPath()))) {
                super.setCurrentDirectory(dir);
            }
    }
}


/*
 * $Log: SafeFileChooser.java,v $
 * Revision 1.3  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.2  2005/01/20 20:43:15  kamil_sham
 * Made massive refactoring to separate and unificate notification framework, added main class selector dialog, and progress bar
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/11 12:12:04  ignath
 * Added good file header and footer
 *
 */