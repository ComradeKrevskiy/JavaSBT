import java.io.*;
import java.rmi.server.RMIClassLoader;
import java.io.IOException;

public class EncryptedClassLoader extends ClassLoader {
    private final int key;

    private final File dir;

    public EncryptedClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String name){ //Finds the class with the specified binary name
        byte[] classData = {};
        try {
            classData = readStream(name);
        } catch (IOException e) {return null;}

        classData = decryptData(classData);
        return defineClass(name, classData, 0, classData.length);
    }

    //private byte[] encryptData(byte[] classData){
    //    int len = classData.length;
    //    if (key <= len)
    //    classData[key] += 1;
    //    return classData;
    //}

    private byte[] decryptData(byte[] classData) {
        int len = classData.length;
        if (key <= len)
            classData[key] -= 1;
        return classData;
    }

    byte[] readStream(String name)throws IOException {
        ArrayList<Byte> buf = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(dir.getAbsolutePath() + "\\" + name.replace(".", "\\") + ".class");
            int b;
            while((b = inputStream.read()) != -1)// -1 - EOF
                buf.add((byte)b);

        } catch (FileNotFoundException e) {}
        return (byte[])buf.toArray();
    }
}
