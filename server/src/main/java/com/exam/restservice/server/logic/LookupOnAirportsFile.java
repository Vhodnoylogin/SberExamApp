package com.exam.restservice.server.logic;

import com.exam.restservice.server.logic.exceptions.RecordNotFoundException;
import com.opencsv.bean.CsvToBeanBuilder;
import common.constant.CommonPath;
import common.models.Airport;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LookupOnAirportsFile {
    protected static Map<Long, Airport> cashedFile;
    protected final static Object mutex = new Object();

    protected static Map<Long, Airport> getFromFile() throws IOException {
        return Collections.unmodifiableMap(
                new CsvToBeanBuilder<Airport>(new FileReader(CommonPath.FILE_AIRPORT))
                        .withType(Airport.class)
//                        .withQuoteChar('"')
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
