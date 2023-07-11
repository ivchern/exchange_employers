package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Repositories.ResourceRepository;
import com.ivchern.exchange_employers.Services.Teammate.TeammateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ResourceServiceImplTest {
    @InjectMocks
    ResourceServiceImpl service;

    @Mock
    TeammateService teammateService;

    @Mock
    ResourceRepository resourceRepository;

    @Test
    @DisplayName("Список ресурсов")
    void findAllResources_ReturnValidList() {
        var skills = new LinkedHashSet<Skill>();
        skills.add(new Skill(2L, "Java", "Java docs"));
        skills.add(new Skill(1L, "Spring", "Spring"));

        var teammate = new Teammate("Ivan", "Ivanov", "Junior JAVA dev", "Junior", 1L, 1L, skills);

        var resource1 = new Resource("Java developer", "From Home", new Date(2020, 10, 10), new Date(2022, 10, 10), 1L);
        resource1.setId(1L);
        resource1.setStatus(Status.ACTIVE);
        var resources = List.of(resource1);

        var resourcesDtoOnRequest = List.of(new ResourceDtoOnRequest(1L, "Junior JAVA dev", "Junior", "Java developer",
                "From Home", new Date(2020, 10, 10), new Date(2022, 10, 10),
                Set.of("Java", "Spring"), null, null, Status.ACTIVE));

        Mockito.when(resourceRepository.findAll()).thenReturn(resources);
        Mockito.when(teammateService.findById(1L)).thenReturn(Optional.of(teammate));

        var allResources = this.service.findAll();

        assertNotNull(allResources);
        assertEquals(resourcesDtoOnRequest, allResources);
    }
}