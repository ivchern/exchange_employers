package com.ivchern.exchange_employers.Controllers.CardControllers;

import com.ivchern.exchange_employers.Common.ExceptionResponse;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnSave;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Services.Card.RequestWorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/request", produces = "application/json")
@CrossOrigin("*")
@SecurityRequirement(name = "JWT")
@Tag(name = "Request resources", description = "Запрос ресурсов")
public class RequestWorkerController {
    private final RequestWorkerService requestWorkerService;

    public RequestWorkerController(RequestWorkerService requestWorkerService) {
        this.requestWorkerService = requestWorkerService;
    }
    @GetMapping(path = "/search")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success!"),
                   @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired")})
    @Operation( description = "Поиск ресуросв",
            parameters = {
            @Parameter(name =  "jobTitle", description  = "Поиск заголовка по маске", example = "IOS Dev",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "projectName", description  = "Имя проекта", example = "Зеленый банк",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "rank", description  = "Уровень", schema = @Schema(type = "array")),
            @Parameter(name = "description", in = ParameterIn.QUERY,
                    schema = @Schema(type = "string"), example = "Mobile Deve", description  = "Поиск описания по маске"),
            @Parameter(name =  "locationWorked", description  = "Поиск локации", example = "From Home",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "isInterviewNeeded", description  = "Необходимоcть интервью", example = "false",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "needBefore", description  = "Нужен после. Позже, чем",
                    example = "2024-09-01", schema = @Schema(type = "string")),
            @Parameter(name =  "skill", description  = "Поиск по навывкам. Включение", schema = @Schema(type = "array"))})
    public List<RequestWorkerDtoOnRequest> searchRequests(
            @RequestParam(defaultValue= "0", required = false) Integer page,
            @RequestParam(defaultValue= "10", required = false) Integer pageSize,
            @RequestParam(defaultValue= "id", required = false) String sortBy,
            @Join(path= "skills", alias = "r")
            @Join(path = "r.skill", alias = "s")
            @Parameter(hidden = true) @And({
                    @Spec(path = "jobTitle", params = "jobTitle", spec = Like.class),
                    @Spec(path = "projectName", params = "projectName", spec = Like.class),
                    @Spec(path = "rank", params = "rank", spec = In.class),
                    @Spec(path = "description", params = "description", spec = Like.class),
                    @Spec(path = "locationWorked", params = "locationWorked", spec = Equal.class),
                    @Spec(path = "needBefore", params = "needBefore", spec = GreaterThanOrEqual.class),
                    @Spec(path = "isInterviewNeeded", params = "isInterviewNeeded", spec = Equal.class),
                    @Spec(path = "r.skill", params = "skill", spec = In.class)
            })
            Specification<RequestWorker> SpecRequest){
        Pageable paging = PageRequest.of(page, pageSize,  Sort.by(sortBy));
        return requestWorkerService.findAll(SpecRequest, paging);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success!"),
                  @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                          content = { @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ExceptionResponse.class))})})
    @Operation(description = "Получение всех доступных ресурсов")
    public List<RequestWorkerDtoOnRequest> getRequests(
            @RequestParam(defaultValue= "0", required = false) Integer page,
            @RequestParam(defaultValue= "10", required = false) Integer pageSize,
            @RequestParam(defaultValue= "id", required = false) String sortBy){
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return  requestWorkerService.findAll(paging);
    }

    @GetMapping(path= "/{id}")
    @Operation(description = "Получение запроса ресурса по идентификатору")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success!"),
                   @ApiResponse(responseCode = "404", description = "Resource request card not found",
                           content = { @Content(mediaType = "application/json",
                                   schema = @Schema(implementation = ExceptionResponse.class))}),
                   @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                           content = { @Content(mediaType = "application/json",
                                   schema = @Schema(implementation = ExceptionResponse.class))})})
    public RequestWorkerDtoOnRequest getRequestById(@PathVariable("id") Long id){
        return requestWorkerService.findById(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    @Operation(description = "Создание запроса ресурса")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses( value = {@ApiResponse(responseCode = "200", description = "Success!"),
                   @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                           content = { @Content(mediaType = "application/json",
                           schema = @Schema(implementation = ExceptionResponse.class))}),
                   @ApiResponse(responseCode = "409", description = "Error save data",
                           content = { @Content(mediaType = "application/json",
                           schema = @Schema(implementation = ExceptionResponse.class))})})
    public RequestWorker postRequest(@RequestBody RequestWorkerDtoOnSave request) {
        return requestWorkerService.save(request);
    }
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    @Operation(description = "Обновление данных карточки запроса ресурса")
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
    public RequestWorker putRequest(@PathVariable("id") Long id,
                                    @RequestBody RequestWorkerDtoOnSave request) {
        return requestWorkerService.update(id, request);
    }
    @DeleteMapping(path = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
    public ResponseEntity<Void>  deleteRequest(@PathVariable("id") Long id, Principal principal) {
         requestWorkerService.delete(id, principal);
         return ResponseEntity.noContent().build();
    }
}
