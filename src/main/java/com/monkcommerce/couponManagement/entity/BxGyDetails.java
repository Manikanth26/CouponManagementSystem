package com.monkcommerce.couponManagement.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BXGY")
public class BxGyDetails extends CouponDetails {
	
	private int buyQuantity;
    private int getQuantity;
    private Integer repetitionLimit;
    
    @ElementCollection
    @CollectionTable(name = "bxgy_buy_products", joinColumns = @JoinColumn(name = "bxgy_id"))
    @Column(name = "product_id")
    private List<Long> buyProductIds;

    @ElementCollection
    @CollectionTable(name = "bxgy_get_products", joinColumns = @JoinColumn(name = "bxgy_id"))
    @Column(name = "product_id")
    private List<Long> getProductIds;
    
	public int getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	public int getGetQuantity() {
		return getQuantity;
	}
	public void setGetQuantity(int getQuantity) {
		this.getQuantity = getQuantity;
	}
	public Integer getRepetitionLimit() {
		return repetitionLimit;
	}
	public void setRepetitionLimit(Integer repetitionLimit) {
		this.repetitionLimit = repetitionLimit;
	}
	public List<Long> getBuyProductIds() {
		return buyProductIds;
	}
	public void setBuyProductIds(List<Long> buyProductIds) {
		this.buyProductIds = buyProductIds;
	}
	public List<Long> getGetProductIds() {
		return getProductIds;
	}
	public void setGetProductIds(List<Long> getProductIds) {
		this.getProductIds = getProductIds;
	}
    
    
}
