package org.kol.junit.repository;

import org.kol.junit.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository   extends JpaRepository<Product, Long> {
}
