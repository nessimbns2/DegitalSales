
package in.pfe.elearning.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pfe.elearning.entity.Facture;
import in.pfe.elearning.repository.FactureRepository;
import in.pfe.elearning.repository.ProductRepository;


@Service
public class FactureService {

    @Autowired
    private FactureRepository productRepository;

    public List<Facture> getAllFactures() {
        return productRepository.findAll();
    }

    public Facture getFactureById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Facture addFacture(Facture product) {
        return productRepository.save(product);
    }

    public Facture updateFacture(Facture product) {
        return productRepository.save(product);
    }

    public void deleteFacture(String id) {
        productRepository.deleteById(id);
    }

    public List<Facture> getFacturesByIdUser(String userId) {
        return productRepository.findByUserId(userId);
    }

    

}
