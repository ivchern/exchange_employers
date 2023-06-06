package com.ivchern.exchange_employers.Controllers.CardControllers;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Services.Card.ResourceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


