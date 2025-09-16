package com.monkcommerce.couponManagement.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CARTWISE")
public class CartWiseDetails extends CouponDetails {
	private double minCartValue;
    private double discountPercent;
    private Double flatDiscount;
    private Double maxDiscountCap;
    private Integer minItemsRequired;
    
	public double getMinCartValue() {
		return minCartValue;
	}
	public void setMinCartValue(double minCartValue) {
		this.minCartValue = minCartValue;
	}
	public double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public Double getFlatDiscount() {
		return flatDiscount;
	}
	public void setFlatDiscount(Double flatDiscount) {
		this.flatDiscount = flatDiscount;
	}
	public Double getMaxDiscountCap() {
		return maxDiscountCap;
	}
	public void setMaxDiscountCap(Double maxDiscountCap) {
		this.maxDiscountCap = maxDiscountCap;
	}
	public Integer getMinItemsRequired() {
		return minItemsRequired;
	}
	public void setMinItemsRequired(Integer minItemsRequired) {
		this.minItemsRequired = minItemsRequired;
	}
    
}
