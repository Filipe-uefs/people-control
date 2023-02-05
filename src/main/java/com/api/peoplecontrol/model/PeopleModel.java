package com.api.peoplecontrol.model;

import com.api.peoplecontrol.Utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_peoples")
@Data
public class PeopleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @JsonView(Views.Create.class)
    @Column(name = "name", nullable = false)
    private String name;

    @JsonView(Views.Create.class)
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

}
