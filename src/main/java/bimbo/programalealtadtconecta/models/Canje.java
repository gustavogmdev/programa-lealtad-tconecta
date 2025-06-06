package bimbo.programalealtadtconecta.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "canje")
public class Canje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "recompensa_id", nullable = false)
    private Recompensa recompensa;

    @Column(name = "puntos_utilizados", nullable = false)
    private Integer puntosUtilizados;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

}
