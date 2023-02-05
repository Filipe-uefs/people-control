package controller;


import com.api.peoplecontrol.PeopleControlApplication;
import com.api.peoplecontrol.model.AddressModel;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PeopleControlApplication.class)

public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void getListAddressByPeopleTest() throws Exception {

        UUID peopleID = UUID.randomUUID();
        Mockito.when(addressService.getListAddressByPeople(peopleID)).thenReturn(generateListAddressModel());
        mockMvc.perform(get("/address/listAddressByPeople/" + peopleID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].city", equalTo("Filipe")))
                .andExpect(jsonPath("$[0].number", equalTo("5")))
                .andExpect(jsonPath("$[0].isMain", equalTo(true)));

    }

    @Test
    public void changeAddressMainOfPeopleTest() throws Exception {

        UUID peopleID = UUID.randomUUID();
        String cep = "4444";
        Mockito.when(addressService.updateMainAddress(peopleID, cep)).thenReturn("update success");
        mockMvc.perform(patch("/address/changeAddressMainOfPeople?peopleID=" + peopleID + "&cep=" + cep)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(MockMvcResultMatchers.content().string("update success"));

    }

    @Test
    public void createNewAddressTest() throws Exception {

        UUID peopleID = UUID.randomUUID();
        AddressModel addressModel = new AddressModel();
        addressModel.setCep("44444");
        addressModel.setPeople(new PeopleModel());
        addressModel.setCity("Feira de Santana");
        addressModel.setIsMain(true);
        addressModel.setNumber("5");
        addressModel.setPublicPlace("Rua Salmão Atum");
        Mockito.when(addressService.saveNewAddress(addressModel, peopleID)).thenReturn(addressModel);


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/address/createNewAddress/" + peopleID)
                        .content(asJsonString(addressModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    private List<AddressModel> generateListAddressModel() {
        List<AddressModel> addressModelList = new ArrayList<>();
        AddressModel addressModel = new AddressModel();
        addressModel.setIsMain(true);
        addressModel.setCity("Filipe");
        addressModel.setNumber("5");
        addressModelList.add(addressModel);

        return addressModelList;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
