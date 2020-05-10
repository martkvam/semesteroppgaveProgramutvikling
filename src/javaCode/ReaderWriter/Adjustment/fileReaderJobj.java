package javaCode.ReaderWriter.Adjustment;

import javaCode.objects.Adjustment;
import javaCode.Lists;
import javaCode.ReaderWriter.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class fileReaderJobj implements Reader{
    public void read(Path path) throws IOException {
        Lists list = new Lists();

        try (InputStream fin = Files.newInputStream(path);
             ObjectInputStream stream = new ObjectInputStream(fin))
        {
            List<Adjustment> listeObjekt = (List<Adjustment>) stream.readObject();
            for(Adjustment i : listeObjekt){
                list.addAdjustment(i);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }
}
