package controller;

import com.api.peoplecontrol.PeopleControlApplication;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.service.AddressService;
import com.api.peoplecontrol.service.PeopleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PeopleControlApplication.class)
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    @Test
    public void searchPeopleByIDTest() throws Exception {

        UUID peopleID = UUID.randomUUID();
        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2020-11-15"));
        peopleModel.setName("Carlos");

        Mockito.when(peopleService.getPeopleById(peopleID)).thenReturn(peopleModel);

        mockMvc.perform(get("/peoples/searchPeopleByID/" + peopleID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Carlos")))
                .andExpect(jsonPath("$.birthDate", equalTo("2020-11-15")));
    }

    @Test
    public void createPeopleTest() throws Exception {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2020-11-15"));
        peopleModel.setName("Carlos");

        Mockito.when(peopleService.saveNewPeople(peopleModel)).thenReturn(peopleModel);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/peoples/createPeople")
                        .content(asJsonString(peopleModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Carlos")))
                .andExpect(jsonPath("$.birthDate", equalTo("2020-11-15")));
    }

    @Test
    public void replaceEmployeeTest() throws Exception {

        UUID peopleID = UUID.randomUUID();
        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2020-11-15"));
        peopleModel.setName("Carlos");

        Mockito.when(peopleService.replaceNewPeople(Mockito.any(PeopleModel.class), Mockito.any(UUID.class))).thenReturn(peopleModel);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/peoples/updatePeople/" + peopleID)
                        .content(asJsonString(peopleModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Carlos")))
                .andExpect(jsonPath("$.birthDate", equalTo("2020-11-15")));
    }

    @Test
    public void getAllPeoplesTest() throws Exception {


        List<PeopleModel> peopleModelList = generatePeople();
        PageRequest paginacao = PageRequest.of(0, 2);
        Page<PeopleModel> peopleModelPage = new PageImpl<>(peopleModelList, paginacao, 2);
        given(peopleService.getAllPeoples(Mockito.any(PageRequest.class))).willReturn(peopleModelPage);

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/peoples/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(peopleModelList.get(0).getName())))
                .andExpect(content().string(containsString(peopleModelList.get(1).getName())))
                .andDo(print());

    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new Jdk8Module());
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<PeopleModel> generatePeople() {
        PeopleModel people1 = new PeopleModel();
        people1.setName("Filipe");
        people1.setBirthDate(LocalDate.parse("2023-02-01"));
        PeopleModel people2 = new PeopleModel();
        people2.setName("Carlos");
        people2.setBirthDate(LocalDate.parse("2022-02-01"));
        return Arrays.asList(people1, people2);
    }

}
