package com.exam.restserviceg.logic;

import com.exam.restserviceg.logic.exceptions.NoSuchRecordException;
import com.exam.restserviceg.models.Data;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LookupOnAirportsFile {
    protected static final String FILE_AIRPORT_PATH = "data/airports.dat";

    protected static Map<Long, Data> cashedFile;

    protected static Map<Long, Data> getFromFile() throws IOException {
        if (cashedFile == null) {
            File f = new ClassPathResource(
                    FILE_AIRPORT_PATH
                    , LookupOnAirportsFile.class.getClassLoader()
            ).getFile();
            cashedFile = new CsvToBeanBuilder<Data>(new FileReader(f))
                    .withType(Data.class)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .stream()
                    .filter(x -> x.getId() != null)
                    .collect(Collectors.toMap(
                            Data::getId
                            , x -> x)
                    );
        }

        return cashedFile;
    }

    public static Data getDataById(Long id) throws NoSuchRecordException, IOException {
        return Optional
                .ofNullable(getFromFile().get(id))
                .orElseThrow(() -> new NoSuchRecordException(id));

    }

    public static List<Data> getAllData() throws IOException {
        return new ArrayList<>(getFromFile().values());
    }
}
