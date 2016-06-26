/*
 * Created 12.01.2005 at 15:39:26
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

import junit.framework.TestCase;
import junit.textui.TestRunner;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 * Test class for testing classloder functionality inside jpackit jar package.
 *
 * @author Kamil K. Shamgunov
 */
public class JPackitClassLoaderTest extends TestCase {
    private static final String TEST_INTERNAL_JAR = "META-INF/lib/test-internal.jar";

    public JPackitClassLoaderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws java.lang.Exception {
    }

    protected void tearDown() throws java.lang.Exception {
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(JPackitClassLoaderTest.class);
        return suite;
    }

    public void testGetParent() {
        ClassLoader cl = getClass().getClassLoader().getParent();
        if (cl == null)
            throw new RuntimeException("Coudn't get parent classloader!");
    }

    public void testGetMainJarResource() {
        URL url = getClass().getClassLoader().getResource(TEST_INTERNAL_JAR);
        if (url == null) {
            throw new RuntimeException("Couldn't get " + TEST_INTERNAL_JAR + " main resource!");
        }
    }

    public void testGetMainJarResourceAsStream() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_INTERNAL_JAR);
        if (is == null) {
            throw new RuntimeException("Couldn't get " + TEST_INTERNAL_JAR + " main resource's stream!");
        }
    }

    public void testGetMainJarResources() throws Exception {
        Enumeration en = getClass().getClassLoader().getResources(TEST_INTERNAL_JAR);
        if (en == null || !en.hasMoreElements()) {
            throw new RuntimeException("Couldn't get " + TEST_INTERNAL_JAR + " main resources!");
        }
    }

    public void testGetInternalJarResource() {
        URL url = getClass().getClassLoader().getResource("net/sf/jpackit/pkg/classloader/JPackitClassLoaderTest.class");
        if (url == null) {
            throw new RuntimeException("Couldn't get net/sf/jpackit/pkg/classloader/JPackitClassLoaderTest.class internal resource!");
        }
    }

    public void testGetInternalJarResourceAsStream() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("net/sf/jpackit/pkg/classloader/JPackitClassLoaderTest.class");
        if (is == null) {
            throw new RuntimeException("Couldn't get net/sf/jpackit/pkg/classloader/JPackitClassLoaderTest.class internal resource's stream!");
        }
    }

    public void testGetInternalJarResources() throws Exception {
        Enumeration en = getClass().getClassLoader().getResources("net/sf/jpackit/pkg/classloader/JPackitClassLoaderTest.class");
        if (en == null || !en.hasMoreElements()) {
            throw new RuntimeException("Couldn't get net/sf/jpackit/pkg/classloader/JPackitClassLoaderTest.class internal resources!");
        }
    }

    public void testInternalLoadClass() throws Exception {
        Class clazz = getClass().getClassLoader().loadClass("net.sf.jpackit.pkg.classloader.JPackitClassLoaderTest");
        if (clazz == null) {
            throw new RuntimeException("Couldn't load class net.sf.jpackit.pkg.classloader.JPackitClassLoaderTest from internal jar");
        }

    }

    public void testMainLoadClass() throws Exception {
        Class clazz = getClass().getClassLoader().loadClass("net.sf.jpackit.pkg.jar.Bootstrapper");
        if (clazz == null) {
            throw new RuntimeException("Couldn't load class net.sf.jpackit.pkg.jar.Bootstrapper from main jar");
        }
    }

    public void testGetSystemResource() {
        URL url = ClassLoader.getSystemResource("java/lang/Object.class");
        if (url == null) {
            throw new RuntimeException("Couldn't get java/lang/Object.class system resource!");
        }
    }

    public void testGetSystemResourceAsStream() {
        InputStream is = ClassLoader.getSystemResourceAsStream("java/lang/Object.class");
        if (is == null) {
            throw new RuntimeException("Couldn't get java/lang/Object.class system resource's stream!");
        }
    }

    public void testGetSystemResources() throws Exception {
        Enumeration en = ClassLoader.getSystemResources("java/lang/Object.class");
        if (en == null || !en.hasMoreElements()) {
            throw new RuntimeException("Couldn't get java/lang/Object.class system resources!");
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < args.length; i++)
            System.out.println(args[i]);
        TestRunner.run(suite());
    }
}

/*
 * $Log: JPackitClassLoaderTest.java,v $
 * Revision 1.7  2005/01/24 13:23:36  ignath
 * Refactored code
 *
 * Revision 1.6  2005/01/24 07:10:19  kamil_sham
 * Added support for default ars and predefined args, fixed NPE in Validator, changed build.xml to build jar_test package using commandline jar builder
 *
 * Revision 1.5  2005/01/19 11:43:44  ignath
 * Added cvs log
 *
 */