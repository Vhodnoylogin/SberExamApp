package restserviceg.logic;

import com.opencsv.bean.CsvToBeanBuilder;
import models.Airport;
import org.springframework.core.io.ClassPathResource;
import restserviceg.logic.exceptions.RecordNotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LookupOnAirportsFile {
    protected static final String FILE_AIRPORT_PATH = "/data/airports.dat";

    protected static Map<Long, Airport> cashedFile;

    protected static Map<Long, Airport> getFromFile() throws IOException {
        if (cashedFile == null) {
            File f = new ClassPathResource(
                    FILE_AIRPORT_PATH
                    , LookupOnAirportsFile.class.getClassLoader()
            ).getFile();
            cashedFile = new CsvToBeanBuilder<Airport>(new FileReader(f))
                    .withType(Airport.class)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .stream()
                    .filter(x -> x.getId() != null)
                    .collect(Collectors.toMap(
                            Airport::getId
                            , x -> x)
                    );
        }

        return cashedFile;
    }

    public static Airport getDataById(Long id) throws IOException {
        return Optional
                .ofNullable(getFromFile().get(id))
                .orElseThrow(() -> new RecordNotFoundException(id));

    }

    public static List<Airport> getAllData() throws IOException {
        return new ArrayList<>(getFromFile().values());
    }
}
