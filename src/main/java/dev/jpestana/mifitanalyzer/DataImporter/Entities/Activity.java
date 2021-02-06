package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "last_sync_date")
    private Date lastSyncTime;

    @Column(name = "steps")
    private Integer steps;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "run_distance")
    private Float runDistance;

    @Column(name = "calories")
    private Float calories;

    public Activity() {}

    public Activity(Date date, Date lastSyncTime, Integer steps, Float distance, Float runDistance, Float calories) {
        this.date = date;
        this.lastSyncTime = lastSyncTime;
        this.steps = steps;
        this.distance = distance;
        this.runDistance = runDistance;
        this.calories = calories;
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

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getRunDistance() {
        return runDistance;
    }

    public void setRunDistance(Float runDistance) {
        this.runDistance = runDistance;
    }

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }
}
