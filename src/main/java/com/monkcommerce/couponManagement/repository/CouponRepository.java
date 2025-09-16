package com.monkcommerce.couponManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkcommerce.couponManagement.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	Optional<Coupon> findByCode(String code);
	boolean existsByCode(String code);
}
