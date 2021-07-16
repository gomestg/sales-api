package io.github.gomestg.domain.entity;

import io.github.gomestg.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "request_date")
    private LocalDate date;
    @Column(scale = 2, precision = 20)
    private BigDecimal total;
    @OneToMany(mappedBy = "request")
    private List<RequestItem> details;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
