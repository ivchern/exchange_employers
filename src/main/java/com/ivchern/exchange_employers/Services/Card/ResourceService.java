package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ResourceService {
    ResourceDtoOnRequest save(ResourceDtoOnCreate resource);
    ResourceDtoOnRequest update(ResourceDtoOnRequest resourceDTO, Long Id);
    List<ResourceDtoOnRequest> search(Specification<Resource> specResources,
                                      Specification<Skill> specSkill,
                                      Specification<Teammate> specTeammate,
                                      Pageable page);
    List<ResourceDtoOnRequest> findAll();
    Optional<ResourceDtoOnRequest> findById(Long Id);
    void delete(Long id);


}
