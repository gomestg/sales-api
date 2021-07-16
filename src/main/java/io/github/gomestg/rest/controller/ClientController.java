package io.github.gomestg.rest.controller;

import io.github.gomestg.domain.entity.Client;
import io.github.gomestg.domain.repository.ClientRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Api("API Clients")
public class ClientController {

    private ClientRepository repository;

    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    @ApiOperation("Find details of the client by id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Client FOUND"),
        @ApiResponse(code = 404, message = "Customer id entered was not found")
    })
    public Client findClientById(@PathVariable @ApiParam("Client Id") Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a new Client")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Customer saved successfully"),
            @ApiResponse(code = 400, message = "Validation Error ")
    })
    public Client save(@RequestBody @Valid Client client) {
        return repository.save(client);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete client by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Client deleted successfully"),
            @ApiResponse(code = 404, message = "Customer id entered was not found")
    })
    public void delete(@PathVariable Integer id) {
        repository.findById(id)
                .map(client -> {
                    repository.delete(client);
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update client by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Client updated successfully"),
            @ApiResponse(code = 404, message = "Customer id entered was not found")
    })
    public void update(@PathVariable Integer id, @RequestBody Client client) {
        repository
                .findById(id)
                .map(c -> {
                    client.setId(c.getId());
                    repository.save(client);
                    return c;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @GetMapping()
    @ApiOperation("Find all clients")
    @ApiResponse(code = 200, message = "Find All Clients ")
    public List<Client> find(Client filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return repository.findAll(example);
    }

}
