package com.example.FlipCommerce.service;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.dto.RequestDto.ProductRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.ProductResponseDto;
import com.example.FlipCommerce.exception.SellerNotFoundException;
import com.example.FlipCommerce.model.Product;
import com.example.FlipCommerce.model.Seller;
import com.example.FlipCommerce.repository.ProductRepository;
import com.example.FlipCommerce.repository.SellerRepository;
import com.example.FlipCommerce.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmailId(productRequestDto.getSellerEmailId());
        if(seller==null){
            throw new SellerNotFoundException("EmailId is not registered");
        }
        // dto to entity
        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        seller.getProducts().add(product);
        product.setSeller(seller);


        // save product
        Seller savedSeller = sellerRepository.save(seller); // save both product and seller
        //Product savedProduct = savedSeller.getProducts().get(savedSeller.getProducts().size()-1);

        // prepare response dto
        return ProductTransformer.ProducToProductResponseDto(product);
    }

    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price){

        List<Product> products = productRepository.findByCategoryAndPrice(category,price);

        // prepare a list of dtos
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product: products){
            productResponseDtos.add(ProductTransformer.ProducToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    public List<ProductResponseDto> productofcategory(Category category) throws Exception {
        List<Product>products=productRepository.findAll();
        List<ProductResponseDto>ans=new ArrayList<>();
        for(Product p:products){
            if(p.getCategory()==category){
                ProductResponseDto productResponseDto=ProductTransformer.ProducToProductResponseDto(p);
                ans.add(productResponseDto);
            }
        }
        if(ans.size()==0)
             throw new Exception("No Product Found");
        return ans;
    }

    public List<ProductResponseDto> productgrtthanorequalcost(int price) throws Exception {
        List<Product>products=productRepository.findAll();
        List<ProductResponseDto>ans=new ArrayList<>();
        for(Product p:products){
            if(p.getPrice()>=price){
                ProductResponseDto productResponseDto=ProductTransformer.ProducToProductResponseDto(p);
                ans.add(productResponseDto);
            }
        }
        if(ans.size()==0)
            throw new Exception("No Product Found");
        return ans;
    }
    <K,V extends Comparable<V>> Map<K,V>sortbyvalue(Map<K,V>map){
        Comparator<K> valueComparator=new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) {
                int comp=map.get(o1).compareTo(map.get(o2));
                if(comp<0)
                    return 1;
                return -1;
            }
        };
        Map<K, V> sorted = new TreeMap<K,V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }
    public List<ProductResponseDto> cheapestproduct(int x) throws Exception {
        List<Product>products=productRepository.findcheapest(x);
        if(products.size()==0)
             throw new Exception("No product found");
        List<ProductResponseDto>ans=new ArrayList<>();
        Map<Integer, Integer> set=new TreeMap<>();
        for(Product o:products){
            set.put(o.getId(),o.getPrice());
        }
        Map<Integer,Integer> sortedmap=sortbyvalue(set);
   for(Map.Entry<Integer,Integer>e:sortedmap.entrySet()){
            if(ans.size()==x){
                break;
            }
            Optional<Product> product=productRepository.findById(e.getKey());
            Product o=product.get();
            ProductResponseDto productResponseDto= ProductTransformer.ProducToProductResponseDto(o);
            ans.add(productResponseDto);
        }
        return ans;
    }

    public List<ProductResponseDto>  cheapestproductbycategory(Category category,int x) throws Exception {
        String cat=String.valueOf(category);
        List<Product>products=productRepository.cheapestproductbycategory(cat,x);

        if(products.size()==0)
             throw new Exception("No product found");
        List<ProductResponseDto>ans=new ArrayList<>();
        for(Product o:products){
            ProductResponseDto productResponseDto= ProductTransformer.ProducToProductResponseDto(o);
            ans.add(productResponseDto);
        }
        return ans;
    }
}
