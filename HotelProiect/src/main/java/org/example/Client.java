package org.example;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

@Entity
public class Client extends Persoana {

    @NonNull private String nrTelefon;


   public Client(Integer idPersoana, String tipPersoana, String nume, String prenume, String nrTelefon){
        super(idPersoana, tipPersoana, nume, prenume);
        this.nrTelefon = nrTelefon;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idPersoana=" + getIdPersoana() +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                '}';
    }

    public String getNrTelefon() {
        return nrTelefon;
    }
}

