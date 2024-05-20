package com.wosapasa.product.service;

import com.wosapasa.product.dto.ProductRequest;
import com.wosapasa.product.dto.ProductResponse;
import com.wosapasa.product.model.Product;
import com.wosapasa.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("product {} is added",product.getProductId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .price(product.getPrice())
                .build();

    }
    public Optional<Product> getProductById(String productId){
        return productRepository.findById(productId);
    }

    public ProductRequest updateProduct(String productId, ProductRequest productRequest) throws UnexpectedException {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            Product updatedProduct = product.get();
            updatedProduct.setProductName(productRequest.getProductName());
            updatedProduct.setProductDescription(productRequest.getProductDescription());
            updatedProduct.setPrice(productRequest.getPrice());
            Product newProduct = productRepository.save(updatedProduct);
            return convertToDTO(newProduct);
        }else{
            throw new UnexpectedException("product not found{}"+productId);

        }
    }

    private ProductRequest convertToDTO(Product product) {
        ProductRequest dto = new ProductRequest();
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
    public void deleteProductById(String productId) throws UnexpectedException {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            productRepository.deleteById(productId);
            log.info("product {} deleted successfully", productId);
        }else {
            throw new UnexpectedException("unable to find the product"+productId);
        }
    }
}
