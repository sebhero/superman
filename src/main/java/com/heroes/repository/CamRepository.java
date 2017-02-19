package com.heroes.repository;

import com.heroes.model.CamImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by Sebastian Boreback on 2017-02-06.
 */

@RepositoryRestResource
public interface CamRepository extends PagingAndSortingRepository<CamImage, Long> {
}

