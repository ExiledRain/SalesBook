package io.exiled.salesbook.repos;

import io.exiled.salesbook.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepo extends JpaRepository<Sale, Long> {
    List<Sale> findByName(String name);
}
