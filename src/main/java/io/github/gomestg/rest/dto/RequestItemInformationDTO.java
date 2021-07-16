package io.github.gomestg.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestItemInformationDTO {
    private String productName;
    private BigDecimal price;
    private Integer quantity;
}
