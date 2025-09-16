package com.monkcommerce.couponManagement.DTO.cartDTO;

import com.monkcommerce.couponManagement.enums.CouponType;

public class ApplicableCouponDto {
	
	private Long couponId;
    private String code;
    private CouponType type;
    private double discount;
    private double finalPrice;
    
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public CouponType getType() {
		return type;
	}
	public void setType(CouponType type) {
		this.type = type;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	
    
}
