package edu.cooper.ee.ece366.coopmo.message;

import java.util.ArrayList;

public class Message {
    private Object data;
    private Err error;

    public Message() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Err getError() {
        return error;
    }

    public void setError(Err error) {
        this.error = error;
    }

    public static class Err {
        private String code;
        private String message;
        private ArrayList<ErrorReport> errors;

        public Err(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ArrayList<ErrorReport> getErrors() {
            return errors;
        }

        public void setErrors(ArrayList<ErrorReport> errors) {
            this.errors = errors;
        }

        public void addError(String domain, String reason, String message) {
            errors.add(new ErrorReport(domain, reason, message));
        }

        public static class ErrorReport {
            private String domain;
            private String reason;
            private String message;

            public ErrorReport(String domain, String reason, String message) {
                this.domain = domain;
                this.reason = reason;
                this.message = message;
            }
        }


    }


}
