
package jp.gr.java_conf.u6k.pg_note;

import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PgNoteConfiguration extends Configuration {

    @NotEmpty
    private String _devToken;

    @JsonProperty
    public String getDevToken() {
        return _devToken;
    }

    @JsonProperty
    public void setDevToken(String devToken) {
        _devToken = devToken;
    }

}
