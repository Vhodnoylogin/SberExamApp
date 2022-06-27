package models;

import com.opencsv.bean.CsvBindByPosition;

public class Airport {
    @CsvBindByPosition(position = 0)
    protected Long id;
    @CsvBindByPosition(position = 1)
    protected String airport;
    @CsvBindByPosition(position = 2)
    protected String city;
    @CsvBindByPosition(position = 3)
    protected String country;
    @CsvBindByPosition(position = 4)
    protected String IATA;
    @CsvBindByPosition(position = 5)
    protected String ICAO;
    @CsvBindByPosition(position = 6)
    protected Double latitude;
    @CsvBindByPosition(position = 7)
    protected Double longitude;
    @CsvBindByPosition(position = 8)
    protected String watiszis1;
    @CsvBindByPosition(position = 9)
    protected String watiszis2;
    @CsvBindByPosition(position = 10)
    protected String watiszis3;
    @CsvBindByPosition(position = 11)
    protected String timeZoneName;
    @CsvBindByPosition(position = 12)
    protected String type;
    @CsvBindByPosition(position = 13)
    protected String watiszis4;

    public Long getId() {
        return id;
    }

    public String getAirport() {
        return airport;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIATA() {
        return IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getWatiszis1() {
        return watiszis1;
    }

    public String getWatiszis2() {
        return watiszis2;
    }

    public String getWatiszis3() {
        return watiszis3;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public String getType() {
        return type;
    }

    public String getWatiszis4() {
        return watiszis4;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", airport='" + airport + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", IATA='" + IATA + '\'' +
                ", ICAO='" + ICAO + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", watiszis1=" + watiszis1 +
                ", watiszis2=" + watiszis2 +
                ", watiszis3='" + watiszis3 + '\'' +
                ", timeZoneName='" + timeZoneName + '\'' +
                ", type='" + type + '\'' +
                ", watiszis4='" + watiszis4 + '\'' +
                '}';
    }
}
