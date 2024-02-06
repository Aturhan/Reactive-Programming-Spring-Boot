package com.abdullah.repository;

import com.abdullah.entity.JobAdvert;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAdvertRepository extends ReactiveMongoRepository<JobAdvert,String> {
}
