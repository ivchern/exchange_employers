package com.ivchern.exchange_employers.Controllers.CardControllers;

import com.ivchern.exchange_employers.DTO.CardDTO.RequestWorkerDtoOnCreate;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Services.Card.RequestWorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/request", produces = "application/json")
@CrossOrigin("*")
@SecurityRequirement(name = "JWT")
@Tag(name = "Request resources", description = "Запрос ресурсов")
public class RequestWorkerController {
    private RequestWorkerService requestWorkerService;

    public RequestWorkerController(RequestWorkerService requestWorkerService) {
        this.requestWorkerService = requestWorkerService;
    }


    @Transactional
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(parameters = {
            @Parameter(name =  "jobTitle", description  = "Поиск заголовка по маске", example = "IOS Dev",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "projectName", description  = "Имя проекта", example = "Зеленый банк",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "rank", description  = "Уровень", schema = @Schema(type = "array")),
            @Parameter(name = "description", in = ParameterIn.QUERY,
                    schema = @Schema(type = "string"), example = "Mobile Deve", description  = "Поиск описания по маске"),
            @Parameter(name =  "locationWorked", description  = "Поиск локации", example = "From Home",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "isInterviewNeeded", description  = "Необходимо интервью", example = "false",
                    schema = @Schema(type = "string")),
            @Parameter(name =  "needBefore", description  = "Нужен после. Позже, чем",
                    example = "2024-09-01", schema = @Schema(type = "string")),
            @Parameter(name =  "skill", description  = "Поиск по навывкам. Включение", schema = @Schema(type = "array"))})
    public Iterable<RequestWorker> searchRequests(
            @RequestParam(defaultValue= "0", required = false) Integer page,
            @RequestParam(defaultValue= "10", required = false) Integer pageSize,
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
        Pageable paging = PageRequest.of(page, pageSize);
        return requestWorkerService.findAll(SpecRequest, paging);
    }

    @GetMapping()
    public Iterable<RequestWorker> getRequests() {
        return requestWorkerService.findAll();
    }

    @GetMapping(path= "/{id}")
    public ResponseEntity<RequestWorker> geRequestById(@PathVariable("id") Long id){
        Optional<RequestWorker> optRequest = requestWorkerService.findById(id);
        if (optRequest.isPresent()){
            return new ResponseEntity<RequestWorker>(optRequest.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestWorker postRequest(@RequestBody RequestWorkerDtoOnCreate request) {
        return requestWorkerService.save(request);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public RequestWorker putRequest(@PathVariable("id") Long id,
                                    @RequestBody @NotNull RequestWorker request) {
        return requestWorkerService.update(request);
    }

    @DeleteMapping(path = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequest(@PathVariable("id") Long id) {
         requestWorkerService.delete(id);
    }
}
