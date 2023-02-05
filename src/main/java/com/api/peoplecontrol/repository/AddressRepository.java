package com.api.peoplecontrol.repository;

import com.api.peoplecontrol.model.AddressModel;
import com.api.peoplecontrol.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {

    AddressModel findByPeopleAndCep(PeopleModel people, String cep);

    List<AddressModel> findAllByPeople(PeopleModel peopleById);
}
