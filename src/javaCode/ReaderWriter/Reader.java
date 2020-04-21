package javaCode.ReaderWriter;

import javaCode.Component;
import javaCode.Lists;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Reader {
    void read(Path filePath) throws IOException;
}

