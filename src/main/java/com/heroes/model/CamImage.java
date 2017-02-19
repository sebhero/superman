package com.heroes.model;

import javax.persistence.*;
import java.awt.*;

/**
 * Created by Sebastian Boreback on 2017-02-06.
 */
@Entity
@Table(name = "camimage")
public class CamImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long alarmeventId;
    @Lob
    private byte[] image;

    public CamImage(long alarmeventId, byte[] image) {
        this.alarmeventId = alarmeventId;
        this.image = image;
    }

    public long getAlarmeventId() {
        return alarmeventId;
    }

    public void setAlarmeventId(long alarmeventId) {
        this.alarmeventId = alarmeventId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
