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

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * RelativeFileSystemView is needed to restrict user to access filesystem upper than
 * specified root folder.
 *
 * @author Kamil K. Shamgunov
 */

public class RelativeFileSystemView extends FileSystemView {
    private File root;

    /**
     * Creates a new instance of RelativeFileSystemView
     */
    public RelativeFileSystemView(File root) {
        this.root = root;
    }

    public java.io.File createNewFolder(java.io.File file) throws java.io.IOException {
        return file;
    }

    public File[] getRoots() {
        return new File[]{root};
    }
}


/*
 * $Log: RelativeFileSystemView.java,v $
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