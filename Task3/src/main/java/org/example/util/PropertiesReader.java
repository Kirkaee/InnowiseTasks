package org.example.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private static Properties properties = getProperties();

    public static Properties getProperties(){
        File file = new File("src/main/resources/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPassengersNumber(){
        return Integer.parseInt(properties.getProperty("passengersNumber"));
    }

    public static int getElevatorCapacity(){
        return  Integer.parseInt(properties.getProperty("elevatorCapacity"));
    }

    public static int getFloorsNumber(){
        return Integer.parseInt(properties.getProperty("floorsNumber"));
    }
}
