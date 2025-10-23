package com.ongtangco.trueshot.controller;

import com.ongtangco.trueshot.dto.ProductSearchCriteria;
import com.ongtangco.trueshot.enums.ConditionGrade;
import com.ongtangco.trueshot.model.Product;
import com.ongtangco.trueshot.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String condition) {
        if (StringUtils.hasText(brand)) {
            return ResponseEntity.ok(productService.listByBrand(brand));
        }
        if (StringUtils.hasText(condition)) {
            try {
                ConditionGrade grade = ConditionGrade.valueOf(condition.toUpperCase());
                return ResponseEntity.ok(productService.listByCondition(grade));
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String condition) {
        ProductSearchCriteria criteria = new ProductSearchCriteria();
        criteria.setKeyword(keyword);
        criteria.setBrand(brand);
        criteria.setCondition(condition);
        return ResponseEntity.ok(productService.searchProducts(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Integer id) {
        Product product = productService.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product created = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id, @Valid @RequestBody Product product) {
        Product updated = productService.update(id, product);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/grouped/brand")
    public ResponseEntity<Map<String, List<Product>>> groupedByBrand() {
        return ResponseEntity.ok(productService.groupByBrand());
    }
}
