package com.learn.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class StockDTO {
    private Integer stockId;
    private String namaBarang;
    private Integer jumlahStockBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
    private MultipartFile gambarBarang;
}
