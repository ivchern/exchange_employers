package com.ivchern.exchange_employers.Controllers.CardControllers;

import com.ivchern.exchange_employers.Common.ExceptionResponse;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnSave;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Services.Card.Resource.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(parameters = {@Parameter(name = "description", in = ParameterIn.QUERY,
                    schema = @Schema(type = "string"), example = "React Developer", description  = "Поиск описания по маске"),
            @Parameter(name =  "cardTitle", description  = "Поиск заголовка по маске", example = "React",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "locationWorked", description  = "Поиск локации", example = "From Home",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "fromFree", description  = "Дата, когда ресурс свободен. Раньше, чем",
                    example = "2023-09-12", schema = @Schema(type = "string")),
            @Parameter(name =  "endFree", description  = "Дата окончания. Позже, чем", example = "2023-09-31",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "skill", description  = "Поиск по навывкам. Включение", schema = @Schema(type = "array")),
            @Parameter(name =  "rank", description  = "Уровень", schema = @Schema(type = "array"))})
    public Page<ResourceDtoOnRequest> getAllResources(
            @RequestParam(defaultValue= "0", required = false) Integer page,
            @RequestParam(defaultValue= "10", required = false) Integer pageSize,
            @RequestParam(defaultValue= "id", required = false) String sortBy,
            @Parameter(hidden = true)
            @Join(path = "teammate", alias = "t")
            @Join(path = "t.skills", alias = "r")
            @Join(path = "r.skill", alias = "s")
            @And({
                    @Spec(path = "description", params = "description", spec = Like.class),
                    @Spec(path = "locationWorked", params = "locationWorked", spec = Equal.class),
                    @Spec(path = "fromFree", params = "fromFree", spec = LessThanOrEqual.class),
                    @Spec(path = "endFree", params = "endFree", spec = GreaterThanOrEqual.class),
                    @Spec(path = "r.skill", params = "skill", spec = In.class),
                    @Spec(path = "cardTitle", params = "cardTitle", spec = Like.class),
                    @Spec(path = "t.rank", params = "rank", spec = In.class)}
            ) Specification<Resource> resourceSpecification) {
                Pageable paging = PageRequest.of(page, pageSize,  Sort.by(sortBy));
                return resourceService.findAll(resourceSpecification, paging);
    }

    @PostMapping
    @DeleteMapping(path = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Создание карточки запроса ресурсов")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Success!"),
            @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Not enough permissions to use this endpoint",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource request card not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    public Resource save(@RequestBody ResourceDtoOnSave resource){
        return resourceService.save(resource);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    @Operation(description = "Обновление данных карточки ресурса")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success!"),
            @ApiResponse(responseCode = "404", description = "Resource request card not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Not enough permissions to use this endpoint",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})})
    public Resource putResource(@PathVariable("id") Long id, @RequestBody ResourceDtoOnSave resource, Principal principal) {
        return resourceService.update(resource, id, principal);
    }

    @DeleteMapping(path = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Удаление карточки запроса ресурса")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Success!"),
            @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Not enough permissions to use this endpoint",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource request card not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})})
    public ResponseEntity<Void> deleteResource(@PathVariable("id") Long id, Principal principal) {
        resourceService.delete(id, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получение карточкой запроса ресурса по id")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Success!"),
            @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource request card not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})})
    public ResourceDtoOnRequest getResource(@PathVariable("id") Long id) {
        return resourceService.findById(id);
    }
}


