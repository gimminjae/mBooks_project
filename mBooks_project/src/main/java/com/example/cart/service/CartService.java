package com.example.cart.service;

import com.example.cart.entity.CartItem;
import com.example.cart.repository.CartItemRepository;
import com.example.member.enitty.Member;
import com.example.mybook.entity.MyBook;
import com.example.mybook.service.MyBookService;
import com.example.product.entity.Product;
import com.example.product.dto.ProductDto;
import com.example.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final MyBookService myBookService;

    //장바구니에 도서 추가
    public String addItem(Member buyer, Product product) {
        CartItem oldCartItem = cartItemRepository.findByBuyerIdAndProductId(buyer.getId(), product.getId()).orElse(null);

        List<ProductDto> productDtos = productService.getByMember(buyer.toDto());

        //내가 등록한 도서일 경우
        for(ProductDto productDto : productDtos) {
            if(productDto.getId() == product.getId()) {
                return "my product";
            }
        }
        List<MyBook> myBookList = myBookService.getAllByBuyerId(buyer.getId());

        //내가 구매한 도서일 경우
        for(MyBook myBook : myBookList) {
            if(product.getId() == myBook.getProduct().getId()) {
                return "my book";
            }
        }

        //이미 장바구니에 있을 경우
        if (oldCartItem != null) {
            return "already exist";
        }
        CartItem cartItem = CartItem.builder()
                .createDate(LocalDateTime.now())
                .buyer(buyer)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return "addItem";
    }

    //장바구니에서 도서 제거
    @Transactional
    public boolean removeItem(Member buyer, Product product) {
        CartItem oldCartItem = cartItemRepository.findByBuyerIdAndProductId(buyer.getId(), product.getId()).orElse(null);

        if(oldCartItem != null) {
            cartItemRepository.delete(oldCartItem);
            return true;
        }
        return false;
    }
    public boolean hasItem(Member buyer, Product product) {
        return cartItemRepository.existsByBuyerIdAndProductId(buyer.getId(), product.getId());
    }

    //구매자로 장바구니 목록 가져오기
    public List<CartItem> getItemsByBuyer(Member buyer) {
        return cartItemRepository.findAllByBuyerId(buyer.getId());
    }
    public void removeItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    public void removeItem(Member buyer, long productId) {
        Product product = Product.builder().id(productId).build();
        removeItem(buyer, product);
    }
}
