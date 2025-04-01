package org.example;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Factura {
    @NonNull @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFactura;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @NonNull
    @ManyToOne
    private RezervareCamera rezervareCamera;

    @NonNull
    private Double suma;

}
