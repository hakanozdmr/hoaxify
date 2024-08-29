package hakan.ozdmr.hoaxifyws.error;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApiError {

    private int status;

    private String message;

    private String path;

    private final long timestamp = new Date().getTime();

    private Map<String,String> validationErrors = new HashMap<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
