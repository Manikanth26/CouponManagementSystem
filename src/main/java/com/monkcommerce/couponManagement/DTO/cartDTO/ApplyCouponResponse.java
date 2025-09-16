package com.monkcommerce.couponManagement.DTO.cartDTO;

public class ApplyCouponResponse {

	private String couponCode;
    private double originalTotal;
    private double discount;
    private double finalTotal;
    private double shippingFee;
    
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public double getOriginalTotal() {
		return originalTotal;
	}
	public void setOriginalTotal(double originalTotal) {
		this.originalTotal = originalTotal;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getFinalTotal() {
		return finalTotal;
	}
	public void setFinalTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}
	public double getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}
    
    
}
