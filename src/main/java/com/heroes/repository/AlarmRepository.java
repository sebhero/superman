package com.heroes.repository;

import com.heroes.model.AlarmEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Sebastian Boreback on 2017-01-24.
 */
public interface AlarmRepository extends CrudRepository<AlarmEvent,Long>{

//    List<AlarmEvent> findByType(String );
}
