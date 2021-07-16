package io.github.gomestg.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemDTO {
    private Integer product;
    private Integer quantity;
}
