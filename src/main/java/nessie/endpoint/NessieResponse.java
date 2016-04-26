package nessie.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;

class NessieResponse {

    @JsonProperty
    private boolean found;

    NessieResponse(boolean found) {
        this.found = found;
    }
}
