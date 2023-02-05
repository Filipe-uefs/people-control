package service;

import com.api.peoplecontrol.PeopleControlApplication;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.repository.PeopleRepository;
import com.api.peoplecontrol.service.PeopleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PeopleControlApplication.class)
public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleService peopleService;

    @Test
    public void getAllPeoplesTest() {

        PageRequest paginacao = PageRequest.of(0, 2);
        Page<PeopleModel> peopleModelPage = new PageImpl<>(generatePeople(), paginacao, 2);
        given(peopleRepository.findAll(Mockito.any(PageRequest.class))).willReturn(peopleModelPage);
        Page<PeopleModel> peoplePageReturn = peopleService.getAllPeoples(paginacao);

        Assert.assertEquals(peoplePageReturn.getContent().size(), 2);
        Assert.assertEquals(peoplePageReturn.getContent().get(0).getName(), "Filipe");
        Assert.assertEquals(peoplePageReturn.getContent().get(0).getBirthDate(), LocalDate.parse("2023-02-01"));
        Assert.assertEquals(peoplePageReturn.getContent().get(1).getName(), "Carlos");
        Assert.assertEquals(peoplePageReturn.getContent().get(1).getBirthDate(), LocalDate.parse("2022-02-01"));

    }

    @Test
    public void getPeopleByIdTest() {

        UUID peopleID = UUID.randomUUID();
        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2021-11-15"));
        peopleModel.setName("Filipe");
        peopleModel.setId(peopleID);
        given(peopleRepository.findOneById(Mockito.any(UUID.class))).willReturn(peopleModel);
        PeopleModel peopleReturn = peopleService.getPeopleById(peopleID);

        Assert.assertEquals(peopleReturn.getBirthDate(), peopleModel.getBirthDate());
        Assert.assertEquals(peopleReturn.getId(), peopleModel.getId());
        Assert.assertEquals(peopleReturn.getName(), peopleModel.getName());

    }

    @Test
    public void saveNewPeopleTest() {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2021-11-15"));
        peopleModel.setName("Filipe");
        peopleModel.setId(UUID.randomUUID());
        given(peopleRepository.save(peopleModel)).willReturn(peopleModel);
        PeopleModel peopleReturn = peopleService.saveNewPeople(peopleModel);

        Assert.assertEquals(peopleReturn.getBirthDate(), peopleModel.getBirthDate());
        Assert.assertEquals(peopleReturn.getId(), peopleModel.getId());
        Assert.assertEquals(peopleReturn.getName(), peopleModel.getName());

    }

    @Test
    public void replaceNewPeopleTest() {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2020-11-15"));
        peopleModel.setName("Carlos");
        peopleModel.setId(UUID.randomUUID());

        PeopleModel peopleModel1 = new PeopleModel();
        peopleModel1.setBirthDate(LocalDate.parse("2021-11-15"));
        peopleModel1.setName("Filipe");
        peopleModel1.setId(UUID.randomUUID());


        given(peopleRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.of(peopleModel1));
        given(peopleRepository.save(peopleModel1)).willReturn(peopleModel);

        PeopleModel peopleReturn = peopleService.replaceNewPeople(peopleModel1, UUID.randomUUID());

        Assert.assertEquals(peopleReturn.getName(), "Carlos");
        Assert.assertEquals(peopleReturn.getBirthDate(), LocalDate.parse("2020-11-15"));


    }

    @Test
    public void replaceNewPeopleElseGetTest() {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setBirthDate(LocalDate.parse("2020-11-15"));
        peopleModel.setName("Carlos");
        peopleModel.setId(UUID.randomUUID());


        given(peopleRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.empty());
        given(peopleRepository.save(peopleModel)).willReturn(peopleModel);

        PeopleModel peopleReturn = peopleService.replaceNewPeople(peopleModel, UUID.randomUUID());

        Assert.assertEquals(peopleReturn.getName(), "Carlos");
        Assert.assertEquals(peopleReturn.getBirthDate(), LocalDate.parse("2020-11-15"));


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
