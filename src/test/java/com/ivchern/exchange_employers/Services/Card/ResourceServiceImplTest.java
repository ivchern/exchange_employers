package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Repositories.ResourceRepository;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import com.ivchern.exchange_employers.Repositories.TeammateRepository;
import com.ivchern.exchange_employers.Services.Teammate.TeammateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({MockitoExtension.class})
class ResourceServiceImplTest {
    @InjectMocks
    ResourceServiceImpl service;
    @Mock
    TeammateService teammateService;
    @Mock
    SkillRepository skillRepository;
    @Mock
    ResourceRepository resourceRepository;
    @Mock
    TeammateRepository teammateRepository;

    ResourceServiceImplTest() {
    }

    @Test
    @DisplayName("Должен возвращать значение null, когда teammate does не существует")
    void getResourceDTOEntityWhenTeammateDoesNotExist() {
        Resource resource = new Resource();
        resource.setTeammateId(1L);
        Specification<Skill> specSkill = null;
        Specification<Teammate> specTeammate = null;
        Mockito.when(this.teammateService.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.emptyList());
        ResourceDtoOnRequest result = this.service.getResourceDTOEntity(resource, (Specification)specSkill, (Specification)specTeammate);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Должен вернуть  тиммейта")
    void getResourceDTOEntityWhenOnlySpecTeammateIsNotNull() {
        Resource resource = new Resource();
        resource.setTeammateId(1L);
        Specification<Skill> specSkill = null;
        Specification<Teammate> specTeammate = null;
        Teammate teammate = new Teammate();
        teammate.setJobTitle("Software Engineer");
        teammate.setRank("Senior");
        teammate.setSkills(Collections.singleton(new Skill(1L, "Java", "Programming language")));
        Mockito.when(this.teammateService.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.singletonList(teammate));
        ResourceDtoOnRequest result = this.service.getResourceDTOEntity(resource, (Specification)specSkill, (Specification)specTeammate);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Software Engineer", result.getJobTitle());
        Assertions.assertEquals("Senior", result.getRank());
        Assertions.assertEquals(Collections.singleton("Java"), result.getSkills());
    }

    @Test
    @DisplayName("Должен возвращать значение null, если тиммейтне найден")
    void getResourceDTOEntityWhenTeammateDoesNotHaveRequiredSkills() {
        Resource resource = new Resource();
        resource.setTeammateId(1L);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        skill1.setSkill("Java");
        skill1.setDescription("Java programming");
        Skill skill2 = new Skill();
        skill2.setId(2L);
        skill2.setSkill("Python");
        skill2.setDescription("Python programming");
        Teammate teammate = new Teammate();
        teammate.setId(1L);
        teammate.setSkills(new HashSet(Arrays.asList(skill1)));
        Specification<Skill> specSkill = (Specification)Mockito.mock(Specification.class);
        Specification<Teammate> specTeammate = null;
        Mockito.when(this.skillRepository.findAll(specSkill)).thenReturn(Collections.singletonList(skill2));
        Mockito.when(this.teammateService.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.singletonList(teammate));
        ResourceDtoOnRequest result = this.service.getResourceDTOEntity(resource, specSkill, (Specification)specTeammate);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Должен возвращать ResourceDtoOnRequest когда фильров нет")
    void getResourceDTOEntityWhenBothSpecificationsAreNull() {
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setTeammateId(1L);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        skill1.setSkill("Java");
        skill1.setDescription("Java programming language");
        Skill skill2 = new Skill();
        skill2.setId(2L);
        skill2.setSkill("Spring");
        skill2.setDescription("Spring framework");
        Teammate teammate = new Teammate();
        teammate.setId(1L);
        teammate.setJobTitle("Software Engineer");
        teammate.setRank("Senior");
        teammate.setSkills(new HashSet(Arrays.asList(skill1, skill2)));
        Mockito.when(this.teammateService.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.singletonList(teammate));
        ResourceDtoOnRequest result = this.service.getResourceDTOEntity(resource, (Specification)null, (Specification)null);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(teammate.getJobTitle(), result.getJobTitle());
        Assertions.assertEquals(teammate.getRank(), result.getRank());
        Assertions.assertEquals(teammate.getSkills().stream().map(Skill::getSkill).collect(Collectors.toSet()), result.getSkills());
    }

    @Test
    @DisplayName("Должен возвращать ResourceDtoOnRequest только тогда, когда есть фильтрация по specSkill ")
    void getResourceDTOEntityWhenOnlySpecSkillIsNotNull() {
        Resource resource = new Resource();
        resource.setTeammateId(1L);
        Specification<Skill> specSkill = (Specification)Mockito.mock(Specification.class);
        Specification<Teammate> specTeammate = null;
        Teammate teammate = new Teammate();
        teammate.setJobTitle("Software Engineer");
        teammate.setRank("Senior");
        Set<Skill> skills = new HashSet();
        Skill skill1 = new Skill();
        skill1.setSkill("Java");
        Skill skill2 = new Skill();
        skill2.setSkill("Spring");
        skills.add(skill1);
        skills.add(skill2);
        teammate.setSkills(skills);
        Mockito.when(this.teammateService.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.singletonList(teammate));
        Mockito.when(this.skillRepository.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.singletonList(skill1));
        ResourceDtoOnRequest result = this.service.getResourceDTOEntity(resource, specSkill, (Specification)specTeammate);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Software Engineer", result.getJobTitle());
        Assertions.assertEquals("Senior", result.getRank());
        Assertions.assertEquals(2, result.getSkills().size());
        Assertions.assertTrue(result.getSkills().contains("Java"));
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если ни один ресурс не соответствует заданным спецификациям")
    void searchWithNoMatchingSpecifications() {
        Specification<Resource> specResources = (Specification)Mockito.mock(Specification.class);
        Specification<Skill> specSkill = (Specification)Mockito.mock(Specification.class);
        Specification<Teammate> specTeammate = (Specification)Mockito.mock(Specification.class);
        Pageable pageable = PageRequest.of(0, 10);
        List<Resource> resources = new ArrayList();

        Mockito.when(this.resourceRepository.findAll(specResources, pageable)).thenReturn(new PageImpl<>(resources));

        List<ResourceDtoOnRequest> result = this.service.search(specResources, specSkill, specTeammate, pageable);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Должен возвращать список ресурсов, которые соответствуют заданными ограничениями ресурса")
    void searchWithOnlyResourceSpecificationMatching() {
        Specification<Resource> specResources = (Specification)Mockito.mock(Specification.class);
        Specification<Skill> specSkill = null;
        Specification<Teammate> specTeammate = null;
        Resource resource1 = new Resource();
        resource1.setId(1L);
        resource1.setDescription("Resource 1");
        resource1.setLocationWorked("Location 1");
        resource1.setFromFree(new Date());
        resource1.setEndFree(new Date());
        resource1.setTeammateId(1L);
        Resource resource2 = new Resource();
        resource2.setId(1L);
        resource2.setDescription("Resource 2");
        resource2.setLocationWorked("Location 2");
        resource2.setFromFree(new Date());
        resource2.setEndFree(new Date());
        resource2.setTeammateId(1L);
        Teammate teammate = new Teammate();
        teammate.setId(1L);
        teammate.setFirstname("John");
        teammate.setLastname("Doe");
        teammate.setJobTitle("Developer");
        teammate.setRank("Junior");
        teammate.setTeamId(1L);
        teammate.setOwnerId(1L);
        teammate.setSkills(new HashSet());
        List<Resource> resources = Arrays.asList(resource1, resource2);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(this.resourceRepository.findAll(specResources, pageable)).thenReturn(new PageImpl<>(resources));
        Mockito.when(this.teammateService.findAll((Specification)Mockito.any(Specification.class))).thenReturn(Collections.singletonList(teammate));

        List<ResourceDtoOnRequest> result = this.service.search(specResources, (Specification)specSkill, (Specification)specTeammate,pageable);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Resource 1", ((ResourceDtoOnRequest)result.get(0)).getDescription());
        Assertions.assertEquals("Resource 2", ((ResourceDtoOnRequest)result.get(1)).getDescription());
    }

    @Test
    @DisplayName("Список ресурсов")
    void findAllResources_ReturnValidList() {
        LinkedHashSet<Skill> skills = new LinkedHashSet();
        skills.add(new Skill(2L, "Java", "Java docs"));
        skills.add(new Skill(1L, "Spring", "Spring"));
        Teammate teammate = new Teammate("Ivan", "Ivanov", "Junior JAVA dev", "Junior", 1L, 1L, skills);
        Resource resource1 = new Resource("Java developer", "From Home", new Date(2020, 10, 10), new Date(2022, 10, 10), 1L);
        resource1.setId(1L);
        resource1.setStatus(Status.ACTIVE);
        List<Resource> resources = List.of(resource1);
        List<ResourceDtoOnRequest> resourcesDtoOnRequest = List.of(new ResourceDtoOnRequest(1L, "Junior JAVA dev", "Junior", "Java developer", "From Home", new Date(2020, 10, 10), new Date(2022, 10, 10), Set.of("Java", "Spring"), (LocalDateTime)null, (LocalDateTime)null, Status.ACTIVE));
        Mockito.when(this.resourceRepository.findAll()).thenReturn(resources);
        Mockito.when(this.teammateService.findById(1L)).thenReturn(Optional.of(teammate));
        List<ResourceDtoOnRequest> allResources = this.service.findAll();
        Assertions.assertNotNull(allResources);
        Assertions.assertEquals(resourcesDtoOnRequest, allResources);
    }
}
