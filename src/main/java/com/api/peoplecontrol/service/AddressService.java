package com.api.peoplecontrol.service;

import com.api.peoplecontrol.model.AddressModel;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PeopleService peopleService;

    public List<AddressModel> getListAddressByPeople(UUID peopleID) {
        return addressRepository.findAllByPeople(peopleService.getPeopleById(peopleID));
    }
    public AddressModel saveNewAddress(AddressModel newAddressModel, UUID peopleID) {

        PeopleModel people = peopleService.getPeopleById(peopleID);

        if (Objects.nonNull(people)) {

            AddressModel addressModel = addressRepository.findByPeopleAndCep(people, newAddressModel.getCep());
            if (Objects.isNull(addressModel) || !addressModel.getCep().equals(newAddressModel.getCep())) {
                newAddressModel.setPeople(people);
                updateMainAddressOfPeople(people, newAddressModel.getIsMain());
                try {
                    return addressRepository.save(newAddressModel);
                } catch (Exception e) {
                    throw new RuntimeException("Não foi possivel salvar esse endereço", e);
                }
            }
            throw new RuntimeException("Esse cep já está cadastrado em nossa base");
        }

        throw new RuntimeException("Esse ID para pessoa não consta na nossa Base");
    }

    public String updateMainAddress(UUID peopleID, String cep) {
        PeopleModel people = peopleService.getPeopleById(peopleID);
        if (Objects.isNull(people)) {
            throw new RuntimeException("Esse ID para pessoa não consta na nossa Base");
        }
        AddressModel address = addressRepository.findByPeopleAndCep(people, cep);
        if (Objects.isNull(address)) {
            throw new RuntimeException("Esse CEP não consta em nossa base para essa pessoa");
        }
        updateMainAddressOfPeople(people, Boolean.TRUE);
        address.setIsMain(Boolean.TRUE);
        addressRepository.save(address);
        return "update success";
    }

    public void updateMainAddressOfPeople(PeopleModel peopleModel, boolean isMain) {
        if (isMain) {
            addressRepository.findAllByPeople(peopleModel).forEach(currentPeople -> {
                currentPeople.setIsMain(Boolean.FALSE);
                addressRepository.save(currentPeople);
            });
        }
    }

}
