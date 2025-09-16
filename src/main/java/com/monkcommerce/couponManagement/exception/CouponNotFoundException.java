package com.monkcommerce.couponManagement.exception;

public class CouponNotFoundException extends RuntimeException{
	
	public CouponNotFoundException(String message) {
        super(message);
    }

}
