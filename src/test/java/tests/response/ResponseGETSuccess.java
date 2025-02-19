package tests.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponseGETSuccess {

    @JsonProperty("message")
    private String message;


    public String getMessage() {
        return message;
    }
}
