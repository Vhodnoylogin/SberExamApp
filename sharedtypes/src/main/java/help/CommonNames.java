package help;

public class CommonNames {
    public static class WrapperNames {

        public static final String FIELD_NAME_UUID = "uuid";
        public static final String FIELD_NAME_TIMESTAMP = "timestamp";
        public static final String FIELD_NAME_CONTENT_SIZE = "contentSize";
        public static final String FIELD_NAME_TECH_INFO = "techInfo";
        public static final String FIELD_NAME_CONTENT = "content";

        public static final String FIELD_NAME_TYPE = "type";
    }

    public static class ErrorNames {

        public static final String FIELD_NAME_ERROR = "error";
        public static final String FIELD_NAME_ERROR_CLASSNAME = "errorClassName";
    }

    public static class ParamsNames {
        public static final String PARAM_ID = "id";

        public static final String PARAM_UUID = WrapperNames.FIELD_NAME_UUID;
        public static final String PARAM_TIMESTAMP = WrapperNames.FIELD_NAME_TIMESTAMP;

        public static final String PARAM_REQUEST = "request";

        public static final String PARAM_CLIENT_TIMESTAMP = "clientTimestamp";
        public static final String PARAM_CLIENT_UUID = "clientUUID";

        public static final String PARAM_THREAD_NAME = "threadName";
    }


    public static class URLStorage {
        public static final String URL_ROOT = "";
        public static final String URL_ERROR = URL_ROOT + "/error";
        public static final String URL_GREETING = URL_ROOT + "/greeting";
        public static final String URL_AIRPORTS = URL_ROOT + "/airports";
        public static final String URL_AIRPORTS_GET_ALL = URL_AIRPORTS + "/get/all";
        public static final String URL_AIRPORTS_GET_BY_ID = URL_AIRPORTS + "/get";
    }

    public static class Paths {
        public static final String PATH_FILE_AIRPORT = "/data/airportsFull.dat";
    }
}
