package io.github.gomestg.rest.controller;

import io.github.gomestg.domain.entity.Request;
import io.github.gomestg.domain.entity.RequestItem;
import io.github.gomestg.domain.enums.Status;
import io.github.gomestg.rest.dto.RequestDTO;
import io.github.gomestg.rest.dto.RequestInformationDTO;
import io.github.gomestg.rest.dto.RequestItemInformationDTO;
import io.github.gomestg.rest.dto.StatusAtualizationOrderDTO;
import io.github.gomestg.service.RequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private RequestService service;

    public RequestController(RequestService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a new Request")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Request saved successfully"),
            @ApiResponse(code = 400, message = "Validation Error ")
    })
    public Integer save(@RequestBody @Valid RequestDTO dto) {
        Request request = service.save(dto);
        return request.getId();
    }

    @GetMapping("{id}")
    @ApiOperation("Find request by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Request FOUND"),
            @ApiResponse(code = 404, message = "Request id entered was not found")
    })
    public RequestInformationDTO getById(@PathVariable Integer id) {
        return service
                .getCompleteOrder(id)
                .map(p -> convertRequest(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update Request Status by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Status updated successfully"),
            @ApiResponse(code = 404, message = "Request id entered was not found")
    })
    public void updateStatus(@PathVariable Integer id, @RequestBody StatusAtualizationOrderDTO dto) {
        service.updateStatus(id, Status.valueOf(dto.getNewStatus()));
    }

    private RequestInformationDTO convertRequest(Request request) {
        return RequestInformationDTO
                .builder()
                .id(request.getId())
                .cpf(request.getClient().getCpf())
                .requestDate(request.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .clientName(request.getClient().getName())
                .total(request.getTotal())
                .status(request.getStatus().name())
                .items(convertList(request.getDetails()))
                .build();
    }

    private List<RequestItemInformationDTO> convertList(List<RequestItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> RequestItemInformationDTO
                        .builder()
                        .productName(item.getProduct().getName())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }
}
