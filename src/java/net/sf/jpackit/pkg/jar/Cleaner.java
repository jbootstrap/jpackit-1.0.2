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

package net.sf.jpackit.pkg.jar;

import net.sf.jpackit.pkg.classloader.JPackitClassLoaderContext;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarFile;

/**
 * Cleaner shutdownHook.
 *
 * @author Kamil K. Shamgunov
 */

public class Cleaner extends Thread {
    JPackitClassLoaderContext context;

    /**
     * Creates a new instance of Cleaner
     */
    public Cleaner(JPackitClassLoaderContext context) {
        this.context = context;
    }

    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                HashMap map = (HashMap) context.getCachedJarFileMap();
                if (map != null && !map.isEmpty()) {
                    for (Iterator it = ((HashMap) map.clone()).keySet().iterator(); it.hasNext();) {
                        String key = (String) it.next();
                        JarFile jarFile = (JarFile) map.get(key);
                        File file = new File(jarFile.getName());
                        if (file.exists()) {
                            jarFile.close();
                            if (file.delete()) {
                                map.remove(key);
                            }
                        } else {
                            map.remove(key);
                        }
                    }
                } else {
                    return;
                }
                Thread.sleep(333);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
