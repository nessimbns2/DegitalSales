package in.pfe.elearning.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Facture {
    private String id;
    private String date;
    private String userId;
    private String clientId;
    private List<String> products;
    private double total;
}
