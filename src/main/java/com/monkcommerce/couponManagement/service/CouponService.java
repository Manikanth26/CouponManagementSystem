package com.monkcommerce.couponManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkcommerce.couponManagement.DTO.*;
import com.monkcommerce.couponManagement.DTO.cartDTO.ApplicableCouponDto;
import com.monkcommerce.couponManagement.DTO.cartDTO.ApplyCouponResponse;
import com.monkcommerce.couponManagement.DTO.cartDTO.CartDto;
import com.monkcommerce.couponManagement.component.CouponDiscountCalculator;
import com.monkcommerce.couponManagement.entity.*;
import com.monkcommerce.couponManagement.exception.CouponAlreadyExistsException;
import com.monkcommerce.couponManagement.exception.CouponNotFoundException;
import com.monkcommerce.couponManagement.exception.InvalidCouponException;
import com.monkcommerce.couponManagement.repository.CouponRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CouponService {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private CouponDiscountCalculator discountCalculator;
	
	private final CouponRepository couponRepo;
	
	public CouponService(CouponRepository couponRepo, CouponDiscountCalculator discountCalculator) {
		this.couponRepo = couponRepo;
		this.discountCalculator = discountCalculator;
	}
	
	public Coupon createCoupon(CouponRequest request) {
		
		if (request == null) {
            throw new IllegalArgumentException("CouponRequest cannot be null");
        }
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Coupon code is required");
        }

        if (couponRepo.existsByCode(request.getCode())) {
            throw new CouponAlreadyExistsException("Coupon with code already exists: " + request.getCode());
        }
        
        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setType(request.getType());
        coupon.setValidFrom(request.getValidFrom());
        coupon.setValidTo(request.getValidTo());
        coupon.setActive(true);

        mapAndSetDetails(coupon, request);
        
        return couponRepo.save(coupon);
	}
	
	public Iterable<Coupon> getAllCoupons() {
        return couponRepo.findAll();
    }
	
	public Optional<Coupon> getCoupon(Long id) {
        return couponRepo.findById(id);
    }

    public Optional<Coupon> updateCoupon(Long id, CouponRequest request) {
    	return couponRepo.findById(id).map(existing -> {
            if (request.getCode() != null && !request.getCode().equals(existing.getCode())) {
                if (couponRepo.existsByCode(request.getCode())) {
                    throw new CouponAlreadyExistsException("Coupon with code already exists: " + request.getCode());
                }
                existing.setCode(request.getCode());
            }
            if (request.getType() != null) {
                existing.setType(request.getType());
            }
            existing.setValidFrom(request.getValidFrom());
            existing.setValidTo(request.getValidTo());
            
            mapAndSetDetails(existing, request);

            return couponRepo.save(existing);
        });
    }
    
    public void deleteCoupon(Long id) {
    	if (!couponRepo.existsById(id)) {
            throw new CouponNotFoundException("Coupon not found with id: " + id);
        }
        couponRepo.deleteById(id);
    }
    
    public ApplyCouponResponse applyCoupon(String code, CartDto cart) throws Exception {
    	
    	if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Coupon code is required");
        }
        if (cart == null || cart.getItems() == null) {
            throw new IllegalArgumentException("Cart and cart items must be provided");
        }
    	
    	Coupon coupon = couponRepo.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Coupon code not found: " + code));


    	if (!coupon.isActive() ||
                (coupon.getValidFrom() != null && coupon.getValidFrom().isAfter(LocalDateTime.now())) ||
                (coupon.getValidTo() != null && coupon.getValidTo().isBefore(LocalDateTime.now()))) {
            throw new InvalidCouponException("Coupon is not valid or expired: " + code);
        }

        double discount = discountCalculator.calculateDiscount(coupon, cart);
        double originalTotal = cart.getCartTotal();
        double shippingFee = cart.getShippingFee();

        ApplyCouponResponse res = new ApplyCouponResponse();
        res.setCouponCode(coupon.getCode());
        res.setOriginalTotal(originalTotal);
        res.setShippingFee(shippingFee);
        res.setDiscount(discount);
        res.setFinalTotal(originalTotal + shippingFee - discount);

        return res;
    }
    public List<ApplicableCouponDto> getApplicableCoupons(CartDto cart) {
    	
    	if (cart == null || cart.getItems() == null) {
            throw new IllegalArgumentException("Cart and items are required");
        }
    	
        List<Coupon> coupons = couponRepo.findAll();

        return coupons.stream()
                .filter(this::isValidNow)
                .filter(Coupon::isActive)
                .map(coupon -> {
                    try {
                        double discount = discountCalculator.calculateDiscount(coupon, cart);

                        ApplicableCouponDto dto = new ApplicableCouponDto();
                        dto.setCouponId(coupon.getId());
                        dto.setCode(coupon.getCode());
                        dto.setType(coupon.getType());
                        dto.setDiscount(discount);
                        dto.setFinalPrice(cart.getCartTotal() + cart.getShippingFee() - discount);
                        return dto;
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(dto -> dto != null && dto.getDiscount() > 0)
                .collect(Collectors.toList());
    }

    private boolean isValidNow(Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();
        return (coupon.getValidFrom() == null || !coupon.getValidFrom().isAfter(now))
                && (coupon.getValidTo() == null || !coupon.getValidTo().isBefore(now));
    }
    
    private void mapAndSetDetails(Coupon coupon, CouponRequest request) {
        
        if (request.getDetails() == null) {
            coupon.setDetails(null);
            return;
        }

        switch (request.getType()) {
            case CART_WISE -> {
                CartWiseDetailsDto dto = objectMapper.convertValue(request.getDetails(), CartWiseDetailsDto.class);
                CartWiseDetails details = new CartWiseDetails();
                details.setMinCartValue(dto.getMinCartValue());
                details.setDiscountPercent(dto.getDiscountPercent());
                details.setFlatDiscount(dto.getFlatDiscount());
                details.setMaxDiscountCap(dto.getMaxDiscountCap());
                details.setMinItemsRequired(dto.getMinItemsRequired());
                coupon.setDetails(details);
            }
            case PRODUCT_WISE -> {
                ProductWiseDetailsDto dto = objectMapper.convertValue(request.getDetails(), ProductWiseDetailsDto.class);
                ProductWiseDetails details = new ProductWiseDetails();
                details.setProductId(dto.getProductId());
                details.setDiscountPercent(dto.getDiscountPercent());
                details.setFlatDiscount(dto.getFlatDiscount());
                details.setMinQuantity(dto.getMinQuantity());
                details.setMaxUsagePerProduct(dto.getMaxUsagePerProduct());
                coupon.setDetails(details);
            }
            case BXGY -> {
                BxGyDetailsDto dto = objectMapper.convertValue(request.getDetails(), BxGyDetailsDto.class);
                BxGyDetails details = new BxGyDetails();
                details.setBuyProductIds(dto.getBuyProductIds());
                details.setBuyQuantity(dto.getBuyQuantity());
                details.setGetProductIds(dto.getGetProductIds());
                details.setGetQuantity(dto.getGetQuantity());
                details.setRepetitionLimit(dto.getRepetitionLimit());
                coupon.setDetails(details);
            }
            case SHIPPING -> {
                ShippingDetailsDto dto = objectMapper.convertValue(request.getDetails(), ShippingDetailsDto.class);
                ShippingDetails details = new ShippingDetails();
                details.setMinCartValueForFreeShipping(dto.getMinCartValueForFreeShipping());
                details.setFlatDiscountOnShipping(dto.getFlatDiscountOnShipping());
                details.setFreeShipping(dto.isFreeShipping());
                coupon.setDetails(details);
            }
            default -> {
                coupon.setDetails(null);
            }
        }
    }
    
}
