package com.fractal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fractal.modules.CategorisedTransactions;
import com.fractal.modules.TokenResponse;
import com.fractal.modules.Transactions;
import com.fractal.services.imp.CategoryTransactionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryTransactionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CategoryTransactionServiceImpl categoryTransactionService;

    TokenResponse tokenResponse;

    @Before
    public void setUp() throws Exception {
        tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("token");
    }

    @Test
    public void getTokenThrowExceptionInvalidData() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(TokenResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));


        CategorisedTransactions response = categoryTransactionService.getCategorisedTransactions("fractal");

        Assert.assertEquals(null,response);
    }

    @Test
    public void getTokenThrowExceptionInvalidInput() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(TokenResponse.class)))
                .thenThrow(new RestClientException("exc"));


        CategorisedTransactions response = categoryTransactionService.getCategorisedTransactions("fractal");

        Assert.assertEquals(null,response);
    }

    @Test
    public void getCategorisedTransactionsReturnValidRecordValidInput() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(TokenResponse.class)))
                .thenReturn(new ResponseEntity( tokenResponse, HttpStatus.OK));

        CategorisedTransactions categorisedTransactions = new CategorisedTransactions();
        Transactions transactions = new Transactions();
        transactions.setAccountId("account");
        transactions.setAmount(1000);
        categorisedTransactions.setTransactions(Arrays.asList(transactions));
        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(CategorisedTransactions.class)))
                .thenReturn(new ResponseEntity( categorisedTransactions, HttpStatus.OK));

       CategorisedTransactions response = categoryTransactionService.getCategorisedTransactions("fractal");

        Assert.assertEquals(1,response.getTransactions().size());
        Assert.assertEquals("account", response.getTransactions().get(0).getAccountId());
        Assert.assertEquals(1000, response.getTransactions().get(0).getAmount());
    }

    @Test
    public void getCategorisedTransactionsThrowExceptionInvalidInput() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(TokenResponse.class)))
                .thenReturn(new ResponseEntity( tokenResponse, HttpStatus.OK));

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(CategorisedTransactions.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        CategorisedTransactions response = categoryTransactionService.getCategorisedTransactions("fractal");

        Assert.assertEquals(null,response);
    }

}
