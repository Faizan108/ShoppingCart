package com.practice.ShoppingCart.service.Cart;

import com.practice.ShoppingCart.repository.CartRepository;
import com.practice.ShoppingCart.service.Product.IProductService;
import com.practice.ShoppingCart.exception.ResourceNotFoundException;
import com.practice.ShoppingCart.model.Cart;
import com.practice.ShoppingCart.model.CartItem;
import com.practice.ShoppingCart.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        Product product  = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+ quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

        Cart cart  = cartService.getCart(cartId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId)).findFirst()
                .orElseThrow(()->new ResourceNotFoundException("Product not found!"));
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

        Cart cart  = cartService.getCart(cartId);
         cart.getItems().stream()
                .filter(item-> item.getProduct().getId().equals(productId)).findFirst()
                 .ifPresent(item->{
                     item.setQuantity(quantity);
                     item.setUnitPrice(item.getProduct().getPrice());
                     item.setTotalPrice();
                 });
         cart.updateTotalAmount();
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }
}
