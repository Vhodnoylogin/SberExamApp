package common.help;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class CommonPath {
    public static final File FILE_AIRPORT;

    static {
        try {
            final String PATH_FILE_AIRPORT = "/data/airportsFull.dat";
            FILE_AIRPORT = new ClassPathResource(
                    PATH_FILE_AIRPORT
                    , CommonPath.class.getClassLoader()
            ).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
