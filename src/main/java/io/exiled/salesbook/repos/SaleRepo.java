package io.exiled.salesbook.repos;

import io.exiled.salesbook.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * JPA interface to persist objects in Database
 *
 * @author Exiled Rain
 * @version 1.0
 * */
public interface SaleRepo extends JpaRepository<Sale, Long> {
    /**
     * Method to find one record from Database
     *
     * @return Sale from Database by @param name
     * */
    List<Sale> findByName(String name);
}
