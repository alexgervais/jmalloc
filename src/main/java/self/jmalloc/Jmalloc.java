package self.jmalloc;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Jmalloc {

    Map<Long, ByteBuffer> map = new HashMap<Long, ByteBuffer>();

    public long storeSerializableInRandomMemory(Serializable serializableObject) {

        byte[] bytes = Serializer.serialize(serializableObject);

        ByteBuffer bb = ByteBuffer.allocateDirect(bytes.length);
        bb.put(bytes);

        long index = UUID.randomUUID().getMostSignificantBits();
        map.put(index, bb);

        return index;
    }

    public long storeSerializableAtMemoryLocation(Serializable serializableObject, long memoryLocation) {

        ByteBuffer bb = map.remove(memoryLocation);
        byte[] bytes = Serializer.serialize(serializableObject);

        if (bb.capacity() < bytes.length) {
            bb = ByteBuffer.allocateDirect(bytes.length);
            bb.put(bytes);
        } else {
            bb.rewind();
            bb.put(bytes);
        }

        map.put(memoryLocation, bb);

        return memoryLocation;
    }

    public Serializable readSerializableFromMemory(long memoryLocation) {

        ByteBuffer bb = map.get(memoryLocation);
        bb.rewind();

        byte[] bytes = new byte[bb.capacity()];
        bb.get(bytes);

        return Serializer.deserialize(bytes);
    }

}
