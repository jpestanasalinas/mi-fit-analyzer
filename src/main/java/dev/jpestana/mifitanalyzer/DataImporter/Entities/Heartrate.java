package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "heartrate")
public class Heartrate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "lastSyncTime")
    private Timestamp lastSyncTime;

    @Column(name = "heartRate")
    private Integer heartRate;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Heartrate(Date date, Timestamp lastSyncTime, Integer heartRate, Timestamp timestamp) {
        this.date = date;
        this.lastSyncTime = lastSyncTime;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heartrate heartrate = (Heartrate) o;
        return Objects.equals(date, heartrate.date) &&
                Objects.equals(lastSyncTime, heartrate.lastSyncTime) &&
                Objects.equals(heartRate, heartrate.heartRate) &&
                Objects.equals(timestamp, heartrate.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, lastSyncTime, heartRate, timestamp);
    }
}
