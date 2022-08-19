package byow.Core;

import java.io.*;
import java.nio.file.Paths;


//@source http://underpop.online.fr
public class SavedFile {
    private String path = String.valueOf(Paths.get("byow", "Core", "savedFiles"));
    private File saved = new File(path);

    public SavedFile() {
        saved.mkdir();
    }

    public void createNew(String name) throws IOException {
        File saving = new File(path, name + ".txt");
        saving.createNewFile();
    }

    public void save(String seed, String direction, String name) throws IOException {
        if (hasFile(name)) {
            overwrite(name);
        }
        createNew(name);
        Serialization ser = new Serialization(seed, direction);
        FileOutputStream file = new FileOutputStream(path + "/" + name + ".txt");
        ObjectOutputStream writer = new ObjectOutputStream(file);
        writer.writeObject(ser.getSeed());
        writer.writeObject(ser.getDirection());
        writer.close();
    }

    public String[] load(String name) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(path + "/" + name + ".txt");
        ObjectInputStream reader = new ObjectInputStream(file);
        String[] data = new String[2];
        for (int i = 0; i < data.length; i++) {
            data[i] = (String) reader.readObject();
        }
        reader.close();
        return data;
    }

    public boolean hasFile(String name) {
        File prev = new File(saved, name + ".txt");
        return prev.exists();
    }

    public void overwrite(String name) {
        File prev = new File(saved, name + ".txt");
        prev.delete();
    }


    public String[] name() {
        File directory = new File(path);
        File[] files = directory.listFiles();
        String[] data = new String[4];

        for (int i = 0; i < nameLength(); i++) {
            String name = files[i].getName();
            data[i] = name.substring(0, name.length() - 4);
        }
        return data;
    }

    public int nameLength() {
        File directory = new File(path);
        int fileCount = directory.listFiles().length;
        return fileCount;
    }

}

