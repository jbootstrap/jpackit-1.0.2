/*
 * Created ${DATE} at ${TIME}
 * ====================================================================
 *
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

package net.sf.jpackit.pkg.classloader;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * Abstract JPackitClassLoader implementation, that implements jar file cache 
 * functionality
 * @author Kamil K. Shamgunov 
 */

public abstract class AbstractJPackitClassloaderContext implements JPackitClassLoaderContext {
    protected Map jarFilesCache;
    
    public void addCachedJarFile(String jarName, java.util.jar.JarFile cachedJarFile) {
        if(jarFilesCache==null)
            jarFilesCache = new HashMap();
        jarFilesCache.put(jarName, cachedJarFile);
    }

    public java.util.jar.JarFile getCachedJarFile(String jarName) {
        if(jarFilesCache!=null)
            return (JarFile) jarFilesCache.get(jarName);
        else return null;
    }

    public java.util.Map getCachedJarFileMap() {
        return jarFilesCache;
    }
}
