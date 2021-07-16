package io.github.gomestg.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(name = "name", length = 100)
    @NotEmpty(message = "{field.client-name.mandatory}")
    private String name;
    @Column(length = 11)
    @NotEmpty(message = "{field.cpf.mandatory}")
    @CPF(message = "{field.cpf.invalid}")
    private String cpf;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Request> requests;

}
