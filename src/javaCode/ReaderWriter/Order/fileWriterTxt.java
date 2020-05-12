package javaCode.ReaderWriter.Order;

import javaCode.Lists;
import javaCode.user.OrderFormatter;
import javaCode.ReaderWriter.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class fileWriterTxt implements Writer {
    @Override
    public void save(Path filePath) throws IOException {
        Files.write(filePath, OrderFormatter.formatOrders(Lists.getOrders()).getBytes());
    }
}
