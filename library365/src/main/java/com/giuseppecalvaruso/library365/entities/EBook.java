package com.giuseppecalvaruso.library365.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ebook")
public class EBook extends Book {

    @Column(name = "file_url",nullable = false)
    private String fileUrl;

    @Column(name="license_Type",nullable = false)
    private String licenseType;


    public EBook(String title, String ISBN, String description, int publication_year, String fileUrl, String licenseType) {
        super(title, ISBN, description, publication_year);
        this.fileUrl = fileUrl;
        this.licenseType = licenseType;
    }

    public EBook() {

    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    @Override
    public String toString() {
        return "EBook{" +
                "fileUrl='" + fileUrl + '\'' +
                ", licenseType='" + licenseType + '\'' +
                '}';
    }
}
