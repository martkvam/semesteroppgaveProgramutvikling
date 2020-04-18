package javaCode.ReaderWriter.Adjustment;

import javaCode.Adjustment;
import javaCode.Car;
import javaCode.Component;
import javaCode.Lists;
import javaCode.ReaderWriter.Writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class fileWriterJobj implements Writer {
    @Override
    public void save(Path filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath.toString());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new ArrayList<>(Lists.getAdjustment()));
        }
    }
}
