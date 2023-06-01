package com.example.FlipCommerce.controller;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.dto.RequestDto.ProductRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.ProductResponseDto;
import com.example.FlipCommerce.exception.SellerNotFoundException;
import com.example.FlipCommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto){

        try{
            ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);
            return new ResponseEntity(productResponseDto, HttpStatus.CREATED);
        }
        catch (SellerNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/category/{category}/price/{price}")
    public ResponseEntity getAllProductsByCategoryAndPrice(@PathVariable("category") Category category,
                                                           @PathVariable("price") int price){

        List<ProductResponseDto> productResponseDtos = productService.getAllProductsByCategoryAndPrice(category,price);
        return new ResponseEntity(productResponseDtos,HttpStatus.FOUND);
    }

    // get all the products of a category
   @GetMapping("/productofcategory")
    public ResponseEntity productofcategory(@RequestParam Category category){
       try{
           List<ProductResponseDto> productResponseDto = productService.productofcategory(category);
           return new ResponseEntity(productResponseDto, HttpStatus.FOUND);
       }
       catch (Exception e) {
           return new ResponseEntity(e.getMessage(), HttpStatus.CREATED);
       }
   }
    // get all the products in a category who have price greater than 500
    @GetMapping("/productgrtthanorequalcost")
    public ResponseEntity productgrtcost(@RequestParam int price){
        try{
            List<ProductResponseDto> productResponseDto = productService.productgrtthanorequalcost(price);
            return new ResponseEntity(productResponseDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CREATED);
        }
    }
    // get the top 5 cheapest products in a category
    @GetMapping("/cheapestproduct")
    public ResponseEntity cheapestproduct(@RequestParam int x){
        try{
            List<ProductResponseDto> productResponseDto = productService.cheapestproduct(x);
            return new ResponseEntity(productResponseDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CREATED);
        }
    }
    // get top 5 costliest products in a category
    @GetMapping("/cheapestproductbycategory")
    public ResponseEntity cheapestproductbycategory(@RequestParam Category category,@RequestParam int x){
        try{
            List<ProductResponseDto> productResponseDto = productService.cheapestproductbycategory(category,x);
            return new ResponseEntity(productResponseDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CREATED);
        }
    }

}
