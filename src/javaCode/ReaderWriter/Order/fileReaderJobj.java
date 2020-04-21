package javaCode.ReaderWriter.Order;

import javaCode.Lists;
import javaCode.ReaderWriter.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class fileReaderJobj implements Reader{
    public void read(Path path) throws IOException {
        /*try (InputStream fin = Files.newInputStream(path);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            Lists list = (Lists) oin.readObject();
            elementList.removeAllComponents();
            list.getComponents().forEach(elementList::addComponent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }

         */
    }
}
