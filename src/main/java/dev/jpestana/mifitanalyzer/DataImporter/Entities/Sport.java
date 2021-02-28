package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sport")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private Integer type;

    @Column(name = "startTime")
    private Timestamp startTime;

    @Column(name = "sportTime")
    private Integer sportTime;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "maxPace")
    private Integer maxPace;

    @Column(name = "minPace")
    private Integer minPace;

    @Column(name = "avgPace")
    private Integer avgPace;

    @Column(name = "calories")
    private Float calories;

    public Sport(Integer type, Timestamp startTime, Integer sportTime, Integer distance, Integer maxPace, Integer minPace, Integer avgPace, Float calories) {
        this.type = type;
        this.startTime = startTime;
        this.sportTime = sportTime;
        this.distance = distance;
        this.maxPace = maxPace;
        this.minPace = minPace;
        this.avgPace = avgPace;
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sport sport = (Sport) o;
        return Objects.equals(type, sport.type) &&
                Objects.equals(startTime, sport.startTime) &&
                Objects.equals(sportTime, sport.sportTime) &&
                Objects.equals(distance, sport.distance) &&
                Objects.equals(maxPace, sport.maxPace) &&
                Objects.equals(minPace, sport.minPace) &&
                Objects.equals(avgPace, sport.avgPace) &&
                Objects.equals(calories, sport.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, startTime, sportTime, distance, maxPace, minPace, avgPace, calories);
    }
}
