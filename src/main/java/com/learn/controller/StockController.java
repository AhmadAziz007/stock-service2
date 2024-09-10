package com.learn.controller;

import com.learn.dto.AdditionalInfoDTO;
import com.learn.dto.StockDTO;
import com.learn.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createStock(@ModelAttribute StockDTO stockDTO, @ModelAttribute AdditionalInfoDTO additionalInfoDTO) {
        // Panggil service untuk membuat stock
        return stockService.createStock(stockDTO, additionalInfoDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listStock() {
        return stockService.listStock();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getStockDetail(@PathVariable Integer id) {
        return stockService.getStockDetail(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Integer id, @ModelAttribute StockDTO stockDTO, @ModelAttribute AdditionalInfoDTO additionalInfoDTO) {
        return stockService.updateStock(id, stockDTO, additionalInfoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable Integer id) {
        return stockService.deleteStock(id);
    }

}



