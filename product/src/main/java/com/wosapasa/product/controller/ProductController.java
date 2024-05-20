package com.wosapasa.product.controller;

import com.wosapasa.product.dto.ProductRequest;
import com.wosapasa.product.dto.ProductResponse;
import com.wosapasa.product.model.Product;
import com.wosapasa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ArrayList<String> homeScreen(){
        ArrayList<String> homeText = new ArrayList<>();
        String message = "Welcome to Wosa Pasa";
        String direction = "Here are the user guide:" +
                "/api/product -> add new product"+
                "api/product/allProducts -> search all products" +
                "api/product/{productId} -> product by id"+
                "api/product/update ->update product"+
                "api/product/delete -> delete product";
        homeText.add(message);
        homeText.add(direction);
        return homeText;
    }

    @GetMapping("/allProducts")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId) {
        Optional<Product> product = productService.getProductById(productId);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductRequest> updateProduct(@PathVariable("productId")String productId, @RequestBody ProductRequest productRequest) {
        try {
            ProductRequest updateProduct = productService.updateProduct(productId, productRequest);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProductById(@PathVariable("productId")String productId) throws UnexpectedException {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
