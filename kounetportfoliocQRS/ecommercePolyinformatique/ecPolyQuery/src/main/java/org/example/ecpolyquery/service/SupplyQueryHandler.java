package org.example.ecpolyquery.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.Supply;
import org.example.ecpolyquery.query.GetAllProductsQuery;
import org.example.ecpolyquery.query.GetAllSupplyQuery;
import org.example.ecpolyquery.repos.SupplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SupplyQueryHandler {

  private SupplyRepository supplyRepository;


  @QueryHandler
  public Page<Supply> handle(GetAllSupplyQuery query) {
    log.debug("Handling GetAllProductsQuery with pagination: page={}, size={}",
      query.getPage(), query.getSize());


    Pageable pageable = PageRequest.of(query.getPage(),
      query.getSize());
    return supplyRepository.findAll( pageable);
  }
}
