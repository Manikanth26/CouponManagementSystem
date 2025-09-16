# Coupon Management System

This project is a **Spring Boot based Coupon Management API** that allows creating, updating, applying, and validating different types of coupons for an e-commerce shopping cart.  

It supports **cart-wise, product-wise, BXGY (Buy X Get Y), and shipping-related coupons** with a centralized validation and discount calculation system.  

---

## Implemented Cases ✅

### 1. Cart-wise Coupon
- Coupons applied on the overall cart value.  
- Supports:
  - Minimum cart value validation
  - Percentage discounts
  - Flat discounts
  - Maximum discount cap
  - Minimum number of items required

**Example:**  
`10% off if cart total > ₹500, max discount ₹200`

---

### 2. Product-wise Coupon
- Coupons applied to specific products in the cart.  
- Supports:
  - Minimum quantity validation
  - Percentage discounts
  - Flat discounts
  - Maximum usage per product

**Example:**  
`Buy 2 or more of Product A → Get ₹50 off per unit`

---

### 3. BXGY (Buy X Get Y) Coupon
- Classic **Buy X, Get Y Free/Discounted** offer.  
- Supports:
  - Multiple product IDs for "buy" and "get"
  - Configurable buy & get quantities
  - Repetition limit (e.g., offer applies max 2 times per order)

**Example:**  
`Buy 2 T-Shirts, Get 1 Cap free (max 2 repetitions)`

---

### 4. Shipping Discount Coupon
- Coupons that reduce or remove shipping costs.  
- Supports:
  - Free shipping if cart meets minimum value
  - Flat discount on shipping fees
  - Complete free shipping toggle

**Example:**  
`Free shipping for orders above ₹1000`  

---

### 5. General Features
- Create, update, delete, and fetch coupons.  
- Apply coupon code to cart and get final price.  
- Fetch all **applicable coupons** for a given cart.  
- Validation for:
  - Coupon active/inactive status
  - Validity dates
  - Non-existent coupon codes
- Error handling with meaningful error codes (`400`, `404`, `409`, `500`).  

---

## Unimplemented Cases 🚫

1. **Coupon Combinations / Stacking**  
   - Currently, only **one coupon can be applied at a time**.  
   - Stacking multiple coupons requires conflict resolution logic (priority, exclusions).

2. **User-Specific / First-Time User Coupons**  
   - No support for coupons restricted to specific users, groups, or order history.  

3. **Usage Tracking Across Orders**  
   - System does not track **global usage limits** (e.g., "first 100 users" or "max 5 times per customer").  

4. **Advanced Discount Rules**  
   - Tiered discounts (e.g., "10% off on ₹1000, 20% off on ₹5000") not implemented.  

---

## Limitations ⚠️

1. Relies on valid DTOs from client (minimal validation).  
2. No coupon stacking support.  
3. No audit logs for coupon creation/usage.  
4. Concurrency issues may arise for high-volume coupon usage.

---

## Assumptions 📝

1. One coupon can be applied per cart.  
2. All calculations assume a **single currency** (₹ in examples).  
3. Coupons validated using **system server time** (`LocalDateTime.now()`).  
4. Cart DTO always includes items and shipping fee.  
5. No user-specific restrictions are considered.  
