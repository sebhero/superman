package com.heroes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

/**
 * Created by Sebastian Boreback on 2017-01-24.
 */
@Entity
public class AlarmEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    String magnetSensor;
    String pirSensor;
    long timestamp;

    public AlarmEvent() {
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public AlarmEvent(String magnetSensor, String pirSensor) {
        this.magnetSensor = magnetSensor;
        this.pirSensor = pirSensor;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public String getMagnetSensor() {
        return magnetSensor;
    }

    public void setMagnetSensor(String magnetSensor) {
        this.magnetSensor = magnetSensor;
    }

    public String getPirSensor() {
        return pirSensor;
    }

    public void setPirSensor(String pirSensor) {
        this.pirSensor = pirSensor;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "id=" + id +
                ", magnetSensor='" + magnetSensor + '\'' +
                ", pirSensor='" + pirSensor + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
