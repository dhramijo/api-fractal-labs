package com.fractal.modules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class Transactions {

    private Integer companyId;
    private Integer bankId;
    private String accountId;
    private String transactionId;
    private String valueDate;
    private String bookingDate;
    private Number amount;
    private String currencyCode;
    private String type;
    private String status;
    private String category;

}
