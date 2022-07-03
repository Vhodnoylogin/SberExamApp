package common.wrapper;

//public class ErrorWrapper<T> extends Wrapper<T> implements IWrapper<T> {
//    @JsonProperty(CommonNames.ErrorNames.FIELD_NAME_ERROR)
//    protected Exception error;
//    @JsonProperty(CommonNames.ErrorNames.FIELD_NAME_ERROR_MESSAGE)
//    protected String errorClassName;
//
////    public static <R> ErrorWrapper<R> wrap(Throwable e) {
////        ErrorWrapper<R> wrapper = wrap(ErrorWrapper::new, ArrayList::new);
////        wrapper.error = e;
////        wrapper.errorClassName = Optional.of(e.getClass().getName()).orElse("Unexpected error");
////        return wrapper;
////    }
//
////    public void setError(Throwable error) {
////        this.error = error;
////    }
//
//    public Exception getError() {
//        return error;
//    }
//
//    public String getErrorClassName() {
//        return errorClassName;
//    }
//
//    @Override
//    public String toString() {
//        return "ErrorWrapper{" +
//                "uuid=" + uuid +
//                ", timestamp=" + timestamp +
//                ", content=" + content +
//                ", contentSize=" + contentSize +
//                ", error=" + error +
//                ", errorClassName='" + errorClassName + '\'' +
//                ", techInfo=" + techInfo +
//                '}';
//    }
//}
