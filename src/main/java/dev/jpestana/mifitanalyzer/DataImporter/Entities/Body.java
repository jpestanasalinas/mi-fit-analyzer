package dev.jpestana.mifitanalyzer.DataImporter.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "body")
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "height")
    private Float height;

    @Column(name = "bmi")
    private Integer bmi;

    @Column(name = "fatRate")
    private Float fatRate;

    @Column(name = "bodyWaterRate")
    private Float bodyWaterRate;

    @Column(name = "boneMass")
    private Float boneMass;

    @Column(name = "metabolism")
    private Float metabolism;

    @Column(name = "muscleRate")
    private Float muscleRate;

    @Column(name = "visceralFat")
    private Float visceralFat;

    @Column(name = "impedance")
    private Float impedance;

    public Body(Timestamp timestamp, Float weight, Float height, Integer bmi, Float fatRate, Float bodyWaterRate,
                Float boneMass, Float metabolism, Float muscleRate, Float visceralFat, Float impedance) {
        this.timestamp = timestamp;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.fatRate = fatRate;
        this.bodyWaterRate = bodyWaterRate;
        this.boneMass = boneMass;
        this.metabolism = metabolism;
        this.muscleRate = muscleRate;
        this.visceralFat = visceralFat;
        this.impedance = impedance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Objects.equals(timestamp, body.timestamp) &&
                Objects.equals(weight, body.weight) &&
                Objects.equals(height, body.height) &&
                Objects.equals(bmi, body.bmi) &&
                Objects.equals(fatRate, body.fatRate) &&
                Objects.equals(bodyWaterRate, body.bodyWaterRate) &&
                Objects.equals(boneMass, body.boneMass) &&
                Objects.equals(metabolism, body.metabolism) &&
                Objects.equals(muscleRate, body.muscleRate) &&
                Objects.equals(visceralFat, body.visceralFat) &&
                Objects.equals(impedance, body.impedance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, weight, height, bmi, fatRate, bodyWaterRate, boneMass, metabolism, muscleRate, visceralFat, impedance);
    }
}
