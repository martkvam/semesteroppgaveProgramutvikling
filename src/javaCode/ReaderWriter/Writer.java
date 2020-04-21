package javaCode.ReaderWriter;

import javaCode.Lists;

import java.io.IOException;
import java.nio.file.Path;

public interface Writer {
    void save(Path filePath) throws IOException;
}
