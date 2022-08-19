package byow.Core;

import java.io.*;

public class SaveAndLoad {

    public SaveAndLoad() { }

    public void createNew() throws IOException {
        File saving = new File("saved.txt");
        saving.createNewFile();
    }

    public void save(String seed, String direction) throws IOException {
        File prev = new File("save.txt");
        if (prev.exists()) {
            prev.delete();
        }
        Serialization ser = new Serialization(seed, direction);
        FileOutputStream file = new FileOutputStream("save.txt");
        ObjectOutputStream writer = new ObjectOutputStream(file);
        writer.writeObject(ser.getSeed());
        writer.writeObject(ser.getDirection());
        writer.close();
    }

    public String[] load() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("save.txt");
        ObjectInputStream reader = new ObjectInputStream(file);
        String[] data = new String[2];
        for (int i = 0; i < data.length; i++) {
            data[i] = (String) reader.readObject();
        }
        reader.close();
        return data;
    }

}
