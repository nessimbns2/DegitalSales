
package in.pfe.elearning.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pfe.elearning.entity.Facture;
import in.pfe.elearning.entity.FactureItem;
import in.pfe.elearning.entity.Product;
import in.pfe.elearning.repository.FactureRepository;
import in.pfe.elearning.repository.ProductRepository;



@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Facture getFactureById(String id) {
        return factureRepository.findById(id).orElse(null);
    }

    public Facture addFacture(Facture facture) {
        if (facture.isBuy() == false) {
        List<FactureItem> products = facture.getProducts();
        for (FactureItem product : products) {
            Product existingProduct = productRepository.findById(product.getProduct().getId()).orElse(null);
            if (existingProduct != null) {
                int newQuantity = existingProduct.getQuantity() - product.getQuantity();
                if (newQuantity < 0) {
                    throw new IllegalArgumentException("Quantity cannot be negative");
                } 
                existingProduct.setQuantity(newQuantity);
                productRepository.save(existingProduct);
            }
        }
        } else {
            List<FactureItem> products = facture.getProducts();
            for (FactureItem product : products) {
                Product existingProduct = productRepository.findById(product.getProduct().getId()).orElse(null);
                if (existingProduct != null) {
                    existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
                    productRepository.save(existingProduct);
                }
            }
        }
        return factureRepository.save(facture);
    }

    public Facture updateFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteFacture(String id) {
        factureRepository.deleteById(id);
    }

    public List<Facture> getFacturesByIdUser(String userId) {
        return factureRepository.findByUserId(userId);
    }

    

}
