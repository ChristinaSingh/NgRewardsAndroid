package main.com.ngrewards.beanclasses;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GiftCertificateModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_gifts")
    @Expose
    private Integer totalGifts;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("result")
    @Expose
    private List<Result> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalGifts() {
        return totalGifts;
    }

    public void setTotalGifts(Integer totalGifts) {
        this.totalGifts = totalGifts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }



    public class Result {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("purchase_date")
        @Expose
        private String purchaseDate;
        @SerializedName("purchase_date_formatted")
        @Expose
        private String purchaseDateFormatted;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("order_total")
        @Expose
        private String orderTotal;
        @SerializedName("remaining_balance")
        @Expose
        private String remainingBalance;
        @SerializedName("used_amount")
        @Expose
        private String usedAmount;
        @SerializedName("usage_percentage")
        @Expose
        private Integer usagePercentage;
        @SerializedName("expiration_date")
        @Expose
        private String expirationDate;
        @SerializedName("expiration_date_formatted")
        @Expose
        private String expirationDateFormatted;
        @SerializedName("expiration_days")
        @Expose
        private Object expirationDays;
        @SerializedName("expiry_status")
        @Expose
        private String expiryStatus;
        @SerializedName("is_expired")
        @Expose
        private Boolean isExpired;
        @SerializedName("gift_codes")
        @Expose
        private GiftCodes giftCodes;
        @SerializedName("customer")
        @Expose
        private Customer customer;

        @SerializedName("merchant_details")
        @Expose
        private MerchantDetails merchantDetails;

        @SerializedName("payment_method")
        @Expose
        private Object paymentMethod;
        @SerializedName("products")
        @Expose
        private List<Product> products;
        @SerializedName("redeemed_at")
        @Expose
        private Object redeemedAt;
        @SerializedName("redemption_history")
        @Expose
        private List<RedemptionHistory> redemptionHistory;
        @SerializedName("total_redemptions")
        @Expose
        private Integer totalRedemptions;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getPurchaseDateFormatted() {
            return purchaseDateFormatted;
        }

        public void setPurchaseDateFormatted(String purchaseDateFormatted) {
            this.purchaseDateFormatted = purchaseDateFormatted;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(String orderTotal) {
            this.orderTotal = orderTotal;
        }

        public String getRemainingBalance() {
            return remainingBalance;
        }

        public void setRemainingBalance(String remainingBalance) {
            this.remainingBalance = remainingBalance;
        }

        public String getUsedAmount() {
            return usedAmount;
        }

        public void setUsedAmount(String usedAmount) {
            this.usedAmount = usedAmount;
        }

        public Integer getUsagePercentage() {
            return usagePercentage;
        }

        public void setUsagePercentage(Integer usagePercentage) {
            this.usagePercentage = usagePercentage;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getExpirationDateFormatted() {
            return expirationDateFormatted;
        }

        public void setExpirationDateFormatted(String expirationDateFormatted) {
            this.expirationDateFormatted = expirationDateFormatted;
        }

        public Object getExpirationDays() {
            return expirationDays;
        }

        public void setExpirationDays(Object expirationDays) {
            this.expirationDays = expirationDays;
        }

        public String getExpiryStatus() {
            return expiryStatus;
        }

        public void setExpiryStatus(String expiryStatus) {
            this.expiryStatus = expiryStatus;
        }

        public Boolean getIsExpired() {
            return isExpired;
        }

        public void setIsExpired(Boolean isExpired) {
            this.isExpired = isExpired;
        }

        public GiftCodes getGiftCodes() {
            return giftCodes;
        }

        public void setGiftCodes(GiftCodes giftCodes) {
            this.giftCodes = giftCodes;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Object getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(Object paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public Object getRedeemedAt() {
            return redeemedAt;
        }

        public void setRedeemedAt(Object redeemedAt) {
            this.redeemedAt = redeemedAt;
        }

        public List<RedemptionHistory> getRedemptionHistory() {
            return redemptionHistory;
        }

        public void setRedemptionHistory(List<RedemptionHistory> redemptionHistory) {
            this.redemptionHistory = redemptionHistory;
        }

        public Integer getTotalRedemptions() {
            return totalRedemptions;
        }

        public void setTotalRedemptions(Integer totalRedemptions) {
            this.totalRedemptions = totalRedemptions;
        }


        public MerchantDetails getMerchantDetails() {
            return merchantDetails;
        }

        public void setMerchantDetails(MerchantDetails merchantDetails) {
            this.merchantDetails = merchantDetails;
        }

        public class GiftCodes {

            @SerializedName("user_code")
            @Expose
            private String userCode;
            @SerializedName("merchant_code")
            @Expose
            private String merchantCode;
            @SerializedName("current_active_code")
            @Expose
            private String currentActiveCode;

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }

            public String getMerchantCode() {
                return merchantCode;
            }

            public void setMerchantCode(String merchantCode) {
                this.merchantCode = merchantCode;
            }

            public String getCurrentActiveCode() {
                return currentActiveCode;
            }

            public void setCurrentActiveCode(String currentActiveCode) {
                this.currentActiveCode = currentActiveCode;
            }

        }

        public class Product {

            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("quantity")
            @Expose
            private String quantity;
            @SerializedName("price")
            @Expose
            private String price;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

        }

        public class RedemptionHistory {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("parent_id")
            @Expose
            private Integer parentId;
            @SerializedName("round")
            @Expose
            private Integer round;
            @SerializedName("gift_code")
            @Expose
            private String giftCode;
            @SerializedName("previous_code")
            @Expose
            private String previousCode;
            @SerializedName("next_code")
            @Expose
            private String nextCode;
            @SerializedName("redeemed_amount")
            @Expose
            private String redeemedAmount;
            @SerializedName("previous_balance")
            @Expose
            private String previousBalance;
            @SerializedName("new_balance")
            @Expose
            private String newBalance;
            @SerializedName("gift_original_balance")
            @Expose
            private String giftOriginalBalance;
            @SerializedName("gift_used_amount")
            @Expose
            private String giftUsedAmount;
            @SerializedName("promotional_value")
            @Expose
            private String promotionalValue;
            @SerializedName("swipe_charge")
            @Expose
            private String swipeCharge;
            @SerializedName("customer_pays")
            @Expose
            private String customerPays;
            @SerializedName("merchant_name")
            @Expose
            private String merchantName;
            @SerializedName("redemption_date")
            @Expose
            private String redemptionDate;
            @SerializedName("redemption_date_formatted")
            @Expose
            private String redemptionDateFormatted;
            @SerializedName("is_current")
            @Expose
            private Boolean isCurrent;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("notes")
            @Expose
            private String notes;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getParentId() {
                return parentId;
            }

            public void setParentId(Integer parentId) {
                this.parentId = parentId;
            }

            public Integer getRound() {
                return round;
            }

            public void setRound(Integer round) {
                this.round = round;
            }

            public String getGiftCode() {
                return giftCode;
            }

            public void setGiftCode(String giftCode) {
                this.giftCode = giftCode;
            }

            public String getPreviousCode() {
                return previousCode;
            }

            public void setPreviousCode(String previousCode) {
                this.previousCode = previousCode;
            }

            public String getNextCode() {
                return nextCode;
            }

            public void setNextCode(String nextCode) {
                this.nextCode = nextCode;
            }

            public String getRedeemedAmount() {
                return redeemedAmount;
            }

            public void setRedeemedAmount(String redeemedAmount) {
                this.redeemedAmount = redeemedAmount;
            }

            public String getPreviousBalance() {
                return previousBalance;
            }

            public void setPreviousBalance(String previousBalance) {
                this.previousBalance = previousBalance;
            }

            public String getNewBalance() {
                return newBalance;
            }

            public void setNewBalance(String newBalance) {
                this.newBalance = newBalance;
            }

            public String getGiftOriginalBalance() {
                return giftOriginalBalance;
            }

            public void setGiftOriginalBalance(String giftOriginalBalance) {
                this.giftOriginalBalance = giftOriginalBalance;
            }

            public String getGiftUsedAmount() {
                return giftUsedAmount;
            }

            public void setGiftUsedAmount(String giftUsedAmount) {
                this.giftUsedAmount = giftUsedAmount;
            }

            public String getPromotionalValue() {
                return promotionalValue;
            }

            public void setPromotionalValue(String promotionalValue) {
                this.promotionalValue = promotionalValue;
            }

            public String getSwipeCharge() {
                return swipeCharge;
            }

            public void setSwipeCharge(String swipeCharge) {
                this.swipeCharge = swipeCharge;
            }

            public String getCustomerPays() {
                return customerPays;
            }

            public void setCustomerPays(String customerPays) {
                this.customerPays = customerPays;
            }

            public String getMerchantName() {
                return merchantName;
            }

            public void setMerchantName(String merchantName) {
                this.merchantName = merchantName;
            }

            public String getRedemptionDate() {
                return redemptionDate;
            }

            public void setRedemptionDate(String redemptionDate) {
                this.redemptionDate = redemptionDate;
            }

            public String getRedemptionDateFormatted() {
                return redemptionDateFormatted;
            }

            public void setRedemptionDateFormatted(String redemptionDateFormatted) {
                this.redemptionDateFormatted = redemptionDateFormatted;
            }

            public Boolean getIsCurrent() {
                return isCurrent;
            }

            public void setIsCurrent(Boolean isCurrent) {
                this.isCurrent = isCurrent;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

        }

        public class Customer {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

        }

        public class MerchantDetails {

            @SerializedName("merchant_id")
            @Expose
            private String merchantId;
            @SerializedName("business_name")
            @Expose
            private String businessName;
            @SerializedName("mobile")
            @Expose
            private String mobile;
            @SerializedName("address")
            @Expose
            private String address;

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }






    }



}



