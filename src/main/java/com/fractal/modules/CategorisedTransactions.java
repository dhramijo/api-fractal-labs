package com.fractal.modules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonad dhrami on 01/12/2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CategorisedTransactions {

    @JsonProperty("results")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Transactions> transactions = new ArrayList<Transactions>();

}
