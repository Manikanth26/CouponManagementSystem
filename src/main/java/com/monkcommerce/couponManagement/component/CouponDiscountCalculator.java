package com.monkcommerce.couponManagement.component;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkcommerce.couponManagement.DTO.cartDTO.CartDto;
import com.monkcommerce.couponManagement.entity.BxGyDetails;
import com.monkcommerce.couponManagement.entity.CartWiseDetails;
import com.monkcommerce.couponManagement.entity.Coupon;
import com.monkcommerce.couponManagement.entity.ProductWiseDetails;
import com.monkcommerce.couponManagement.entity.ShippingDetails;

@Component
public class CouponDiscountCalculator {

	private final ObjectMapper objectMapper;

    public CouponDiscountCalculator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public double calculateDiscount(Coupon coupon, CartDto cart) {
    	
    	double discount = 0;
        double originalTotal = cart.getCartTotal();
        double shippingFee = cart.getShippingFee();

        switch (coupon.getType()) {
            case CART_WISE -> {
                CartWiseDetails details = objectMapper.convertValue(coupon.getDetails(), CartWiseDetails.class);
                if (originalTotal >= details.getMinCartValue() &&
                        cart.getItemCount() >= (details.getMinItemsRequired() != null ? details.getMinItemsRequired() : 0)) {
                    if (details.getFlatDiscount() != null) {
                        discount = details.getFlatDiscount();
                    } else {
                        discount = (originalTotal * details.getDiscountPercent()) / 100.0;
                        if (details.getMaxDiscountCap() != null) {
                            discount = Math.min(discount, details.getMaxDiscountCap());
                        }
                    }
                }
            }
            case PRODUCT_WISE -> {
                ProductWiseDetails details = objectMapper.convertValue(coupon.getDetails(), ProductWiseDetails.class);
                discount = cart.getItems().stream().filter(item ->
                        (details.getProductId() != null && details.getProductId().equals(item.getProductId()))
                ).mapToDouble(item -> {
                    if (item.getQuantity() >= (details.getMinQuantity() != null ? details.getMinQuantity() : 0)) {
                        double d = details.getFlatDiscount() != null
                                ? details.getFlatDiscount() * item.getQuantity()
                                : (item.getPrice() * item.getQuantity() * details.getDiscountPercent()) / 100.0;
                        if (details.getMaxUsagePerProduct() != null) {
                            int applicableQty = Math.min(item.getQuantity(), details.getMaxUsagePerProduct());
                            d = (item.getPrice() * applicableQty * details.getDiscountPercent()) / 100.0;
                        }
                        return d;
                    }
                    return 0.0;
                }).sum();
            }
            case BXGY -> {
                BxGyDetails details = objectMapper.convertValue(coupon.getDetails(), BxGyDetails.class);
                int timesApplicable = cart.getItems().stream()
                        .filter(item -> details.getBuyProductIds().contains(item.getProductId()))
                        .mapToInt(item -> item.getQuantity() / details.getBuyQuantity())
                        .sum();

                if (details.getRepetitionLimit() != null) {
                    timesApplicable = Math.min(timesApplicable, details.getRepetitionLimit());
                }

                int freeItemsToApply = timesApplicable * details.getGetQuantity();
                discount = cart.getItems().stream()
                        .filter(item -> details.getGetProductIds().contains(item.getProductId()))
                        .sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                        .limit(freeItemsToApply)
                        .mapToDouble(i -> i.getPrice())
                        .sum();
            }
            case SHIPPING -> {
                ShippingDetails details = objectMapper.convertValue(coupon.getDetails(), ShippingDetails.class);
                if (originalTotal >= details.getMinCartValueForFreeShipping()) {
                    if (details.isFreeShipping()) {
                        discount = shippingFee;
                    } else if (details.getFlatDiscountOnShipping() != null) {
                        discount = Math.min(shippingFee, details.getFlatDiscountOnShipping());
                    }
                }
            }
        }
        return discount;
    }
}
