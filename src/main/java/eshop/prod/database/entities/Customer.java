package eshop.prod.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CREATE TABLE customer (
//     id_customer SERIAL PRIMARY KEY,
//     name VARCHAR(255) NOT NULL,
//     email VARCHAR(255) NOT NULL,
//     address VARCHAR(255) NOT NULL
// );

@Entity
@Builder
@Data
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_customer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    /**
     * Update only the fields that are not null
     * @param original
     * @param updated
     * @return
     */
    public Customer updateOnllyNecesary(Customer original, Customer updated) {

        if (updated.getName() != null) {
            original.setName(updated.getName());
        }
        if (updated.getEmail() != null) {
            original.setEmail(updated.getEmail()); 
        }
        if (updated.getAddress() != null) {
            original.setAddress(updated.getAddress()); 
        }

        return original;
    }
    
}
