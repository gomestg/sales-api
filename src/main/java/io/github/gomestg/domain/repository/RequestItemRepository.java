package io.github.gomestg.domain.repository;

import io.github.gomestg.domain.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestItemRepository extends JpaRepository<RequestItem, Integer> {
}
