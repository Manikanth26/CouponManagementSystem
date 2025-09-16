package com.monkcommerce.couponManagement.DTO;

import java.util.List;

public class BxGyDetailsDto {

	private List<Long> buyProductIds;
    private int buyQuantity;
    private List<Long> getProductIds;
    private int getQuantity;
    private int repetitionLimit;
    
	public List<Long> getBuyProductIds() {
		return buyProductIds;
	}
	public void setBuyProductIds(List<Long> buyProductIds) {
		this.buyProductIds = buyProductIds;
	}
	public int getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	public List<Long> getGetProductIds() {
		return getProductIds;
	}
	public void setGetProductIds(List<Long> getProductIds) {
		this.getProductIds = getProductIds;
	}
	public int getGetQuantity() {
		return getQuantity;
	}
	public void setGetQuantity(int getQuantity) {
		this.getQuantity = getQuantity;
	}
	public int getRepetitionLimit() {
		return repetitionLimit;
	}
	public void setRepetitionLimit(int repetitionLimit) {
		this.repetitionLimit = repetitionLimit;
	}
    
    
    
}
