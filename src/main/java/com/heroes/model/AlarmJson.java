package com.heroes.model;

/**
 * Created by Sebastian Boreback on 2017-01-24.
 */
public class AlarmJson {
    String magnetSensor;
    String pirSensor;

    public AlarmJson() {
    }

    public AlarmJson(String magnetSensor, String pirSensor) {
        this.magnetSensor = magnetSensor;
        this.pirSensor = pirSensor;
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

    @Override
    public String toString() {
        return "AlarmJson{" +
                "magnetSensor='" + magnetSensor + '\'' +
                ", pirSensor='" + pirSensor + '\'' +
                '}';
    }
}
