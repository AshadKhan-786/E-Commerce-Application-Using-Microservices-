package in.ashokit.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import in.ashokit.dto.CartDto;
import in.ashokit.entity.Cart;
import in.ashokit.mapper.CartMapper;
import in.ashokit.repo.CartRepo;

public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepo cartRepo;
	
	@Override
	public CartDto addToCart(CartDto cartDto) {
		Cart cartEntity = CartMapper.convertToEntity(cartDto);
		Cart cart = cartRepo.save(cartEntity);
		return CartMapper.convertToDto(cart);
	}

	@Override
	public CartDto updateCartQuantityById(CartDto cartDto) {
		Optional<Cart> cartById = cartRepo.findById(cartDto.getCartId());
		
		if(cartById.isPresent()) {
			Cart existedCart = cartById.get();
			existedCart.setProductId(cartDto.getProductId());
			existedCart.setQuantity(cartDto.getQuantity());
			Cart updatedCart = cartRepo.save(existedCart);
			
			return CartMapper.convertToDto(updatedCart);
		}else {
			throw new NoSuchElementException("Cart not found for ID: " + cartDto.getCartId());
		}
	}

	@Override
	public CartDto getCartByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartDto deleteCartById(Integer cartId) {
		// TODO Auto-generated method stub
		return null;
	}

}
