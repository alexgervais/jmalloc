package self.jmalloc;

import java.io.*;

public class Serializer {

    public static byte[] serialize(Serializable serializableObject) {

        ByteArrayOutputStream bos = null;
        ObjectOutput out = null;
        byte[] bytes = null;

        try {
            bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(serializableObject);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }

    public static Serializable deserialize(byte[] bytes) {

        ByteArrayInputStream bis = null;
        ObjectInput in = null;
        Serializable object = null;

        try {
            bis = new ByteArrayInputStream(bytes);
            in = new ObjectInputStream(bis);
            object = (Serializable) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }
}