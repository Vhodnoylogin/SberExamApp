package restserviceg.logic;

import com.opencsv.bean.CsvToBeanBuilder;
import models.Airport;
import org.springframework.core.io.ClassPathResource;
import restserviceg.logic.exceptions.RecordNotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LookupOnAirportsFile {
    protected static final String FILE_AIRPORT_PATH = "/data/airports.dat";

    protected static Map<Long, Airport> cashedFile;
    protected final static Object mutex = new Object();

    protected static Map<Long, Airport> getFromFile() throws IOException {
        File f = new ClassPathResource(
                FILE_AIRPORT_PATH
                , LookupOnAirportsFile.class.getClassLoader()
        ).getFile();
        return Collections.unmodifiableMap(
                new CsvToBeanBuilder<Airport>(new FileReader(f))
                        .withType(Airport.class)
                        .withIgnoreEmptyLine(true)
                        .build()
                        .stream()
                        .filter(x -> x.getId() != null)
                        .collect(Collectors.toMap(
                                Airport::getId
                                , x -> x)
                        )
        );
    }

    protected static Map<Long, Airport> getMapData() throws IOException {
        synchronized (mutex) {
            if (cashedFile == null) {
                cashedFile = getFromFile();
            }
        }
        return cashedFile;
    }

    public static Airport getDataById(Long id) throws IOException {
        return Optional
                .ofNullable(getMapData().get(id))
                .orElseThrow(() -> new RecordNotFoundException(id));

    }

    public static List<Airport> getAllData() throws IOException {
        return new ArrayList<>(getMapData().values());
    }
}
