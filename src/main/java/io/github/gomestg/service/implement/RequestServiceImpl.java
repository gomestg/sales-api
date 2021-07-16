package io.github.gomestg.service.implement;

import io.github.gomestg.domain.entity.Client;
import io.github.gomestg.domain.entity.Product;
import io.github.gomestg.domain.entity.Request;
import io.github.gomestg.domain.entity.RequestItem;
import io.github.gomestg.domain.enums.Status;
import io.github.gomestg.domain.repository.ClientRepository;
import io.github.gomestg.domain.repository.ProductRepository;
import io.github.gomestg.domain.repository.RequestItemRepository;
import io.github.gomestg.domain.repository.RequestRepository;
import io.github.gomestg.exception.BusinessRuleException;
import io.github.gomestg.exception.OrderNotFoundException;
import io.github.gomestg.rest.dto.RequestDTO;
import io.github.gomestg.rest.dto.RequestItemDTO;
import io.github.gomestg.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRep;
    private final ClientRepository clientRep;
    private final ProductRepository productRep;
    private final RequestItemRepository requestItemsRep;

    @Override
    @Transactional
    public Request save(RequestDTO dto) {
        Client client = clientRep
                .findById(dto.getClient())
                .orElseThrow(() -> new BusinessRuleException("Invalid CLIENT_ID"));

        Request request = new Request();
        request.setTotal(dto.getTotal());
        request.setDate(LocalDate.now());
        request.setClient(client);
        request.setStatus(Status.REALIZED);

        List<RequestItem> details = convertItems(request, dto.getDetails());
        requestRep.save(request);
        requestItemsRep.saveAll(details);
        request.setDetails(details);
        return request;
    }

    @Override
    public Optional<Request> getCompleteOrder(Integer id) {
        return requestRep.findByIdFetchDetails(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Status status) {
        requestRep
                .findById(id)
                .map(request -> {
                    request.setStatus(status);
                    return requestRep.save(request);
                }).orElseThrow(() -> new OrderNotFoundException());
    }

    private List<RequestItem> convertItems(Request request, List<RequestItemDTO> items) {
        if (items.isEmpty()) {
            throw new BusinessRuleException("Unable to make a request without items");
        }

        return items
                .stream()
                .map(dto -> {
                    Product product = productRep
                            .findById(dto.getProduct())
                            .orElseThrow(() -> new BusinessRuleException("Invalid PRODUCT_ID"));

                    RequestItem requestItem = new RequestItem();
                    requestItem.setQuantity(dto.getQuantity());
                    requestItem.setRequest(request);
                    requestItem.setProduct(product);
                    return requestItem;
                }).collect(Collectors.toList());
    }
}
