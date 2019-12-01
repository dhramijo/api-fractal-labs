package com.fractal.services.imp;

import com.fractal.modules.*;
import com.fractal.services.CategoryTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jonad dhrami on 01/12/2019.
 */
@Component
public class CategoryTransactionServiceImpl implements CategoryTransactionService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.partnerId}")
    private String partnerId;

    @Value("${api.fractalUrl}")
    private String fractalUrl;

    private static final Logger log = LoggerFactory.getLogger(CategoryTransactionServiceImpl.class);

    @Override
    public CategorisedTransactions getCategorisedTransactions(String categoryId) {
        String token  = getToken(restTemplate);
        log.debug("token generated: " + token);

        if (!token.isEmpty()) {
            HttpHeaders headers = getAuthorizedHttpHeaders(token);

            try{
                log.info("request is sending to Fractal endpoint resource");
                ResponseEntity<CategorisedTransactions> resp = restTemplate.exchange(fractalUrl + "/categories/"
                                + categoryId + "/transactions", HttpMethod.GET,
                        new HttpEntity<>(null, headers), CategorisedTransactions.class);
                return resp.getBody();

            }catch (Exception exc){
                log.info("selected category doesn't contain data, category=" + categoryId);

            }

        }
        return null;
    }

    @Override
    public boolean updateCategoryTransaction(UpdateCategoryTransaction updateCategoryTransaction) {
        String token  = getToken(restTemplate);
        log.debug("token generated: " + token);

        if (!token.isEmpty()) {
            HttpHeaders headers = getAuthorizedHttpHeaders(token);

            try{
                log.info("request is sending to Fractal endpoint resource");
                ResponseEntity<ResponseEntity> exchange = restTemplate.exchange(fractalUrl + "/categories/",
                        HttpMethod.PUT, new HttpEntity<>(updateCategoryTransaction, headers), ResponseEntity.class);
                return true;

            }catch (Exception exc){
                log.info("Requested category or transaction cannot be found for data=" + updateCategoryTransaction );

            }

        }
        return false;
    }

    @Override
    public boolean createNewCategory(Category category) {
        String token  = getToken(restTemplate);
        log.debug("token generated: " + token);

        if (!token.isEmpty()) {
            HttpHeaders headers = getAuthorizedHttpHeaders(token);

            try{
                log.info("request is sending to Fractal endpoint resource");
                ResponseEntity<ResponseEntity> exchange = restTemplate.exchange(fractalUrl + "/categories/",
                        HttpMethod.POST, new HttpEntity<>(category, headers), ResponseEntity.class);
                return true;

            }catch (Exception exc){
                log.info("Error while creating a new category");

            }

        }
        return false;
    }

    /**
     * gets token to access remote rest resource
     * @param restTemplate spring restTemplate for http requests
     * @return token for successfull requests empty string for error
     */
    private String getToken(RestTemplate restTemplate){
        ResponseEntity<TokenResponse> res = null;
        HttpHeaders headers = getHttpHeaders(apiKey, partnerId);
        try {
            res = restTemplate.exchange(fractalUrl + "/token", HttpMethod.POST, new HttpEntity<>(null, headers), TokenResponse.class);
            return res.getBody().getAccessToken();
        }catch (HttpClientErrorException ex){
            log.error("given api key or partner id is not valid");
        }catch (Exception ex){
            log.error("there is a problem during the connection the api");
        }
        return "";

    }

    /**
     * generate http headers for rest request
     * @param apiKey for the rest resource
     * @param partnerId for the partner
     * @return http header generated
     */
    private HttpHeaders getHttpHeaders(String apiKey, String partnerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", apiKey);
        headers.set("X-Partner-Id", partnerId);
        return headers;
    }

    /**
     * generate headers containing authentication information
     * @param token for the rest resource
     * @return http headers contain authentication info
     */
    private HttpHeaders getAuthorizedHttpHeaders(String token) {
        HttpHeaders headers = getHttpHeaders(apiKey, partnerId);
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

}
