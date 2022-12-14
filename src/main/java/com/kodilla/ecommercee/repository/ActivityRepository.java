package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Activity;
import com.kodilla.ecommercee.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    Activity save (Activity activity);

    List<Activity> findAll();
}
