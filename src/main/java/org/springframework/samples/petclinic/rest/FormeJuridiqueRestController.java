package org.springframework.samples.petclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.FormeJuridique;
import org.springframework.samples.petclinic.service.FormeJuridiqueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/juridiques")
public class FormeJuridiqueRestController {
    @Autowired
    private FormeJuridiqueService formeJuridiqueService;

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<FormeJuridique>> getFormeJuridiques() {
        Collection<FormeJuridique> formeJuridiques = this.formeJuridiqueService.findAllFormeJuridiques();
        if (formeJuridiques.isEmpty()) {
            return new ResponseEntity<Collection<FormeJuridique>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<FormeJuridique>>(formeJuridiques, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{formeJuridiqueId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FormeJuridique> getFormeJuridique(@PathVariable("formeJuridiqueId") int formeJuridiqueId) {
        FormeJuridique formeJuridique = formeJuridiqueService.findFormeJuridiqueById(formeJuridiqueId);
        if (formeJuridique == null) {
            return new ResponseEntity<FormeJuridique>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<FormeJuridique>(formeJuridique, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FormeJuridique> addFormeJuridique(@RequestBody @Valid FormeJuridique formeJuridique, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (formeJuridique == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<FormeJuridique>(headers, HttpStatus.BAD_REQUEST);
        }
        this.formeJuridiqueService.saveFormeJuridique(formeJuridique);
        headers.setLocation(ucBuilder.path("/api/juridiques/{id}").buildAndExpand(formeJuridique.getId()).toUri());
        return new ResponseEntity<FormeJuridique>(formeJuridique, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{formeJuridiqueId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FormeJuridique> updateFormeJuridique(@PathVariable("formeJuridiqueId") int formeJuridiqueId, @RequestBody @Valid FormeJuridique formeJuridique, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (formeJuridique == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<FormeJuridique>(headers, HttpStatus.BAD_REQUEST);
        }
        FormeJuridique currentFormeJuridique = this.formeJuridiqueService.findFormeJuridiqueById(formeJuridiqueId);
        if (currentFormeJuridique == null) {
            return new ResponseEntity<FormeJuridique>(HttpStatus.NOT_FOUND);
        }
        currentFormeJuridique.mergeFormeJuridique(formeJuridique);
        this.formeJuridiqueService.saveFormeJuridique(currentFormeJuridique);
        return new ResponseEntity<FormeJuridique>(currentFormeJuridique, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @RequestMapping(value = "/{formeJuridiqueId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deletePet(@PathVariable("formeJuridiqueId") int formeJuridiqueId) {
        FormeJuridique formeJuridique = this.formeJuridiqueService.findFormeJuridiqueById(formeJuridiqueId);
        if (formeJuridique == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.formeJuridiqueService.deleteFormeJuridique(formeJuridique);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
