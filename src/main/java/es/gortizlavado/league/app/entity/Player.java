package es.gortizlavado.league.app.entity;

import es.gortizlavado.league.app.entity.hibernate.type.PostgresIdUUIDType;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players")
@TypeDefs({
        @TypeDef(name = "pg-id-uuid", typeClass = PostgresIdUUIDType.class)
}) // use this to avoid the issue 'unrecognized id type : pg-uuid -> java.util.UUID'
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator="IdOrGenerated") // when GenerationType.IDENTITY strategy is enabled the parameter id not appear in the insert sql
    @GenericGenerator(name="IdOrGenerated",
            strategy="es.gortizlavado.league.app.entity.hibernate.generator.UseIdOrGenerate")
    @Type(type="pg-id-uuid", parameters = @Parameter(name = "column", value = "id"))
    private UUID id;

    private String name;

    private String lastname;

    private String surname;

    @Column(name = "date_birthday")
    private LocalDate dateOfBirthday;

    @Column(name = "player_position")
    private Position position;

    private Team team;

    private Status status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Player player = (Player) o;
        return id != null && Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
