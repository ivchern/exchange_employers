package com.ivchern.exchange_employers.Controllers.CardControllers;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Services.Card.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/resources", produces = "application/json")
@CrossOrigin("*")
@SecurityRequirement(name = "JWT")
@Tag(name = "Resources", description = "Ресурсы")
public class ResourcesController {
    private final ResourceService resourceService;
    public ResourcesController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Transactional
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(parameters = {@Parameter(name = "description", in = ParameterIn.QUERY,
                    schema = @Schema(type = "string"), example = "Mobile Deve", description  = "Поиск описания по маске"),
            @Parameter(name =  "jobTitle", description  = "Поиск заголовка по маске", example = "IOS Dev",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "locationWorked", description  = "Поиск локации", example = "From Home",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "fromFree", description  = "Дата, когда ресурс свободен. Раньше, чем",
                    example = "2024-09-01", schema = @Schema(type = "string")),
            @Parameter(name =  "endFree", description  = "Дата окончания. Позже, чем", example = "2025-02-01",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "skill", description  = "Поиск по навывкам. Включение", schema = @Schema(type = "array")),
            @Parameter(name =  "rank", description  = "Уровень", schema = @Schema(type = "array"))})
    public List<ResourceDtoOnRequest> searchResources(
            @RequestParam(defaultValue= "0", required = false) Integer page,
            @RequestParam(defaultValue= "10", required = false) Integer pageSize,
            @Parameter(hidden = true) @And({
                    @Spec(path = "description", params = "description", spec = Like.class),
                    @Spec(path = "locationWorked", params = "locationWorked", spec = Equal.class),
                    @Spec(path = "fromFree", params = "fromFree", spec = LessThanOrEqual.class),
                    @Spec(path = "endFree", params = "endFree", spec = GreaterThanOrEqual.class)
            }
            ) Specification<Resource> specResources,
            @Parameter(hidden = true) @And({
                    @Spec(path = "skill", params = "skill", spec = In.class)}
            ) Specification<Skill> specSkill,
                    @Parameter(hidden = true) @And({
                            @Spec(path = "jobTitle", params = "jobTitle", spec = Like.class),
                            @Spec(path = "rank", params = "rank", spec = In.class)}
            ) Specification<Teammate> specTeammate) {
                Pageable paging = PageRequest.of(page, pageSize);
                return resourceService.search(specResources, specSkill, specTeammate, paging);
    }
    //TODO: ADD recent page
    @GetMapping()
    public List<ResourceDtoOnRequest> getResources(){
        return resourceService.findAll();
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceDtoOnRequest setResources(@RequestBody ResourceDtoOnCreate resource){
        return resourceService.save(resource);
    }
    @GetMapping(path= "/{id}")
    public ResponseEntity<ResourceDtoOnRequest> getResourceById(@PathVariable("id") Long id){
        Optional<ResourceDtoOnRequest> optResource = resourceService.findById(id);
        if (optResource.isPresent()){
            return new ResponseEntity<ResourceDtoOnRequest>(optResource.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/{id}", consumes = "application/json")
    public ResourceDtoOnRequest putResource(@PathVariable("id") Long id, @RequestBody ResourceDtoOnRequest resource) {
        return resourceService.update(resource, id);
    }

    @DeleteMapping(path= "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource( @PathVariable("id") Long id) {
         resourceService.delete(id);
    }
}


