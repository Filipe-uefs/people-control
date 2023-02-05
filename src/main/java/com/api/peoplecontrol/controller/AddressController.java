package com.api.peoplecontrol.controller;

import com.api.peoplecontrol.Utils.Views;
import com.api.peoplecontrol.model.AddressModel;
import com.api.peoplecontrol.service.AddressService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/createNewAddress/{peopleID}")
    public ResponseEntity<AddressModel> createNewAddress(@RequestBody @JsonView(Views.Create.class) AddressModel newAddress, @PathVariable UUID peopleID) {
        return new ResponseEntity<AddressModel>(addressService.saveNewAddress(newAddress, peopleID), HttpStatus.CREATED);
    }

    @PatchMapping("/changeAddressMainOfPeople")
    public ResponseEntity<String> changeAddressMainOfPeople(@RequestParam UUID peopleID, @RequestParam @Valid String cep) {
        return new ResponseEntity<>(addressService.updateMainAddress(peopleID, cep), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listAddressByPeople/{peopleID}")
    public ResponseEntity<List<AddressModel>> getListAddressByPeople(@PathVariable UUID peopleID) {
        return ResponseEntity.ok().body(addressService.getListAddressByPeople(peopleID));
    }
}
