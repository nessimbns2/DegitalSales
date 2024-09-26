package in.pfe.elearning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.pfe.elearning.entity.Facture;
import in.pfe.elearning.services.FactureService;

import java.util.List;

@RestController
@RequestMapping("/factures")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        List<Facture> factures = factureService.getAllFactures();
        return new ResponseEntity<>(factures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable String id) {
        Facture facture = factureService.getFactureById(id);
        if (facture != null) {
            return new ResponseEntity<>(facture, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Facture> addFacture(@RequestBody Facture facture) {
        Facture newFacture = factureService.addFacture(facture);
        return new ResponseEntity<>(newFacture, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable String id, @RequestBody Facture facture) {
        facture.setId(id); // Assuming Facture has a setId method
        Facture updatedFacture = factureService.updateFacture(facture);
        return new ResponseEntity<>(updatedFacture, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable String id) {
        factureService.deleteFacture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Facture>> getFacturesByIdUser(@PathVariable String userId) {
        List<Facture> factures = factureService.getFacturesByIdUser(userId);
        return new ResponseEntity<>(factures, HttpStatus.OK);
    }
}