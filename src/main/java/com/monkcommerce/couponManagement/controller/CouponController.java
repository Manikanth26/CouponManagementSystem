package com.monkcommerce.couponManagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkcommerce.couponManagement.DTO.CouponRequest;
import com.monkcommerce.couponManagement.DTO.cartDTO.ApplicableCouponDto;
import com.monkcommerce.couponManagement.DTO.cartDTO.ApplyCouponResponse;
import com.monkcommerce.couponManagement.DTO.cartDTO.CartDto;
import com.monkcommerce.couponManagement.entity.Coupon;
import com.monkcommerce.couponManagement.service.CouponService;

@RestController
@RequestMapping("/coupons")
public class CouponController {
	
	private final CouponService couponService;
	
	public CouponController(CouponService couponService) {
		this.couponService=couponService;
	}
	
	@PostMapping
	public ResponseEntity<Coupon> createCoupon(@RequestBody CouponRequest couponRequest){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(couponService.createCoupon(couponRequest));
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Coupon> getCoupon(@PathVariable Long id) {
        return couponService.getCoupon(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody CouponRequest request) {
        return couponService.updateCoupon(id, request)
                .map(updated -> ResponseEntity.ok(updated))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PostMapping("/apply-coupon/{code}")
    public ResponseEntity<ApplyCouponResponse> applyCoupon(@PathVariable String code, @RequestBody CartDto cart) throws Exception{
    	return ResponseEntity.ok(couponService.applyCoupon(code, cart));
    }
    
    @PostMapping("/applicable-coupons")
    public ResponseEntity<List<ApplicableCouponDto>> getApplicableCoupons(@RequestBody CartDto cart){
    	return ResponseEntity.ok(couponService.getApplicableCoupons(cart));
    }
    
    
    
}
