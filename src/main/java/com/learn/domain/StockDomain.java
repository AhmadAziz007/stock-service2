package com.learn.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_stock", schema = "public")
public class StockDomain {
    @Id
    @SequenceGenerator(name = "mst_stock_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mst_stock_seq")
    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "nama_barang")
    private String namaBarang;

    @Column(name = "jumlah_stock_barang")
    private Integer jumlahStockBarang;

    @Column(name = "no_seri_barang")
    private String nomorSeriBarang;

    @Column(name = "additional_info",columnDefinition = "text")
    private String additionalInfo;

    @Column(name = "gambar_barang",columnDefinition = "text")
    private String gambarBarang;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
