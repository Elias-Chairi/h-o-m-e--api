package edu.ntnu.iir.bidata.teamHOME.response.jsonapi;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a top level object.
 */
public abstract class TopLevel<T> {
    private T data;
    private Jsonapi jsonapi;
    private static final String jsonapiVersion = "1.1";

    public class Jsonapi {
        @Schema(example = jsonapiVersion)
        private String version;

        public Jsonapi(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }

    public TopLevel(T data) {
        this.data = data;
        this.jsonapi = new Jsonapi(jsonapiVersion);
    }

    public T getData() {
        return data;
    }

    public Jsonapi getJsonapi() {
        return jsonapi;
    }
}
