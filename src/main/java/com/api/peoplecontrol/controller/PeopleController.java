package com.api.peoplecontrol.controller;

import com.api.peoplecontrol.Utils.Views;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.service.PeopleService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/peoples")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/createPeople")
    public ResponseEntity<PeopleModel> createPeople(@RequestBody @JsonView(Views.Create.class) PeopleModel peopleModel) {
        return new ResponseEntity<>(peopleService.saveNewPeople(peopleModel), HttpStatus.CREATED);
    }

    @PutMapping("/updatePeople/{id}")
    public ResponseEntity<PeopleModel> replaceEmployee(@RequestBody @JsonView(Views.Create.class) PeopleModel newPeople, @PathVariable UUID id) {
        return new ResponseEntity<>(peopleService.replaceNewPeople(newPeople, id), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<PeopleModel>> getAllPeoples(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(peopleService.getAllPeoples(pageable));
    }

    @GetMapping("/searchPeopleByID/{id}")
    public ResponseEntity<PeopleModel> getPeopleById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(peopleService.getPeopleById(id));
    }

}
