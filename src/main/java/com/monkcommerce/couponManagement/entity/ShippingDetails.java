package com.monkcommerce.couponManagement.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SHIPPING")
public class ShippingDetails extends CouponDetails {
	
	private Double minCartValueForFreeShipping;
    private boolean freeShipping;
    private Double flatDiscountOnShipping;
    
	public Double getMinCartValueForFreeShipping() {
		return minCartValueForFreeShipping;
	}
	public void setMinCartValueForFreeShipping(Double minCartValueForFreeShipping) {
		this.minCartValueForFreeShipping = minCartValueForFreeShipping;
	}
	public boolean isFreeShipping() {
		return freeShipping;
	}
	public void setFreeShipping(boolean freeShipping) {
		this.freeShipping = freeShipping;
	}
	public Double getFlatDiscountOnShipping() {
		return flatDiscountOnShipping;
	}
	public void setFlatDiscountOnShipping(Double flatDiscountOnShipping) {
		this.flatDiscountOnShipping = flatDiscountOnShipping;
	}
    
    
    
}
