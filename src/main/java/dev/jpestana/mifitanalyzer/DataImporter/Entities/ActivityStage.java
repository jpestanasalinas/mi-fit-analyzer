package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "activitystage")
public class ActivityStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "start")
    private Timestamp start;

    @Column(name = "stop")
    private Timestamp stop;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "calories")
    private Float calories;

    @Column(name = "steps")
    private Integer steps;

    public ActivityStage() {
    }

    public ActivityStage(Date date, Timestamp start, Timestamp stop, Float distance, Float calories, Integer steps) {
        this.date = date;
        this.start = start;
        this.stop = stop;
        this.distance = distance;
        this.calories = calories;
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityStage that = (ActivityStage) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(start, that.start) &&
                Objects.equals(stop, that.stop) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(calories, that.calories) &&
                Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, start, stop, distance, calories, steps);
    }
}
