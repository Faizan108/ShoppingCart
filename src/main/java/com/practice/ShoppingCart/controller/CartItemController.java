package com.practice.ShoppingCart.controller;


import com.practice.ShoppingCart.exception.ResourceNotFoundException;
import com.practice.ShoppingCart.responses.ApiResponse;
import com.practice.ShoppingCart.service.Cart.ICartItemService;
import com.practice.ShoppingCart.service.Cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
        try{
            if(cartId==null){
                cartId = cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item added successfully",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        try{
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Removed Item Successfully",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
        public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                @PathVariable Long itemId,
                @RequestParam Integer quantity){
            try{
                cartItemService.updateItemQuantity(cartId,itemId,quantity);
                return ResponseEntity.ok(new ApiResponse("Updated Successfully",null));
            }catch (ResourceNotFoundException e){
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
            }

    }
}
