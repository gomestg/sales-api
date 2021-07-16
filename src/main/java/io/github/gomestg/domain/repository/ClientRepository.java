package io.github.gomestg.domain.repository;

import io.github.gomestg.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByNameLike(String name);

    boolean existsByName(String name);

    @Query(" select c from Client c left join fetch c.requests where c.id = :id ")
    Client findClientFetchRequests(Integer id);
}
