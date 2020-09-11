package org.springframework.samples.petclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Pays;
import org.springframework.samples.petclinic.service.PaysService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/payss")
public class PaysRestController {

    @Autowired
    private PaysService paysService;

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Pays>> getPayss() {
        Collection<Pays> payss = this.paysService.findAllPayss();
        if (payss.isEmpty()) {
            return new ResponseEntity<Collection<Pays>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Pays>>(payss, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{paysId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Pays> getPays(@PathVariable("paysId") int paysId) {
        Pays pays = paysService.findPaysById(paysId);
        if (pays == null) {
            return new ResponseEntity<Pays>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pays>(pays, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Pays> addPays(@RequestBody @Valid Pays pays, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (pays == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Pays>(headers, HttpStatus.BAD_REQUEST);
        }
        this.paysService.savePays(pays);
        headers.setLocation(ucBuilder.path("/api/payss/{id}").buildAndExpand(pays.getId()).toUri());
        return new ResponseEntity<Pays>(pays, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{paysId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Pays> updatePays(@PathVariable("paysId") int paysId, @RequestBody @Valid Pays pays, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (pays == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Pays>(headers, HttpStatus.BAD_REQUEST);
        }
        Pays currentPays = this.paysService.findPaysById(paysId);
        if (currentPays == null) {
            return new ResponseEntity<Pays>(HttpStatus.NOT_FOUND);
        }
        currentPays.mergePays(pays);
        this.paysService.savePays(currentPays);
        return new ResponseEntity<Pays>(currentPays, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{paysId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deletePet(@PathVariable("paysId") int paysId) {
        Pays pays = this.paysService.findPaysById(paysId);
        if (pays == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.paysService.deletePays(pays);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
