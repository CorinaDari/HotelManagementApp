package org.example;

import jakarta.persistence.*;
import lombok.*;

@Data //genereaza getteri si setteri
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //genereaza equals() and hashCode() pentru clasa marcata
@NoArgsConstructor //constructor fara parametri
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@RequiredArgsConstructor //constructor care accepta toate campurile marcate ca final sau non null
@Entity
public class Persoana {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy  = GenerationType.SEQUENCE)
    @NonNull
    private Integer idPersoana;
    @NonNull private String tipPersoana;
    @NonNull private String nume;
    @NonNull private String prenume;

    public Integer getIdPersoana() {
        return idPersoana;
    }

    public String getTipPersoana() {
        return tipPersoana;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

}


