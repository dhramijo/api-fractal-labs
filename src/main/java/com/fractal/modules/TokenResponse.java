package com.fractal.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by jonad dhrami on 01/12/2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("partner_name")
    private String partnerName;
    @JsonProperty("partner_id")
    private String partnerId;
    private String expires;
    @JsonProperty("token_type")
    private String tokenType;

}
