package com.ivchern.exchange_employers.Controllers.CardControllers;

import com.ivchern.exchange_employers.DTO.CardDTO.RequestWorkerDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Services.Card.RequestWorkerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
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
