package org.example;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//constructor care accepta toate campurile marcate ca final sau non null
@Entity
public class Camera {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numarCamera;
    @NonNull private Integer etaj;
    @NonNull private String tipCamera;
    @NonNull private Boolean ocupata;

    public Camera(int i, int i1, String simpla, boolean b) {
    }

}
