package com.heroes.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by Sebastian Boreback on 2017-01-24.
 */

/**
 * A model of the alarm
 */
@Entity
@Table(name = "alarms")
public class AlarmEvent {
    //REST /AlarmEvents

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String magnetSensor;
    private String pirSensor;
    private long timestamp;

    public AlarmEvent() {
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public AlarmEvent(String magnetSensor, String pirSensor) {
        this.magnetSensor = magnetSensor;
        this.pirSensor = pirSensor;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public long getId() {
        return id;
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
