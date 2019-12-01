package com.fractal.controllers;

import com.fractal.modules.CategorisedTransactions;
import com.fractal.modules.Category;
import com.fractal.modules.UpdateCategoryTransaction;
import com.fractal.services.imp.CategoryTransactionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@Api(value="Categorised transactions, create a new category and update a category", description="Operations for creating a new category, getting categorised transaction data for given category and updates category info for a transaction")
public class CategoryTransactionController {

    @Autowired
    CategoryTransactionServiceImpl categoryTransactionService;

    private static final Logger log = LoggerFactory.getLogger(CategoryTransactionController.class);

    @GetMapping("api/v1/{categoryId}")
    @ResponseBody
    @ApiOperation(value = "Get categorised transactions for a given category", response = CategorisedTransactions.class)
    ResponseEntity<?> getCategorisedTransaction(@PathVariable String categoryId) {
        CategorisedTransactions categorisedTransactions = categoryTransactionService.getCategorisedTransactions(categoryId);
        if (categorisedTransactions == null ){
            return new ResponseEntity<>("The given category doesn't contain data", null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categorisedTransactions, null, HttpStatus.OK);
    }

    @PutMapping(value = "api/v1/updatecategory")
    @ApiOperation(value = "Update category of an existing transaction")
    ResponseEntity<?> updateCategoryTransaction(@RequestBody UpdateCategoryTransaction updateCategoryTransaction){
        if (StringUtils.isEmpty(updateCategoryTransaction.getCompanyId()) ||
                StringUtils.isEmpty(updateCategoryTransaction.getCategoryId()) ||
                StringUtils.isEmpty(updateCategoryTransaction.getTransactionId())){
            log.info("updatecategory is called with empty data");
            return new ResponseEntity<>("Company, Category or Transaction cannot be null", null, HttpStatus.BAD_REQUEST);

        }
        boolean response = categoryTransactionService.updateCategoryTransaction(updateCategoryTransaction);
        if (response == true){
            return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Requested category or transaction cannot be found", null, HttpStatus.NOT_FOUND);

    }

    // Method to be implemented. Waiting to know the Fractal POST endpoint for adding new Category.
    @PostMapping(value = "api/v1/category")
    @ApiOperation(value = "Add a new category")
    ResponseEntity<?> updateCategoryTransaction(@RequestBody Category category){

        if (StringUtils.isEmpty(category.getId()) || StringUtils.isEmpty(category.getName())){
            log.info("category is called with empty data");
            return new ResponseEntity<>("Category Id or Category Name cannot be null", null, HttpStatus.BAD_REQUEST);
        }

        boolean response = categoryTransactionService.createNewCategory(category);

        if (response == true){
            return new ResponseEntity<>(null, null, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The new category has not been added", null, HttpStatus.BAD_REQUEST);
    }

}