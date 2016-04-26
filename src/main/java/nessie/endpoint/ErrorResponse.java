package nessie.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;

class ErrorResponse {

    @JsonProperty
    private String errorMsg;

    ErrorResponse(Throwable t) {
        this.errorMsg = t.getMessage();
    }
}
