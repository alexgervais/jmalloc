package self.jmalloc

import self.jmalloc.objects.AnObject

class JmallocTest extends GroovyTestCase {

    AnObject anObject
    AnObject yetAnotherObject
    String simpleString
    Integer integer;

    void setUp() {

        anObject = new AnObject(name: "123", age: 123)
        yetAnotherObject = new AnObject(name: "OMGOMGOMG!", age: 1)
        simpleString = "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz11223344556677889900"
        integer = 29834;
    }

    void testStoreAndRead() throws Exception {

        Jmalloc jmalloc = new Jmalloc()

        long address1 = jmalloc.storeSerializableInRandomMemory(anObject)
        long address2 = jmalloc.storeSerializableInRandomMemory(yetAnotherObject)
        long address3 = jmalloc.storeSerializableInRandomMemory(simpleString)
        long address4 = jmalloc.storeSerializableInRandomMemory(integer)

        AnObject myobj = jmalloc.readSerializableFromMemory(address1)

        assert "123" == myobj.getName()
        assert 123 == myobj.getAge()

        AnObject myobj2 = jmalloc.readSerializableFromMemory(address2)

        assert "OMGOMGOMG!" == myobj2.getName()
        assert 1 == myobj2.getAge()

        String aString = jmalloc.readSerializableFromMemory(address3)

        assert "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz11223344556677889900" == aString

        int anInt = jmalloc.readSerializableFromMemory(address4)

        assert 29834 == anInt
    }

    void testStoreReadSetRead() throws Exception {

        Jmalloc jmalloc = new Jmalloc()

        long address = jmalloc.storeSerializableInRandomMemory(anObject)

        AnObject obj1 = jmalloc.readSerializableFromMemory(address)

        assert "123" == obj1.getName()
        assert 123 == obj1.getAge()

        obj1.setName("OMGOMGOMG!")
        obj1.setAge(42)

        AnObject obj2 = jmalloc.readSerializableFromMemory(address)

        assert "123" == obj2.getName()
        assert 123 == obj2.getAge()
    }

    void testStoreReadSetStoreRead() throws Exception {

        Jmalloc jmalloc = new Jmalloc()

        long address = jmalloc.storeSerializableInRandomMemory(anObject)

        AnObject obj1 = jmalloc.readSerializableFromMemory(address)

        assert "123" == obj1.getName()
        assert 123 == obj1.getAge()

        obj1.setName("OMGOMGOMG!")
        obj1.setAge(42)

        jmalloc.storeSerializableAtMemoryLocation(obj1, address)

        AnObject obj2 = jmalloc.readSerializableFromMemory(address)

        assert "OMGOMGOMG!" == obj2.getName()
        assert 42 == obj2.getAge()
    }

    void testStoreReadSetStoreNewAddress() throws Exception {

        Jmalloc jmalloc = new Jmalloc()

        long address = jmalloc.storeSerializableInRandomMemory(anObject)

        AnObject obj1 = jmalloc.readSerializableFromMemory(address)

        assert "123" == obj1.getName()
        assert 123 == obj1.getAge()

        obj1.setName("OMGOMGOMG!")
        obj1.setAge(42)

        shouldFailWithCause(NullPointerException.class, {
            jmalloc.storeSerializableAtMemoryLocation(anObject, 1)
        })
    }

}
