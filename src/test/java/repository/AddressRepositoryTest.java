package repository;


import com.api.peoplecontrol.PeopleControlApplication;
import com.api.peoplecontrol.model.AddressModel;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.repository.AddressRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PeopleControlApplication.class)
public class AddressRepositoryTest {

    @Mock
    private AddressRepository addressRepository;


    @Test
    public void findByPeopleAndCepTest() {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setName("Filipe");
        peopleModel.setBirthDate(LocalDate.parse("2015-01-01"));

        AddressModel addressModel = new AddressModel();
        addressModel.setPeople(peopleModel);
        addressModel.setCep("44032566");
        addressModel.setNumber("5");
        addressModel.setIsMain(true);
        addressModel.setPublicPlace("qualquer");


        given(addressRepository.findByPeopleAndCep(Mockito.any(), Mockito.anyString())).willReturn(addressModel);

        AddressModel addressReturn = addressRepository.findByPeopleAndCep(peopleModel, "44032566");

        Assert.assertEquals(addressReturn.getCep(), "44032566");
        Assert.assertEquals(addressReturn.getNumber(), "5");
        Assert.assertEquals(addressReturn.getIsMain(), true);
        Assert.assertEquals(addressReturn.getPublicPlace(), "qualquer");
        Assert.assertEquals(addressReturn.getPeople().getName(), "Filipe");
        Assert.assertEquals(addressReturn.getPeople().getBirthDate(), LocalDate.parse("2015-01-01"));

    }

    @Test
    public void findAllByPeopleTest() {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setName("Filipe");
        peopleModel.setBirthDate(LocalDate.parse("2015-01-01"));

        given(addressRepository.findAllByPeople(peopleModel)).willReturn(generateListAddress());

        List<AddressModel> addressModelList = addressRepository.findAllByPeople(peopleModel);

        Assert.assertFalse(addressModelList.isEmpty());

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
