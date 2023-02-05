package com.api.peoplecontrol.service;

import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public Page<PeopleModel> getAllPeoples(Pageable pageable) {
        return peopleRepository.findAll(pageable);
    }
    public PeopleModel getPeopleById(UUID id) {
        return peopleRepository.findOneById(id);
    }

    public PeopleModel saveNewPeople(PeopleModel peopleModel) {
        return peopleRepository.save(peopleModel);
    }

    public PeopleModel replaceNewPeople (PeopleModel newPeople, UUID id) {
        return peopleRepository.findById(id).map(people -> {
            people.setBirthDate(newPeople.getBirthDate());
            people.setName(newPeople.getName());
            return peopleRepository.save(people);
        }).orElseGet(() -> peopleRepository.save(newPeople));
    }
}
