package com.monkcommerce.couponManagement.DTO.cartDTO;

import java.util.List;

public class CartDto {
	private List<CartItemDto> items;
    private double shippingFee;

    public List<CartItemDto> getItems() {
		return items;
	}

	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}

	public double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public double getCartTotal() {
        return items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }

    public int getItemCount() {
        return items.stream().mapToInt(CartItemDto::getQuantity).sum();
    }
	
}
