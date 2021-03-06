package com.fractal.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jonad dhrami on 01/12/2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Data
public class Category {

    private String id;
    private String name;

}
