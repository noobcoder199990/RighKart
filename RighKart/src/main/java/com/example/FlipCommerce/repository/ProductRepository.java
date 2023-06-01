package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryAndPrice(Category category, int price);

    List<Product> findByCategory(Category category);
@Query(value = "select * from product order by price limit :x",nativeQuery = true)
    List<Product> findcheapest(int x);
@Query(value = "select * from product where category=:category order by price asc limit :x",nativeQuery = true)
    List<Product> cheapestproductbycategory(String category, int x);
}
