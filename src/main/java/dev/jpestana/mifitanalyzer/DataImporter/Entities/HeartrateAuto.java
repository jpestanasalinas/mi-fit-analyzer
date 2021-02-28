package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "heartrateAuto")
public class HeartrateAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "heartRate")
    private Integer heartRate;


    public HeartrateAuto(Date date, Timestamp time, Integer heartRate) {
        this.date = date;
        this.time = time;
        this.heartRate = heartRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartrateAuto that = (HeartrateAuto) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(heartRate, that.heartRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, heartRate);
    }
}
