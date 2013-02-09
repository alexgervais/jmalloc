package self.jmalloc

import org.junit.Test
import self.jmalloc.objects.AnObject

class JmallocFloodTest extends GroovyTestCase {

    void testFlood() throws Exception {

        Jmalloc jmalloc = new Jmalloc();

        int i = 0;
        while ((i++) < 99999) {
            jmalloc.storeSerializableInRandomMemory(new AnObject(name: "abcdefghijklmnopqrstuvwxyz", age: 54321))
            sleep(1)
            println(i + "\t\t" + Runtime.getRuntime().totalMemory() + "\t" + Runtime.getRuntime().freeMemory())
        }
    }
}
