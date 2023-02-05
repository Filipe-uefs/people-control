package com.api.peoplecontrol.model;

import com.api.peoplecontrol.Utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tb_address")
@Data
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonView(Views.Create.class)
    @Column(name = "public_place")
    private String publicPlace;

    @JsonView(Views.Create.class)
    @Column(name = "cep", unique = true, nullable = false)
    private String cep;

    @JsonView(Views.Create.class)
    @Column(name = "number")
    private String number;

    @JsonView(Views.Create.class)
    @Column(name = "city")
    private String city;

    @JsonView(Views.Create.class)
    @Column(name = "is_main")
    private Boolean isMain;

    @ManyToOne
    @JoinColumn(name = "people_id")
    private PeopleModel people;


}
