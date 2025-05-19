package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}