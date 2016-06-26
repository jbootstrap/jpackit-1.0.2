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
import java.util.Arrays;
import java.util.List;

/**
 * ListModel based on ArrayList
 *
 * @author Kamil K. Shamgunov
 */
public class ArrayListListModel extends AbstractListModel implements MutableComboBoxModel {
    private List list;
    private Object currentObject;


    /**
     * Creates a new instance of ArrayListListModel
     */
    public ArrayListListModel(List list) {
        this.list = list;
        sort();
    }

    public Object getElementAt(int param) {
        return list.get(param);
    }

    public int getSize() {
        return list.size();
    }

    public void addElement(Object obj) {
        if (!list.contains(obj)) {
            list.add(obj);
            fireContentsChanged(this, 0, list.size());
        }
    }

    public Object getSelectedItem() {
        return currentObject;
    }

    public void insertElementAt(Object obj, int param) {
        list.add(param, obj);
        fireContentsChanged(this, 0, list.size());
    }

    public void removeElement(Object obj) {
        int index = list.indexOf(obj);
        if (index != -1) {
            removeElementAt(index);
        }
    }

    public void removeElementAt(int param) {
        if (list.size() > param) {
            list.remove(param);
        }
        fireContentsChanged(this, 0, list.size());
    }

    public void setSelectedItem(Object obj) {
        currentObject = obj;
        fireContentsChanged(this, 0, list.size());
    }

    public void sort() {
        String[] jars = (String[]) list.toArray(new String[0]);
        Arrays.sort(jars);
        list.clear();
        for (int i = 0; i < jars.length; i++) {
            list.add(jars[i]);
        }
        fireContentsChanged(this, 0, list.size());
    }
}


/*
 * $Log: ArrayListListModel.java,v $
 * Revision 1.5  2005/01/24 13:23:35  ignath
 * Refactored code
 *
 * Revision 1.4  2005/01/21 17:44:02  kamil_sham
 * Fixed many bugs, added sorting to ArrayListModel
 *
 * Revision 1.3  2005/01/13 05:57:36  kamil_sham
 * Fixed bug 1100901 Jar location chooser multiple operation is missing. Added multiple selection support,
 *
 * Revision 1.2  2005/01/12 15:04:25  ignath
 * Added jars validation and main class validation to BuildConfigurationValidator.
 
 * Replaced ArrayList to interface List where it was appropriate.
 *
 * Revision 1.1  2005/01/12 09:25:47  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/11 12:12:04  ignath
 * Added good file header and footer
 *
 */
