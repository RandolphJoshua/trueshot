package com.ongtangco.trueshot.service;

import com.ongtangco.trueshot.dto.ProductSearchCriteria;
import com.ongtangco.trueshot.enums.ConditionGrade;
import com.ongtangco.trueshot.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> searchProducts(ProductSearchCriteria criteria);
    Product get(Integer id);
    Product create(Product product);
    Product update(Integer id, Product product);
    void delete(Integer id);
    List<Product> listByBrand(String brand);
    List<Product> listByCondition(ConditionGrade conditionGrade);
    Map<String, List<Product>> groupByBrand();
}
