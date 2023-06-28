package org.kol.junit.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Product_Record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NonNull
    private  String name;

    @NonNull
    private String summary;

    private int rating;
}
