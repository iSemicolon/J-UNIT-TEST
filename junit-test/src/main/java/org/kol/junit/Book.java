package org.kol.junit;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Book_Record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @NonNull
    private  String name;

    @NonNull
    private String summary;

    private int rating;
}
