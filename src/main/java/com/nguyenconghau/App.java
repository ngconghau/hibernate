package com.nguyenconghau;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.nguyenconghau.pojo.Category;
import com.nguyenconghau.pojo.Manufacturer;
import com.nguyenconghau.pojo.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Hello world!
 *
 */
public class App {
    private static final SessionFactory FACTORY = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        // Session session = HibernateUtil.getSessionFactory().openSession();
        // Product product = new Product();
        // product.setName("iphone");
        // product.setDescription("iphone 13");
        // product.setPrice(new BigDecimal(302));
        // Category category = session.get(Category.class, 1);
        // product.setCategory(category);
        // Manufacturer manufacturer1 = session.get(Manufacturer.class, 1);
        // Manufacturer manufacturer2 = session.get(Manufacturer.class, 2);
        // Set<Manufacturer> m = new HashSet<>();
        // m.add(manufacturer1);
        // m.add(manufacturer2);
        // product.setManufacturer(m);
        // save(product);
        // featchAllProduct();
        // stats();
        // group();

        Category category2 = findCategoryByID(3);
        delete(category2);

    }

    public static Category findCategoryByID(int id) {
        // open session
        Session session = FACTORY.openSession();

        // retrive presitent class object
        Category category = session.get(Category.class, id);

        // close session
        session.close();

        return category;
    }

    public static void update(Category category) {
        // open session
        Session session = FACTORY.openSession();
        // begin transaction
        session.beginTransaction();
        // use the session to update the category
        session.update(category);
        // commit transaction
        session.getTransaction().commit();
        // close session
        session.close();
    }

    public static void delete(Category category) {
        // open session
        Session session = FACTORY.openSession();
        // begin transaction
        session.beginTransaction();
        // use the session to update the category
        session.delete(category);
        // commit transaction
        session.getTransaction().commit();
        // close session
        session.close();
    }

    public static void group() {
        // open session
        Session session = FACTORY.openSession();

        // create criteria builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        // Create criteria query
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        // create root
        Root<Product> pRoot = criteriaQuery.from(Product.class);
        Root<Category> cRoot = criteriaQuery.from(Category.class);
        criteriaQuery.where(criteriaBuilder.equal(pRoot.get("category"), cRoot.get("id")));

        criteriaQuery.multiselect(cRoot.get("name").as(String.class), criteriaBuilder.count(pRoot.get("id")));
        criteriaQuery.groupBy(cRoot.get("name").as(String.class));
        criteriaQuery.orderBy(criteriaBuilder.asc(cRoot.get("name").as(String.class)));

        Query<Object[]> query = session.createQuery(criteriaQuery);
        List<Object[]> result = query.getResultList();
        for (Object[] object : result) {
            System.out.println(object[0] + " " + object[1]);
        }
    }

    public static void stats() {
        // open session
        Session session = FACTORY.openSession();

        // create criteria builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        // Create criteria query
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        // create root
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(criteriaBuilder.count(root.get("id")),
                criteriaBuilder.max(root.get("price").as(BigDecimal.class)),
                criteriaBuilder.avg(root.get("price").as(BigDecimal.class)));

        Query<Object[]> query = session.createQuery(criteriaQuery);
        Object[] result = query.getSingleResult();
        for (Object object : result) {
            System.out.println(object);
        }

    }

    public static void featchAllProduct() {

        // open session
        Session session = FACTORY.openSession();

        // create criteria builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        // Create criteria query
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        // create root
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);

        // filter data
        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%iphone%");
        Predicate p2 = criteriaBuilder.like(root.get("description").as(String.class), "%nokia%");
        criteriaQuery.where(criteriaBuilder.or(p1, p2));

        // create query
        Query<Product> query = session.createQuery(criteriaQuery);

        // execute query
        List<Product> listProducts = query.getResultList();
        for (Product product : listProducts) {
            System.out.println(product.getName() + " " + product.getPrice());
        }
    }

    public static void save(Product product) {
        // Open Session
        Session session = HibernateUtil.getSessionFactory().openSession();
        /*
         * mệnh đề from lấy tất cả đối tượng vd "FROM Category". mệnh đề select lấy vài
         * thuộc tính đối tượng vd "Select a.name from category a". mệnh đề where chỉ
         * định điều kiện lọc dữ liệu vd "from category a where a.name=: na" sử dụng
         * setParemeter() để truyền vào giá trị vào cho tham số na.
         * 
         */
        // Query<Category> query = session.createQuery("FROM Category a where
        // a.name=:namee", Category.class);
        // query.setParameter("namee", "điện thoại");
        // List<Category> cats = query.list();

        // for (Category category : cats) {
        // System.out.println(category.getId() + " " + category.getName());
        // }

        // Set<Product> products = category.getProduct();// lấy tất cả các product từ
        // category chỉ định
        // for (Product product : products) {
        // System.out.println(product.getName());
        // }

        // begin a trasaction
        Transaction transaction = session.beginTransaction();

        // use the session to save the category
        session.save(product);

        // commit trasaction
        transaction.commit();

        // close session

        session.close();
    }
}
