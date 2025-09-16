package com.monkcommerce.couponManagement.DTO;

public class ProductWiseDetailsDto {

	private Long productId;
    private double discountPercent;
    private Double flatDiscount;
    private Integer minQuantity;
    private Integer maxUsagePerProduct;
    
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public Integer getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(Integer minQuantity) {
		this.minQuantity = minQuantity;
	}
	public Integer getMaxUsagePerProduct() {
		return maxUsagePerProduct;
	}
	public void setMaxUsagePerProduct(Integer maxUsagePerProduct) {
		this.maxUsagePerProduct = maxUsagePerProduct;
	}
    
    
}
