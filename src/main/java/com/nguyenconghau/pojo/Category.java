package com.nguyenconghau.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category") // xác định lớp này là đại điện cho bảng catagory dưới csdl
public class Category implements Serializable {

    @Id // xác đinh thuộc tính này là khóa chính dưới csdl
    @GeneratedValue(strategy = GenerationType.IDENTITY) // xác đinh chiến lược cách thức để biết generate ra khóa chính
    @Column(name = "id") // xác định cái tên trường dưới csdl mapping
    private int id; // tên trường trên lớp của mình

    @Column(name = "name")
    private String name;
    /*
     * 1.mappedBy chính là đối tượng của lớp Product khi chúng ta khai báo 2.fetch
     * trong OnetoMany mặc định để giá trị LAZY để tránh tình trạng lấy product bị
     * trùng lặp thông tin
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Product> product;

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
    }

    public Category() {
    }

    public Category(CategoryBuilder builder) {
        this.name = builder.name;
    }

    public Category(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }

    /*
     * Create builder pattern to use initalize objectF
     */
    public static class CategoryBuilder {
        private String name;

        public CategoryBuilder(String name) {
            this.name = name;
        }

        public Category build() {
            return new Category(this);
        }
    }

}
