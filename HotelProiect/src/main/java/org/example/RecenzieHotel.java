package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class RecenzieHotel {
    @EqualsAndHashCode.Include
    @NonNull @Id
    private Integer idRecenzie;
    @NonNull private Client client;
    @NonNull private String comentariu;
    @NonNull private Double nota;
}
