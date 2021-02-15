package io.exiled.salesbook.service;

import io.exiled.salesbook.model.Sale;

import java.util.List;

/**
 * Service to export data from database to PDF
 *
 * @author Exiled Rai
 * @version 1.0
 * */
public interface ExportService {
    void manipulatePdf()throws Exception;

    List<Sale> getRecords();
}
