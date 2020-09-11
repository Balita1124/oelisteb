package org.springframework.samples.petclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import sun.misc.Request;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Client>> getClients() {
        Collection<Client> clients = this.clientService.findAllClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<Collection<Client>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Client>>(clients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Client> getClient(@PathVariable("clientId") int clientId) {
        Client client = clientService.findClientById(clientId);
        if (client == null) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Client> addClient(@RequestBody @Valid Client client, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (client == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Client>(headers, HttpStatus.BAD_REQUEST);
        }
        this.clientService.saveClient(client);
        headers.setLocation(ucBuilder.path("/api/clients/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<Client>(client, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Client> updateClient(@PathVariable("clientId") int clientId, @RequestBody @Valid Client client, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (client == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Client>(headers, HttpStatus.BAD_REQUEST);
        }
        Client currentClient = this.clientService.findClientById(clientId);
        if (currentClient == null) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
        currentClient.mergeClient(client);
        this.clientService.saveClient(currentClient);
        return new ResponseEntity<Client>(currentClient, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deletePet(@PathVariable("clientId") int clientId) {
        Client client = this.clientService.findClientById(clientId);
        if (client == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clientService.deleteClient(client);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
