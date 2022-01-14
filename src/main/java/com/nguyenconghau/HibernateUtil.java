package com.nguyenconghau;

import java.util.Properties;

import com.nguyenconghau.pojo.Category;
import com.nguyenconghau.pojo.Manufacturer;
import com.nguyenconghau.pojo.Product;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private final static SessionFactory FACTORY;
    static {
        Configuration conf = new Configuration();
        Properties pros = new Properties();
        pros.put(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");
        pros.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        pros.put(Environment.URL, "jdbc:sqlserver://localhost:1433; databaseName= hibernate");
        pros.put(Environment.USER, "sa");
        pros.put(Environment.PASS, "1");
        // pros.put(Environment.SHOW_SQL, true);
        conf.setProperties(pros);
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Product.class);
        conf.addAnnotatedClass(Manufacturer.class);
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);

    }

    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }

}
