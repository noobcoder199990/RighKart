package com.example.FlipCommerce.controller;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.dto.RequestDto.SellerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.ProductResponseDto;
import com.example.FlipCommerce.dto.ResponseDto.SellerResponseDto;
import com.example.FlipCommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto){

        SellerResponseDto sellerResponseDto = sellerService.addSeller(sellerRequestDto);
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }

    // get all the sellers who sell products of a particular category
       @GetMapping("/sellerofparticularcategory")
       public ResponseEntity sellerofparticularcategory(@RequestParam Category category) throws Exception {
      try{
          List<String> seller = sellerService.sellerofparticularcategory(category);
          return new ResponseEntity(seller, HttpStatus.CREATED);
      }
       catch(Exception e){
           return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
       }
       }

    // get all the products sold by a seller in a category
    @GetMapping("/productbyccategorysoldbyseller")
    public ResponseEntity productbycategorysoldbyseller(@RequestParam Category category,@RequestParam String email) throws Exception {
        try{
            List<ProductResponseDto> sellers = sellerService.productbycategorysoldbyseller(category,email);
            return new ResponseEntity(sellers, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    // seller with highest number of products
    @GetMapping("/sellerhighestproducts")
    public ResponseEntity sellerhighestproducts(){
        try{
            List<SellerResponseDto>seller=sellerService.sellerhighestproducts();
            return new ResponseEntity(seller,HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    // seller with minimum number of products
    @GetMapping("/sellerminproducts")
    public ResponseEntity sellerminproducts(){
        try{
            List<SellerResponseDto>seller=sellerService.sellerminproducts();
            return new ResponseEntity(seller,HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    // seller(s) selling the costliest product
    @GetMapping("/sellercostliestproducts")
    public ResponseEntity sellercostliestproducts(){
        try{
            List<SellerResponseDto>seller=sellerService.sellercostliestproducts();
            return new ResponseEntity(seller,HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    // seller(s) selling the cheapest product
    @GetMapping("/sellercheapestproducts")
    public ResponseEntity sellercheapestproducts(){
        try{
            HashSet<SellerResponseDto> seller=sellerService.sellercheapestproducts();
            return new ResponseEntity(seller,HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
