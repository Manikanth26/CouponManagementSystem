package com.monkcommerce.couponManagement.entity;

import java.time.LocalDateTime;

import com.monkcommerce.couponManagement.enums.CouponType;

import jakarta.persistence.*;

@Entity
@Table(name = "coupons")
public class Coupon {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String code;
	
	@Enumerated(EnumType.STRING)
	private CouponType type;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "details_id")
	private CouponDetails details;
	
	private boolean active = true;
	private LocalDateTime validFrom;
    private LocalDateTime validTo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public CouponDetails getDetails() {
		return details;
	}
	public void setDetails(CouponDetails details) {
		this.details = details;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
