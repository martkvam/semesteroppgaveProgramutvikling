package javaCode.ReaderWriter.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {
    public static void WriteFile(Path path, String string) throws IOException {
        Files.write(path, string.getBytes());
    }
}
