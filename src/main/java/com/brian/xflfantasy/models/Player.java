package com.brian.xflfantasy.models;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 50)
    private String first_name;

    @NotNull
    @Size(max = 50)
    private String last_name;

    @NotNull
    @Size(max = 50)
    private String team_city;

    @NotNull
    @Size(max = 50)
    private String team_name;

    @NotNull
    @Size(max = 50)
    private String position;

    @NotNull
    private Double total_points;

    private int team_id;
    @Nullable
    private boolean starting;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player that = (Player) o;
        return id == that.id && Objects.equals(first_name, that.first_name) && Objects.equals(last_name, that.last_name) && Objects.equals(team_city, that.team_city) && Objects.equals(team_name, that.team_name) && Objects.equals(position, that.position) && Objects.equals(total_points, that.total_points) && Objects.equals(team_id, that.team_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, team_city, team_name, position, total_points, team_id);
    }
}
