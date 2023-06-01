package com.example.FlipCommerce.service;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.dto.RequestDto.SellerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.ProductResponseDto;
import com.example.FlipCommerce.dto.ResponseDto.SellerResponseDto;
import com.example.FlipCommerce.exception.SellerNotFoundException;
import com.example.FlipCommerce.model.Product;
import com.example.FlipCommerce.model.Seller;
import com.example.FlipCommerce.repository.ProductRepository;
import com.example.FlipCommerce.repository.SellerRepository;
import com.example.FlipCommerce.transformer.ProductTransformer;
import com.example.FlipCommerce.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
@Autowired
    ProductRepository productRepository;
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto){
        // dto -> entity
        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);
        Seller savedSeller = sellerRepository.save(seller);
        // prepare response Dto
        return SellerTransformer.SellerToSellerResponseDto(savedSeller);
    }

    public List<String> sellerofparticularcategory(Category category) throws Exception {
//         List<Product>products=productRepository.findByCategory(category);
//         System.out.println(products);
        List<Seller>sellers=sellerRepository.findAll();
        List<String>ans=new ArrayList<>();
        for(Seller s:sellers){
            for(Product it:s.getProducts()){
                if(it.getCategory()==category) {
                    ans.add(s.getName());
                }
            }
        }
        if(ans.size()==0)
            throw new Exception("No product found of specific category");
         return ans;
    }

    public List<ProductResponseDto> productbycategorysoldbyseller(Category category,String email) throws Exception {
        Seller seller = sellerRepository.findByEmailId(email);
        if(seller==null) {
            throw new SellerNotFoundException("EmailId is not registered");
        }
        System.out.println(seller.getName());
        List<ProductResponseDto>products=new ArrayList<>();
        for(Product p:seller.getProducts()){
             if(p.getCategory()==category){
                 ProductResponseDto productResponseDto= ProductTransformer.ProducToProductResponseDto(p);
                 products.add(productResponseDto);
             }
         }
         return products;
    }

    public List<SellerResponseDto> sellerhighestproducts() throws Exception {
        List<Seller>sellers=sellerRepository.findAll();
        int max=0;
        List<SellerResponseDto>sellerResponseDtos=new ArrayList<>();
        for(Seller s:sellers){
            max=Math.max(s.getProducts().size(),max);
        }
        for(Seller s:sellers){
            if(s.getProducts().size()==max){
                sellerResponseDtos.add(SellerTransformer.SellerToSellerResponseDto(s));
            }
        }
        if(sellerResponseDtos.size()==0){
            throw new Exception("No seller found");
        }
        return sellerResponseDtos;
    }

    public List<SellerResponseDto> sellerminproducts() throws Exception {
        List<Seller>sellers=sellerRepository.findAll();
        int min=(int)1e9;
        List<SellerResponseDto>sellerResponseDtos=new ArrayList<>();
        for(Seller s:sellers){
            min=Math.min(s.getProducts().size(),min);
        }
        for(Seller s:sellers){
            if(s.getProducts().size()==min){
                sellerResponseDtos.add(SellerTransformer.SellerToSellerResponseDto(s));
            }
        }
        if(sellerResponseDtos.size()==0){
            throw new Exception("No seller found");
        }
        return sellerResponseDtos;
    }

    public List<SellerResponseDto> sellercostliestproducts() throws Exception {
        List<Seller>sellers=sellerRepository.findAll();
        int max=0;
        List<SellerResponseDto>sellerResponseDtos=new ArrayList<>();
        for(Seller s:sellers){
            for(Product p:s.getProducts()){
                max=Math.max(p.getPrice(),max);
            }
        }
        for(Seller s:sellers){
            for(Product p:s.getProducts()){
               if(p.getPrice()==max){
                   sellerResponseDtos.add(SellerTransformer.SellerToSellerResponseDto(s));
               }
            }
        }
        if(sellerResponseDtos.size()==0){
            throw new Exception("No seller found");
        }
        return sellerResponseDtos;
    }

    public HashSet<SellerResponseDto> sellercheapestproducts() throws Exception {
        List<Seller>sellers=sellerRepository.findAll();
        int min=(int)1e9;
        HashSet<SellerResponseDto> sellerResponseDtos=new HashSet<>();
        for(Seller s:sellers){
            for(Product p:s.getProducts()){
                min=Math.min(p.getPrice(),min);
            }
        }
        for(Seller s:sellers){
            for(Product p:s.getProducts()){
                if(p.getPrice()==min){
                    sellerResponseDtos.add(SellerTransformer.SellerToSellerResponseDto(s));
                }
            }
        }
        if(sellerResponseDtos.size()==0){
            throw new Exception("No seller found");
        }
        return sellerResponseDtos;
    }
}
