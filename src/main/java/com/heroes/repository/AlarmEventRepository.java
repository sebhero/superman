package com.heroes.repository;

import com.heroes.model.AlarmEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Sebastian Boreback on 2017-01-31.
 */

/***
 * Handle communication with MySQL. Using JPA to connect to MySQL
 */
@RepositoryRestResource
public interface AlarmEventRepository extends PagingAndSortingRepository<AlarmEvent, Long> {
    List<AlarmEvent> findByMagnetSensor(@Param("magnetSensor") String magnetSensor);

}
