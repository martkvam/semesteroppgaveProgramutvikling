package javaCode.ReaderWriter.Car;

import javaCode.Lists;
import javaCode.ReaderWriter.Writer;
import javaCode.objects.Car;
import javaCode.objects.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class fileWriterTxt implements Writer {

    public static String DELIMITER = ";";
    @Override
    public void save(Path filePath) throws IOException {
        Files.write(filePath, Lists.getCars().toString().getBytes());
    }

    public static String formatCar(Car car){
        return car.getCarID() + DELIMITER + car.getCarType() + DELIMITER + car.getDescription() + DELIMITER + car.getPrice();
    }

    public static String formatCars (List<Car> carList){
        StringBuffer str = new StringBuffer();
        for (Car o : carList){
            str.append(formatCar(o));
            str.append("\n");
        }
        return str.toString();
    }
}
