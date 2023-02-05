package repository;


import com.api.peoplecontrol.PeopleControlApplication;
import com.api.peoplecontrol.model.PeopleModel;
import com.api.peoplecontrol.repository.PeopleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PeopleControlApplication.class)
public class PeopleRepositoryTest {

    @Mock
    private PeopleRepository peopleRepository;


    @Test
    public void findOneByIdTest() throws Exception {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setName("Filipe");
        peopleModel.setBirthDate(LocalDate.parse("2015-01-01"));
        given(peopleRepository.findOneById(Mockito.any(UUID.class))).willReturn(peopleModel);

        PeopleModel peopleReturn = peopleRepository.findOneById(UUID.randomUUID());

        Assert.assertEquals(peopleReturn.getName(), "Filipe");
        Assert.assertEquals(peopleReturn.getBirthDate(), LocalDate.parse("2015-01-01"));

    }
}
