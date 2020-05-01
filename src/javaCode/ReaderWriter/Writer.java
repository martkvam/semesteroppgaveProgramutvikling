package javaCode.ReaderWriter;

import java.io.IOException;
import java.nio.file.Path;

public interface Writer {
    void save(Path filePath) throws IOException;
}
