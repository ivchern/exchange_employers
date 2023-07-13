package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.Model.Card.Rank;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Repositories.RequestWorkerRepository;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RequestWorkerServiceImpl")
class RequestWorkerServiceImplTest {

    @Mock
    private RequestWorkerRepository requestWorkerRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private RequestWorkerServiceImpl requestWorkerService;

    @Test
    @DisplayName("Должен возвращать пустой список, если ни один RequestWorker не соответствует спецификации")
    void findAllWithSpecificationAndNoMatch() {
        Specification<RequestWorker> specRequest = mock(Specification.class);
        Pageable paging = PageRequest.of(0, 10);

        when(requestWorkerRepository.findAll(specRequest, paging)).thenReturn(new PageImpl<>(Collections.emptyList()));

        Iterable<RequestWorker> result = requestWorkerService.findAll(specRequest, paging);

        assertThat(result).isEmpty();
        verify(requestWorkerRepository, times(1)).findAll(specRequest, paging);
    }

    @Test
    @DisplayName("Должны возвращаться все RequestWorkers, соответствующие спецификации и не выходящие за пределы страницы")
    void findAllWithSpecificationAndPaging() {
        // Create a sample specification
        Specification<RequestWorker> specRequest = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), Status.ACTIVE);

        // Create a sample pageable object
        Pageable pageable = PageRequest.of(0, 10);

        // Create a list of RequestWorkers to be returned by the repository
        List<RequestWorker> requestWorkers = new ArrayList<>();
        requestWorkers.add(new RequestWorker("Job Title 1", "Project 1", Rank.Junior, "Description 1", "Location 1", new Date(), true, new HashSet<>()));
        requestWorkers.add(new RequestWorker("Job Title 2", "Project 2", Rank.Senior, "Description 2", "Location 2", new Date(), false, new HashSet<>()));
        requestWorkers.add(new RequestWorker("Job Title 3", "Project 3", Rank.Middle, "Description 3", "Location 3", new Date(), true, new HashSet<>()));

        // Mock the repository method to return the list of RequestWorkers
        when(requestWorkerRepository.findAll(specRequest, pageable)).thenReturn(new PageImpl<RequestWorker>(requestWorkers));

        // Call the method under test
        Iterable<RequestWorker> result = requestWorkerService.findAll(specRequest, pageable);

        // Verify the repository method was called with the correct arguments
        verify(requestWorkerRepository, times(1)).findAll(specRequest, pageable);

        // Verify the result matches the expected list of RequestWorkers
        assertThat(result).containsExactlyElementsOf(requestWorkers);
    }

}