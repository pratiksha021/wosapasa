package com.wosapasa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String productName;
    private String productDescription;
    private BigDecimal price;
}
