package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.model.Product;
import com.example.FlipCommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {

    Seller findByEmailId(String emailId);
@Query(value="select * from seller where category=:category and seller=:seller",nativeQuery = true)
    List<Product> findBySellerAndCategory(Category category,Seller seller);
}
