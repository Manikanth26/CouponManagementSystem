package com.monkcommerce.couponManagement.DTO;

import java.time.LocalDateTime;

import com.monkcommerce.couponManagement.enums.CouponType;

public class CouponRequest {
	private String code;
    private CouponType type;
    private Object details;   
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    
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
	public Object getDetails() {
		return details;
	}
	public void setDetails(Object details) {
		this.details = details;
	}
	public LocalDateTime getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}
	public LocalDateTime getValidTo() {
		return validTo;
	}
	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}
    
}
