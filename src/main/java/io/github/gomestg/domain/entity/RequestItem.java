package io.github.gomestg.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column
    private Integer quantity;

}
