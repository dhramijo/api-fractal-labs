package com.fractal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryTransactionControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getCategorisedTransactionForGivenCategoryReturnSuccessValidInput() throws Exception {
        mockMvc.perform(get("/api/v1/a6hg1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results.[0].accountId" , is("fakeAcct11")))
                .andExpect(jsonPath("$.results.[0].bankId" , is(8)))
                .andExpect(jsonPath("$.results.[0].amount" , is(4000)))
                .andExpect(jsonPath("$.results.[0].category" , is("Revenue")))
                .andExpect(jsonPath("$.results.[0].companyId" , is(2)));
    }

    @Test
    public void getCategorisedTransactionForGivenCategoryReturnSuccessInvalidValidInput() throws Exception {
        mockMvc.perform(get("/api/v1/invalidCategory"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCategoryTransactionReturnNodataForValidInput() throws Exception {
        mockMvc.perform(put("/api/v1/updatecategory").accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("{\"companyId\": \"randomCompanyId\",\"categoryId\": \"a6hg1\",\"transactionId\": \"invalidTransaction\"}"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateCategoryTransactionReturnNotfoundForInvalidInput() throws Exception {
        mockMvc.perform(put("/api/v1/updatecategory").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"companyId\": \"randomCompanyId\",\"categoryId\": \"a6hg1\",\"transactionId\": \"invalidTransaction\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCategoryTransactionReturnFoundForInvalidInput() throws Exception {
        mockMvc.perform(put("/api/v1/updatecategory").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"companyId\": \"randomCompanyId\",\"categoryId\": \"random\",\"transactionId\": \"invalidTransaction\"}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
