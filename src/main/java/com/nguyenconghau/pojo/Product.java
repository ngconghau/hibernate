package com.nguyenconghau.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String name;
    private String description;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    /*
     * mặc định là eager có nghĩa là khi truy vấn vào bản product sẽ lấy luôn thông
     * tin của category lên luôn, không như lazy không lấy category lên luôn cho đến
     * khi chúng ta truy cập đến category của product nó mới lấy
     */
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "prod_manufacturer", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = {
            @JoinColumn(name = "manufacturer_id") })
    private Set<Manufacturer> manufacturer;

    public Set<Manufacturer> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Set<Manufacturer> manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Product() {
    }

    public Product(int id, String name, String description, BigDecimal price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
