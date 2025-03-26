package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ServiciuCamera {
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idServiciu;
    @ManyToOne
    private RezervareCamera rezervare;
    @NonNull private String tipServiciu;

    public Integer getIdServiciu() {
        return idServiciu;
    }

    public RezervareCamera getRezervare() {
        return rezervare;
    }

    public String getTipServiciu() {
        return tipServiciu;
    }
}
