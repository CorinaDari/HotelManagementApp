package org.example;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

@Entity
public class Angajat extends Persoana {

    @NonNull private String pozitie;
    @NonNull private Double salariu;

    public Angajat(Integer idPersoana, String tipPersoana, String nume, String prenume, String pozitie, Double salariu){
        super(idPersoana, tipPersoana, nume, prenume);
        this.pozitie = pozitie;
        this.salariu = salariu;
    }


    @Override
    public String toString() {
        return "Angajat{" +
                "idPersoana=" + getIdPersoana() +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", pozitie='" + pozitie + '\'' +
                ", salariu=" + salariu +
                '}';
    }
}
