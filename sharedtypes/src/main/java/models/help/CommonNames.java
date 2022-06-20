package models.help;

public class CommonNames {
    public static class Wrapper {

        public static final String FIELD_NAME_UUID = "uuid";
        public static final String FIELD_NAME_TIMESTAMP = "timestamp";
        public static final String FIELD_NAME_CONTENT_SIZE = "contentSize";
        public static final String FIELD_NAME_TECH_INFO = "techInfo";
        public static final String FIELD_NAME_CONTENT = "content";
    }

    public static class Error {

        public static final String FIELD_NAME_ERROR = "error";
        public static final String FIELD_NAME_ERROR_CLASSNAME = "errorClassName";
    }

    public static class Params {
        public static final String PARAM_ID = "id";

        public static final String PARAM_UUID = Wrapper.FIELD_NAME_UUID;
        public static final String PARAM_TIMESTAMP = Wrapper.FIELD_NAME_TIMESTAMP;

        public static final String PARAM_REQUEST = "request";
    }


    public static class URLStorage {
        public static final String URL_ROOT = "";
        public static final String URL_ERROR = URL_ROOT + "/error";
        public static final String URL_GREETING = URL_ROOT + "/greeting";
        public static final String URL_AIRPORTS = URL_ROOT + "/airports";
        public static final String URL_AIRPORTS_GET_ALL = URL_AIRPORTS + "/get/all";
        public static final String URL_AIRPORTS_GET_BY_ID = URL_AIRPORTS + "/get";
    }
}
