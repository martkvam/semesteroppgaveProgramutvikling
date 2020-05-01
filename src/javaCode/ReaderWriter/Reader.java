package javaCode.ReaderWriter;

import java.io.IOException;
import java.nio.file.Path;

public interface Reader {
    void read(Path filePath) throws IOException;
}

