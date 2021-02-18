package io.exiled.salesbook.service;

import io.exiled.salesbook.model.Sale;

import java.util.List;

/**
 * Service to export data from database to PDF
 *
 * @author Exiled Rain
 * @version 1.0
 * */
public interface ExportService {
    /**
     * Method that creates pdf file
     * */
    void manipulatePdf()throws Exception;

    /**
     * Method to get all records from the Database.
     * @return records from database as List of Sale
     * */
    List<Sale> getRecords();
}
