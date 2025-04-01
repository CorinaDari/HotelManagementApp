package org.example;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity

public class RezervareCamera {
    @Id @GeneratedValue(strategy  = GenerationType.SEQUENCE)
    private Integer idRezervare;

    @ManyToOne @JoinColumn(name = "CLIENT_IDPERSOANA")
    @NonNull private Client client;

    @ManyToOne @JoinColumn(name = "CAMERA_NUMARCAMERA")
    @NonNull @Id private Camera camera;

    @NonNull private String dataCheckIn;

    @NonNull private String dataCheckOut;
    @OneToMany(mappedBy = "rezervare",cascade = CascadeType.ALL,orphanRemoval = true)
    private ArrayList<ServiciuCamera> serviciiCamera;

    public RezervareCamera(Integer idRezervare, @NonNull Client client, @NonNull Camera camera, @NonNull String dataCheckIn, @NonNull String dataCheckOut) {
        this.idRezervare = idRezervare;
        this.client = client;
        this.camera = camera;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
    }

    public Integer getId() {
        return idRezervare;
    }

    public Client getClient() {
        return client;
    }

    public Camera getCamera() {
        return camera;
    }

    public String getDataCheckIn() {
        return dataCheckIn;
    }

    public String getDataCheckOut() {
        return dataCheckOut;
    }


}
