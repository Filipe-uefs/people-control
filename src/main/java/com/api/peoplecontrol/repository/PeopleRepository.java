package com.api.peoplecontrol.repository;

import com.api.peoplecontrol.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleModel, UUID> {
    PeopleModel findOneById(UUID id);

}
