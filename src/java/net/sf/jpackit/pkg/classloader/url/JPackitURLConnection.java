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

package net.sf.jpackit.pkg.classloader.url;

import net.sf.jpackit.pkg.classloader.JPackitClassLoaderContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * URLConnection implementation that gets jar in internal package format decodes(if needed),
 * saves it in temporary folder and returns JarFile to JPackitURLClassLoader
 *
 * @author Kamil K. Shamgunov
 */

public class JPackitURLConnection extends JarURLConnection {

    private JPackitClassLoaderContext jpackitClassLoaderContext;

    /**
     * Creates a new instance of JPackitURLConnection
     */
    public JPackitURLConnection(JPackitClassLoaderContext jpackitContext, URL url) throws MalformedURLException {
        super(url);
        this.jpackitClassLoaderContext = jpackitContext;
    }

    public void connect() throws java.io.IOException {
    }

    public java.util.jar.JarFile getJarFile() throws java.io.IOException {
        String jarName = getJarFileURL().getFile();
        jarName = jarName.substring(1, jarName.length());
        if (jpackitClassLoaderContext.getCachedJarFile(jarName) != null) {
            return jpackitClassLoaderContext.getCachedJarFile(jarName);
        }

        InputStream iStream;
        try {
            iStream = jpackitClassLoaderContext.getInternalJarInputStream(jarName);
        } catch (Exception e) {
            throw new FileNotFoundException(jarName);
        }
        if (iStream == null) {
            throw new FileNotFoundException(jarName);
        }

        File tempFile = File.createTempFile("jpackit_jar_", "" + System.currentTimeMillis());

        byte[] buff = new byte[8196];
        int len;
        FileOutputStream fos = new FileOutputStream(tempFile);
        while ((len = iStream.read(buff)) != -1) {
            fos.write(buff, 0, len);
        }
        fos.flush();
        fos.close();
        JarFile jarFile = new JarFile(tempFile);
        jpackitClassLoaderContext.addCachedJarFile(jarName, jarFile);
        return jarFile;
    }

    public java.util.jar.Manifest getManifest() throws java.io.IOException {
        JarFile jarFile = getJarFile();
        if (jarFile != null) {
            return jarFile.getManifest();
        } else {
            return null;
        }
    }

    public java.util.jar.Attributes getMainAttributes() throws java.io.IOException {
        JarFile jarFile = getJarFile();
        if (jarFile != null) {
            return jarFile.getManifest().getMainAttributes();
        } else {
            return null;
        }
    }

    public InputStream getInputStream() throws java.io.IOException {
        try {
            JarFile jarFile = getJarFile();
            if (jarFile != null) {
                JarEntry entry = jarFile.getJarEntry(getEntryName());
                if (entry != null) {
                    return jarFile.getInputStream(entry);
                } else {
                    throw new FileNotFoundException();
                }
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}

/*
 * $Log: JPackitURLConnection.java,v $
 * Revision 1.4  2005/01/24 13:23:34  ignath
 * Refactored code
 *
 * Revision 1.3  2005/01/12 10:55:30  kamil_sham
 * Fixed bug  1100772	getResourceAsStream is not working
 *
 * Revision 1.2  2005/01/12 09:38:06  kamil_sham
 * Removed modified from javadoc
 *
 * Revision 1.1  2005/01/12 09:25:46  kamil_sham
 * *** empty log message ***
 *
 * Revision 1.6  2005/01/11 12:12:02  ignath
 * Added good file header and footer
 *
 * Revision 1.5  2005/01/05 13:59:15  kamil_sham
 * Fixed bug 1096368 - Strange bug in JPackitURLConnection
 *
 * Revision 1.4  2005/01/05 13:37:27  ignath
 * Debug info
 *
 * Revision 1.3  2004/12/28 09:00:19  ignath
 * Added CVS 'Log' keyword to all java files - to trace cvs changes
 *
 */