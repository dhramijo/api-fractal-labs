package com.fractal.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jonad dhrami on 01/12/2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCategoryTransaction {

    private String companyId;
    private String categoryId;
    private String transactionId;

}
