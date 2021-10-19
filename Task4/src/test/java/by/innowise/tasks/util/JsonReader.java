package by.innowise.tasks.util;

import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static String readJson(String path) {
        try {
            return FileUtils.readFileToString(
                    new File(String.valueOf(
                            new ClassPathResource(path).getFile())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
