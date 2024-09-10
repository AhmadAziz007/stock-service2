package com.learn.repository;

import com.learn.domain.StockDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockDomain, Integer> {
    @Query(value = "SELECT * FROM mst_stock WHERE stock_id = :stockId", nativeQuery = true)
    Optional<StockDomain> findByStockId(@Param("stockId") Integer stockId);

    @Query(value = "SELECT * FROM mst_stock WHERE no_seri_barang = :nomorSeriBarang", nativeQuery = true)
    StockDomain findByNoSeriBarang(@Param("nomorSeriBarang") String nomorSeriBarang);

    @Query(value = "SELECT DISTINCT a.* FROM mst_stock as a ORDER BY a.stock_id ASC", nativeQuery = true)
    List<StockDomain> findAll();


}
