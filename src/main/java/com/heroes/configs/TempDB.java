package com.heroes.configs;

import com.heroes.model.AlarmEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Sebastian Boreback on 2017-01-24.
 */

@Service
public class TempDB {

    ArrayList<AlarmEvent> alarmEvents = new ArrayList<>();

    public ArrayList<AlarmEvent> getAlarmEvents() {
        return alarmEvents;
    }

    public void setAlarmEvents(ArrayList<AlarmEvent> alarmEvents) {
        this.alarmEvents = alarmEvents;
    }
}
