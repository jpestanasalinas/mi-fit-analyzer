package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sleep")
public class Sleep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "lastSyncTime")
    private Timestamp lastSyncTime;

    @Column(name = "deepSleepTime")
    private Integer deepSleepTime;

    @Column(name = "shallowSleepTime")
    private Integer shallowSleepTime;

    @Column(name = "wakeTime")
    private Integer wakeTime;

    @Column(name = "start")
    private Timestamp start;

    @Column(name = "stop")
    private Timestamp stop;

    public Sleep(Date date, Timestamp lastSyncTime, Integer deepSleepTime, Integer shallowSleepTime, Integer wakeTime, Timestamp start, Timestamp stop) {
        this.date = date;
        this.lastSyncTime = lastSyncTime;
        this.deepSleepTime = deepSleepTime;
        this.shallowSleepTime = shallowSleepTime;
        this.wakeTime = wakeTime;
        this.start = start;
        this.stop = stop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sleep sleep = (Sleep) o;
        return Objects.equals(lastSyncTime, sleep.lastSyncTime) &&
                Objects.equals(deepSleepTime, sleep.deepSleepTime) &&
                Objects.equals(shallowSleepTime, sleep.shallowSleepTime) &&
                Objects.equals(wakeTime, sleep.wakeTime) &&
                Objects.equals(start, sleep.start) &&
                Objects.equals(stop, sleep.stop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastSyncTime, deepSleepTime, shallowSleepTime, wakeTime, start, stop);
    }

}
