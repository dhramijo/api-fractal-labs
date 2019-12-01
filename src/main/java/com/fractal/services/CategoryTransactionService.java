package com.fractal.services;

import com.fractal.modules.CategorisedTransactions;
import com.fractal.modules.Category;
import com.fractal.modules.UpdateCategoryTransaction;

/**
 * Created by jonad dhrami on 01/12/2019.
 */
public interface CategoryTransactionService {

    public CategorisedTransactions getCategorisedTransactions(String categoryId);
    public boolean updateCategoryTransaction(UpdateCategoryTransaction updateTransactionCategory);
    public boolean createNewCategory(Category category);

}
