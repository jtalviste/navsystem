package eu.wemakesoftware.jt.navsystem.controller.model;

public enum NavSystemError {


    UNKNOWN_ID(1,"Unknown mobile station UUID"),
    TOO_FEW_REPORTS(2,"Too few reports to triangulate");

    public static class NavSystemExceptionBase extends Exception {
        public final NavSystemError error;

        private NavSystemExceptionBase(NavSystemError error) {
            this.error = error;
        }
    }

    public static class UnknownIdException extends NavSystemExceptionBase {

        public UnknownIdException() {
            super(UNKNOWN_ID);
        }
    }

    public static class TooFewReportsException extends NavSystemExceptionBase {

        public TooFewReportsException() {
            super(TOO_FEW_REPORTS);
        }
    }

    public final int errorCode;
    public final String description;

    NavSystemError(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

}
