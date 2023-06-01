package com.example.FlipCommerce.controller;

import com.example.FlipCommerce.dto.RequestDto.OrderRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.OrderResponseDto;
import com.example.FlipCommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity placeOrder(@RequestBody OrderRequestDto orderRequestDto){

        try{
            OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequestDto);
            return new ResponseEntity(orderResponseDto,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get top 5 orders with highest order value
        @GetMapping("/fivecostliestorder")
        public ResponseEntity fivecostliestorder(@RequestParam int x){

            try{
                List<OrderResponseDto> orderResponseDto = orderService.fivecostliestorder(x);
                return new ResponseEntity(orderResponseDto,HttpStatus.CREATED);
            }
            catch (Exception e){
                return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    // all the orders of a particular customer
    @GetMapping("/getordersofcustomer")
    public ResponseEntity getordersofcustomer(@RequestParam String email){

        try{
            List<OrderResponseDto> orderResponseDto = orderService.getordersofcustomer(email);
            return new ResponseEntity(orderResponseDto,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
   // top 5 recently ordered orders of a customer
   @GetMapping("/recentorder")
   public ResponseEntity recentorder(@RequestParam String email,@RequestParam int rec){

       try{
           List<OrderResponseDto> orderResponseDto = orderService.recentorder(email,rec);
           return new ResponseEntity(orderResponseDto,HttpStatus.CREATED);
       }
       catch (Exception e){
           return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
   }
}
