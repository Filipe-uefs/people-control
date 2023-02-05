package service;


import com.api.peoplecontrol.PeopleControlApplication;
import com.api.peoplecontrol.model.AddressModel;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.repository.AddressRepository;
import com.api.peoplecontrol.service.AddressService;
import com.api.peoplecontrol.service.PeopleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PeopleControlApplication.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Mock
    private PeopleService peopleService;

    @Test
    public void getListAddressByPeopleTest() {

        UUID peopleID = UUID.randomUUID();

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setName("Filipe");
        peopleModel.setBirthDate(LocalDate.parse("2015-01-01"));

        when(addressRepository.findAllByPeople(Mockito.any(PeopleModel.class))).thenReturn(generateListAddress());
        when(peopleService.getPeopleById(peopleID)).thenReturn(peopleModel);
        List<AddressModel> addressModelList = addressService.getListAddressByPeople(peopleID);
        Assert.assertFalse(addressModelList.isEmpty());
        Assert.assertEquals(addressModelList.size(), 2);
    }

    @Test
    public void saveNewAddressTest() {

        UUID peopleID = UUID.randomUUID();

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setName("Filipe");
        peopleModel.setBirthDate(LocalDate.parse("2015-01-01"));

        AddressModel addressModel = new AddressModel();
        addressModel.setPeople(new PeopleModel());
        addressModel.setCep("44032566");
        addressModel.setNumber("5");
        addressModel.setIsMain(true);
        addressModel.setPublicPlace("qualquer");

        when(addressRepository.findAllByPeople(Mockito.any(PeopleModel.class))).thenReturn(generateListAddress());
        when(peopleService.getPeopleById(peopleID)).thenReturn(peopleModel);
        AddressModel addressModelList = addressService.saveNewAddress(addressModel, peopleID);
    }

    @Test
    public void updateMainAddressTest() {

        UUID peopleID = UUID.randomUUID();

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setName("Filipe");
        peopleModel.setBirthDate(LocalDate.parse("2015-01-01"));

        AddressModel addressModel = new AddressModel();
        addressModel.setPeople(new PeopleModel());
        addressModel.setCep("44032566");
        addressModel.setNumber("5");
        addressModel.setIsMain(true);
        addressModel.setPublicPlace("qualquer");

        when(peopleService.getPeopleById(peopleID)).thenReturn(peopleModel);
        when(addressRepository.findByPeopleAndCep(Mockito.any(PeopleModel.class), Mockito.anyString())).thenReturn(addressModel);
        when(addressRepository.save(Mockito.any(AddressModel.class))).thenReturn(addressModel);
        when(addressRepository.findAllByPeople(Mockito.any(PeopleModel.class))).thenReturn(new ArrayList<>());

        String response = addressService.updateMainAddress(peopleID, "44444");
        Assert.assertEquals(response, "update success");
    }

    private List<AddressModel> generateListAddress() {

        AddressModel addressModel = new AddressModel();
        addressModel.setPeople(new PeopleModel());
        addressModel.setCep("44032566");
        addressModel.setNumber("5");
        addressModel.setIsMain(true);
        addressModel.setPublicPlace("qualquer");

        AddressModel addressModel1 = new AddressModel();
        addressModel1.setPeople(new PeopleModel());
        addressModel1.setCep("44032566");
        addressModel1.setNumber("5");
        addressModel1.setIsMain(true);
        addressModel1.setPublicPlace("qualquer");

        return Arrays.asList(addressModel, addressModel1);
    }

}
