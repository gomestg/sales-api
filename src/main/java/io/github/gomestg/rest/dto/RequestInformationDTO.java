package io.github.gomestg.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInformationDTO {
    private Integer id;
    private String cpf;
    private String clientName;
    private BigDecimal total;
    private String requestDate;
    private String status;
    private List<RequestItemInformationDTO> items;
}
