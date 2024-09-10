package com.learn.service;

import com.learn.domain.StockDomain;
import com.learn.dto.AdditionalInfoDTO;
import com.learn.dto.StockDTO;
import com.learn.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    public ResponseEntity<?> createStock(StockDTO stockDTO, AdditionalInfoDTO additionalInfoDTO) {
        try {
            StockDomain stock = new StockDomain();
            stock.setNamaBarang(stockDTO.getNamaBarang());
            stock.setJumlahStockBarang(stockDTO.getJumlahStockBarang());
            stock.setNomorSeriBarang(stockDTO.getNomorSeriBarang());

            // Perbaikan pada setAdditionalInfo
            stock.setAdditionalInfo("Jenis Barang: " + additionalInfoDTO.getKodeJenisBarang() + ", Warna: " + additionalInfoDTO.getKodeWarna());

            // Menangani kemungkinan kesalahan saat menyimpan gambar
            try {
                stock.setGambarBarang(saveImageAsBase64(stockDTO.getGambarBarang()));
            } catch (RuntimeException e) {
                // Log error dan kirim pesan kesalahan
                throw new RuntimeException("Error while saving image: " + e.getMessage(), e);
            }

            stock.setCreatedAt(new Date());
            stock.setCreatedBy("Admin");

            // Simpan ke database
            StockDomain savedStock = stockRepository.save(stock);

            // Membungkus response dalam ResponseEntity
            return ResponseEntity.ok(savedStock);
        } catch (Exception e) {
            // Tangkap kesalahan umum dan kirim pesan kesalahan
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating stock: " + e.getMessage());
        }
    }

    public ResponseEntity<List<StockDomain>> listStock() {
        List<StockDomain> stocks = stockRepository.findAll();
        return ResponseEntity.ok(stocks);
    }

    public ResponseEntity<?> getStockDetail(Integer stockId) {
        try {
            // Ambil stock berdasarkan stockId
            Optional<StockDomain> stock = stockRepository.findByStockId(stockId);

            // Jika stock tidak ditemukan, kembalikan response NOT_FOUND dengan pesan
            if (stock.isEmpty()) {
                return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
            }

            // Jika stock ditemukan, kembalikan response OK beserta data stock
            return new ResponseEntity<>(stock.get(), HttpStatus.OK);
        } catch (Exception e) {
            // Jika terjadi kesalahan, kembalikan response INTERNAL_SERVER_ERROR
            return new ResponseEntity<>("Error retrieving stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateStock(Integer stockId, StockDTO stockDTO, AdditionalInfoDTO additionalInfoDTO) {
        Optional<StockDomain> stockOpt = stockRepository.findById(stockId);
        if (stockOpt.isPresent()) {
            StockDomain stock = stockOpt.get();
            stock.setNamaBarang(stockDTO.getNamaBarang());
            stock.setJumlahStockBarang(stockDTO.getJumlahStockBarang());
            stock.setNomorSeriBarang(stockDTO.getNomorSeriBarang());
            stock.setAdditionalInfo("Jenis Barang: " + additionalInfoDTO.getKodeJenisBarang() + ", Warna: " + additionalInfoDTO.getKodeWarna());

            if (stockDTO.getGambarBarang() != null) {
                try {
                    stock.setGambarBarang(saveImageAsBase64(stockDTO.getGambarBarang()));
                } catch (RuntimeException e) {
                    throw new RuntimeException("Error while saving image: " + e.getMessage());
                }
            }

            stock.setUpdatedAt(new Date());
            stock.setUpdatedBy("Admin");

            stockRepository.save(stock);
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
        }
    }

    public ResponseEntity<?> deleteStock(Integer stockId) {
        try {
            // Ambil stock berdasarkan stockId
            Optional<StockDomain> stock = stockRepository.findByStockId(stockId);

            // Jika stock tidak ditemukan, kembalikan response NOT_FOUND dengan pesan
            if (stock.isEmpty()) {
                return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
            }

            // Jika stock ditemukan, kembalikan response OK beserta data stock
            stockRepository.delete(stock.get());
            return new ResponseEntity<>("Stock delete successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Jika terjadi kesalahan, kembalikan response INTERNAL_SERVER_ERROR
            return new ResponseEntity<>("Error retrieving stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveImageAsBase64(MultipartFile file) {
        // Validasi MIME type untuk hanya menerima JPG/PNG
        if (file != null && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))) {
            try {
                // Konversi gambar ke format Base64 String
                return Base64.getEncoder().encodeToString(file.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Failed to save image", e);
            }
        } else {
            throw new RuntimeException("Invalid image format");
        }
    }

}

