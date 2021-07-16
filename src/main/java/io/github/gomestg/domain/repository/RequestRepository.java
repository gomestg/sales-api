package io.github.gomestg.domain.repository;

import io.github.gomestg.domain.entity.Client;
import io.github.gomestg.domain.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByClient(Client client);

    @Query("select p from Request p left join fetch p.details where p.id = :id ")
    Optional<Request> findByIdFetchDetails(@Param("id") Integer id);
}
