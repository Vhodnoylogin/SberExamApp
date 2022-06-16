package com.exam.restserviceg.logic;

import com.exam.restserviceg.logic.exceptions.NoSuchRecordException;
import com.exam.restserviceg.models.Data;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LookupOnAirportsFile {
    protected static final String FILE_AIRPORT_PATH = "data/airports.dat";

    protected static Stream<Data> getFromFile() throws IOException {
//        File f = new ClassPathResource(
//                FILE_AIRPORT_PATH
//                , LookupOnAirportsFile.class.getClassLoader()
//        ).getFile();
//        return Files.readAllLines(f.toPath()).stream().map(Data::new);

        File f = new ClassPathResource(
                FILE_AIRPORT_PATH
                , LookupOnAirportsFile.class.getClassLoader()
        ).getFile();
//        CSVParserBuilder builder = new CSVReaderBuilder(f)
//                .withSeparator(',');
        CsvToBean<Data> qql = new CsvToBeanBuilder<Data>(new FileReader(f))
                .withType(Data.class)
                .build();
        return qql.stream();
//        try (Reader reader = Files.newBufferedReader(Paths.get("file.csv"))){
//            qql.build().
//        }
    }

    public static Data getDataById(Long id) throws NoSuchRecordException, IOException {
        Optional<Data> res = getFromFile()
                .filter(x -> Objects.equals(x.getId(), id))
                .findAny();

        if (res.isPresent()) {
            return res.get();
        } else {
            System.out.println("DGDFG");
            throw new NoSuchRecordException(id);
        }

    }

    public static List<Data> getAllData() throws IOException {
        return getFromFile().collect(Collectors.toList());
    }
}
