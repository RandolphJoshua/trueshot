package com.ongtangco.trueshot.serviceimpl;

import com.ongtangco.trueshot.dto.ProductSearchCriteria;
import com.ongtangco.trueshot.entity.ProductData;
import com.ongtangco.trueshot.enums.ConditionGrade;
import com.ongtangco.trueshot.model.Product;
import com.ongtangco.trueshot.repository.ProductDataRepository;
import com.ongtangco.trueshot.service.ProductService;
import com.ongtangco.trueshot.util.Transform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductDataRepository productDataRepository;
    private final Transform<ProductData, Product> transformEntityToModel = new Transform<>(Product.class);
    private final Transform<Product, ProductData> transformModelToEntity = new Transform<>(ProductData.class);

    @Override
    public List<Product> getAllProducts() {
        return productDataRepository.findAll().stream()
                .map(transformEntityToModel::transform)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProducts(ProductSearchCriteria criteria) {
        if (criteria == null) {
            return getAllProducts();
        }
        Set<ProductData> result = new LinkedHashSet<>();
        if (StringUtils.hasText(criteria.getKeyword())) {
            result.addAll(productDataRepository.searchByKeyword(criteria.getKeyword().trim()));
        }
        if (StringUtils.hasText(criteria.getBrand())) {
            result.addAll(productDataRepository.findByBrandIgnoreCase(criteria.getBrand().trim()));
        }
        if (StringUtils.hasText(criteria.getCondition())) {
            try {
                ConditionGrade grade = ConditionGrade.valueOf(criteria.getCondition().trim().toUpperCase(Locale.ROOT));
                result.addAll(productDataRepository.findByConditionGrade(grade));
            } catch (IllegalArgumentException ex) {
                log.warn("Unsupported condition filter [{}]", criteria.getCondition());
            }
        }
        if (result.isEmpty()) {
            return getAllProducts();
        }
        return result.stream().map(transformEntityToModel::transform).collect(Collectors.toList());
    }

    @Override
    public Product get(Integer id) {
        return productDataRepository.findById(id)
                .map(transformEntityToModel::transform)
                .orElse(null);
    }

    @Override
    public Product create(Product product) {
        ProductData data = transformModelToEntity.transform(product);
        if (!StringUtils.hasText(data.getSku())) {
            data.setSku(generateSku(data));
        }
        data.setAvailable(Boolean.TRUE.equals(product.getAvailable()) || (product.getStockQuantity() == null || product.getStockQuantity() > 0));
        ProductData saved = productDataRepository.save(data);
        log.info("Created product {}", saved.getSku());
        return transformEntityToModel.transform(saved);
    }

    @Override
    public Product update(Integer id, Product product) {
        return productDataRepository.findById(id).map(existing -> {
            ProductData updated = transformModelToEntity.transform(product);
            updated.setId(existing.getId());
            if (!StringUtils.hasText(updated.getSku())) {
                updated.setSku(existing.getSku());
            }
            if (updated.getCreatedAt() == null) {
                updated.setCreatedAt(existing.getCreatedAt());
            }
            if (updated.getAvailable() == null) {
                updated.setAvailable(existing.getAvailable());
            }
            ProductData saved = productDataRepository.save(updated);
            log.info("Updated product {}", saved.getSku());
            return transformEntityToModel.transform(saved);
        }).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        productDataRepository.findById(id).ifPresent(product -> {
            productDataRepository.delete(product);
            log.info("Deleted product {}", product.getSku());
        });
    }

    @Override
    public List<Product> listByBrand(String brand) {
        if (!StringUtils.hasText(brand)) {
            return Collections.emptyList();
        }
        return productDataRepository.findByBrandIgnoreCase(brand.trim()).stream()
                .map(transformEntityToModel::transform)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> listByCondition(ConditionGrade conditionGrade) {
        if (conditionGrade == null) {
            return Collections.emptyList();
        }
        return productDataRepository.findByConditionGrade(conditionGrade).stream()
                .map(transformEntityToModel::transform)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Product>> groupByBrand() {
        return getAllProducts().stream().collect(Collectors.groupingBy(Product::getBrand, TreeMap::new, Collectors.toList()));
    }

    private String generateSku(ProductData productData) {
        String brand = Optional.ofNullable(productData.getBrand()).orElse("TRS").replaceAll("[^A-Za-z]", "").toUpperCase(Locale.ROOT);
        String model = Optional.ofNullable(productData.getModelName()).orElse("CAM").replaceAll("[^A-Za-z0-9]", "").toUpperCase(Locale.ROOT);
        return String.format("%s-%s-%s", brand, model, UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
    }
}
