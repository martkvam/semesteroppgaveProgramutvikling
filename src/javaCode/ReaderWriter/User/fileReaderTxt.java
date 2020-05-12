package javaCode.ReaderWriter.User;

import javaCode.InLog.ReadUsers;
import javaCode.ReaderWriter.Reader;

import java.io.IOException;
import java.nio.file.Path;

public class fileReaderTxt implements Reader {
        @Override
        public void read(Path filePath) throws IOException {
            try {
                ReadUsers.getUserList();
            }catch (Exception e){
                throw new IllegalArgumentException(e.getMessage());
            }
    }
}