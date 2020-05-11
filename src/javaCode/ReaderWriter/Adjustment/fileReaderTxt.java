package javaCode.ReaderWriter.Adjustment;

import javaCode.ReaderWriter.Reader;

import java.io.IOException;
import java.nio.file.Path;

public class fileReaderTxt implements Reader {

    @Override
    public void read(Path path) throws IOException {
         /*   try (BufferedReader reader = Files.newBufferedReader(path)) {
                String tekst;
                while ((tekst = reader.readLine()) != null) {
                    list.addAdjustment(parsePerson(tekst));
                }
            } catch (InvalidPersonFormatException e) {
                throw new InvalidPersonFormatException(e.getMessage());
            }
            return list;
        }
        private Person parsePerson(String line) throws InvalidPersonFormatException {
            // split line string into three using the separator ";"
            String[] split = line.split(";");
            if(split.length != 7) {
                throw new InvalidPersonFormatException("må bruke ; for å dele de 7 datafeltene");
            }
            DataCollection collection = new DataCollection();
            String name = split[0];
            String dag = split[2];
            String maaned = split[3];
            String aar =split[4];
            String epost = split[5];
            String tlf = split[6];

            Person person = collection.testInnput(name, dag, maaned, aar, epost, tlf);

          */

    }
}

