package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "activityminute")
public class ActivityMinute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "steps")
    private Integer steps;

    public ActivityMinute() {}

    public ActivityMinute(Date date, Timestamp time, Integer steps) {
        this.date = date;
        this.time = time;
        this.steps = steps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setLastSyncTime(Timestamp time) {
        this.time = time;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityMinute activity = (ActivityMinute) o;
        return Objects.equals(id, activity.id) &&
                Objects.equals(date, activity.date) &&
                Objects.equals(time, activity.time) &&
                Objects.equals(steps, activity.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, steps);
    }
}
