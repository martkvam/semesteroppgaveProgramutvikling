package javaCode.ReaderWriter.Car;

import javaCode.Lists;
import javaCode.ReaderWriter.Writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;

public class fileWriterJobj implements Writer{

    public void save(Path filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath.toString());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new ArrayList<>(Lists.getCars()));
        }
    }
}
