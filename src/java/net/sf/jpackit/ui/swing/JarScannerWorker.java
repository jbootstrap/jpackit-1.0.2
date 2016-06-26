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

import java.util.List;

/**
 * Jars scanner worker thread
 *
 * @author Kamil K. Shamgunov
 */
public class JarScannerWorker extends Thread {
    private String jarPath;
    private List executables;
    private JPackitProgressDialog progressDialog;

    /**
     * Creates a new instance of JarScannerWorker
     */
    public JarScannerWorker() {
    }

    public void run() {


    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public List getExecutables() {
        return executables;
    }

    public void setExecutables(List executables) {
        this.executables = executables;
    }

    public JPackitProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(JPackitProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}

/*
 * $Log: JarScannerWorker.java,v $
 * Revision 1.4  2005/01/24 18:32:49  kamil_sham
 * fixed build.xml to leave command line client in build directory. Fixed class file comments, added javadocs
 *
 */