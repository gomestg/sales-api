package io.github.gomestg.rest.dto;

import io.github.gomestg.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    @NotNull(message = "{field.client-id.mandatory}")
    private Integer client;
    @NotNull(message = "{field.total-request.mandatory}")
    private BigDecimal total;
    @NotEmptyList(message = "{field.items-request.mandatory}")
    private List<RequestItemDTO> details;
}
