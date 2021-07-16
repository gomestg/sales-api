package io.github.gomestg.service;

import io.github.gomestg.domain.entity.Request;
import io.github.gomestg.domain.enums.Status;
import io.github.gomestg.rest.dto.RequestDTO;

import java.util.Optional;

public interface RequestService {
    Request save(RequestDTO dto);

    Optional<Request> getCompleteOrder(Integer id);

    void updateStatus(Integer id, Status status);
}
