package main.com.ngrewards.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import main.com.ngrewards.beanclasses.NotificationBeanNew;

/*public class NotificationModel {

    @SerializedName("result")
    @Expose
    private ArrayList<Result> result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("reciever_id")
        @Expose
        private String recieverId;
        @SerializedName("chat_message")
        @Expose
        private String chatMessage;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("payload")
        @Expose
        private Payload payload;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecieverId() {
            return recieverId;
        }

        public void setRecieverId(String recieverId) {
            this.recieverId = recieverId;
        }

        public String getChatMessage() {
            return chatMessage;
        }

        public void setChatMessage(String chatMessage) {
            this.chatMessage = chatMessage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Payload getPayload() {
            return payload;
        }

        public void setPayload(Payload payload) {
            this.payload = payload;
        }


        public class Payload {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("text")
            @Expose
            private String text;
            @SerializedName("ios_status")
            @Expose
            private String iosStatus;
            @SerializedName("sound")
            @Expose
            private String sound;
            @SerializedName("badge")
            @Expose
            private String badge;
            @SerializedName("type")
            @Expose
            private Integer type;
            @SerializedName("content-available")
            @Expose
            private Integer contentAvailable;
            @SerializedName("cart_id")
            @Expose
            private String cartId;
            @SerializedName("order_id")
            @Expose
            private String orderId;
            @SerializedName("split_amount_x")
            @Expose
            private String splitAmountX;
            @SerializedName("merchant_id")
            @Expose
            private String merchantId;
            @SerializedName("merchant_business_no")
            @Expose
            private String merchantBusinessNo;
            @SerializedName("merchant_business_name")
            @Expose
            private String merchantBusinessName;
            @SerializedName("member_id")
            @Expose
            private String memberId;
            @SerializedName("due_date")
            @Expose
            private String dueDate;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("number_of_emi")
            @Expose
            private String numberOfEmi;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getIosStatus() {
                return iosStatus;
            }

            public void setIosStatus(String iosStatus) {
                this.iosStatus = iosStatus;
            }

            public String getSound() {
                return sound;
            }

            public void setSound(String sound) {
                this.sound = sound;
            }

            public String getBadge() {
                return badge;
            }

            public void setBadge(String badge) {
                this.badge = badge;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public Integer getContentAvailable() {
                return contentAvailable;
            }

            public void setContentAvailable(Integer contentAvailable) {
                this.contentAvailable = contentAvailable;
            }

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getSplitAmountX() {
                return splitAmountX;
            }

            public void setSplitAmountX(String splitAmountX) {
                this.splitAmountX = splitAmountX;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getMerchantBusinessNo() {
                return merchantBusinessNo;
            }

            public void setMerchantBusinessNo(String merchantBusinessNo) {
                this.merchantBusinessNo = merchantBusinessNo;
            }

            public String getMerchantBusinessName() {
                return merchantBusinessName;
            }

            public void setMerchantBusinessName(String merchantBusinessName) {
                this.merchantBusinessName = merchantBusinessName;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getDueDate() {
                return dueDate;
            }

            public void setDueDate(String dueDate) {
                this.dueDate = dueDate;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getNumberOfEmi() {
                return numberOfEmi;
            }

            public void setNumberOfEmi(String numberOfEmi) {
                this.numberOfEmi = numberOfEmi;
            }

            @Override
            public String toString() {

                *//*         merchant_id = data.getString("merchant_id");
                    merchant_name = data.getString("merchant_business_name");
                   merchant_number = data.getString("merchant_business_no");
                    order_cart_id = data.getString("cart_id");
                  sub_total_price = data.getString("split_amount_x");
                   tax_price = data.getString("split_amount_x");
                  total_amount_due = data.getString("split_amount_x");
                   type = data.getString("type");
                  quantity = data.getString("split_amount_x");
                employee_sales_id = data.getString("split_amount_x");
                employee_slaes_name = data.getString("split_amount_x");
                order_id = data.getString("order_id");*//*
                return "{" +
                        "title='" + title + '\'' +
                        ", text='" + text + '\'' +
                        ", iosStatus='" + iosStatus + '\'' +
                        ", sound='" + sound + '\'' +
                        ", badge='" + badge + '\'' +
                        ", type=" + type +
                        ", contentAvailable=" + contentAvailable +
                        ", cart_id='" + cartId + '\'' +
                        ", order_id='" + orderId + '\'' +
                        ", split_amount_x='" + splitAmountX + '\'' +
                        ", merchant_id='" + merchantId + '\'' +
                        ", merchant_business_no='" + merchantBusinessNo + '\'' +
                        ", merchant_business_name='" + merchantBusinessName + '\'' +
                        ", memberId='" + memberId + '\'' +
                        ", dueDate='" + dueDate + '\'' +
                        ", message='" + message + '\'' +
                        ", numberOfEmi='" + numberOfEmi + '\'' +
                        '}';
            }
        }

    }
}*/


public class NotificationModel {

    @SerializedName("result")
    @Expose
    private List<Result> result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("reciever_id")
        @Expose
        private String recieverId;
        @SerializedName("chat_message")
        @Expose
        private String chatMessage;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("payload")
        @Expose
        private Payload payload;
        @SerializedName("json_data")
        @Expose
        private JsonData jsonData;
        @SerializedName("pay_bill_data")
        @Expose
        private List<PayBillDatum> payBillData;

        @SerializedName("transfer_request_data")
        @Expose
        private List<NotificationBeanNew.TransferRequestDatum> transferRequestData;

        public List<NotificationBeanNew.TransferRequestDatum> getTransferRequestData() {
            return transferRequestData;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecieverId() {
            return recieverId;
        }

        public void setRecieverId(String recieverId) {
            this.recieverId = recieverId;
        }

        public String getChatMessage() {
            return chatMessage;
        }

        public void setChatMessage(String chatMessage) {
            this.chatMessage = chatMessage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Payload getPayload() {
            return payload;
        }

        public void setPayload(Payload payload) {
            this.payload = payload;
        }

        public JsonData getJsonData() {
            return jsonData;
        }

        public void setJsonData(JsonData jsonData) {
            this.jsonData = jsonData;
        }

        public List<PayBillDatum> getPayBillData() {
            return payBillData;
        }

        public void setPayBillData(List<PayBillDatum> payBillData) {
            this.payBillData = payBillData;
        }



        public class Payload {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("text")
            @Expose
            private String text;
            @SerializedName("ios_status")
            @Expose
            private String iosStatus;
            @SerializedName("sound")
            @Expose
            private String sound;
            @SerializedName("badge")
            @Expose
            private String badge;
            @SerializedName("type")
            @Expose
            private Integer type;
            @SerializedName("content-available")
            @Expose
            private Integer contentAvailable;
            @SerializedName("cart_id")
            @Expose
            private String cartId;
            @SerializedName("order_id")
            @Expose
            private String orderId;
            @SerializedName("split_amount_x")
            @Expose
            private String splitAmountX;
            @SerializedName("merchant_id")
            @Expose
            private String merchantId;
            @SerializedName("merchant_business_no")
            @Expose
            private String merchantBusinessNo;
            @SerializedName("merchant_business_name")
            @Expose
            private String merchantBusinessName;
            @SerializedName("member_id")
            @Expose
            private String memberId;
            @SerializedName("due_date")
            @Expose
            private String dueDate;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("number_of_emi")
            @Expose
            private String numberOfEmi;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getIosStatus() {
                return iosStatus;
            }

            public void setIosStatus(String iosStatus) {
                this.iosStatus = iosStatus;
            }

            public String getSound() {
                return sound;
            }

            public void setSound(String sound) {
                this.sound = sound;
            }

            public String getBadge() {
                return badge;
            }

            public void setBadge(String badge) {
                this.badge = badge;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public Integer getContentAvailable() {
                return contentAvailable;
            }

            public void setContentAvailable(Integer contentAvailable) {
                this.contentAvailable = contentAvailable;
            }

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getSplitAmountX() {
                return splitAmountX;
            }

            public void setSplitAmountX(String splitAmountX) {
                this.splitAmountX = splitAmountX;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getMerchantBusinessNo() {
                return merchantBusinessNo;
            }

            public void setMerchantBusinessNo(String merchantBusinessNo) {
                this.merchantBusinessNo = merchantBusinessNo;
            }

            public String getMerchantBusinessName() {
                return merchantBusinessName;
            }

            public void setMerchantBusinessName(String merchantBusinessName) {
                this.merchantBusinessName = merchantBusinessName;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getDueDate() {
                return dueDate;
            }

            public void setDueDate(String dueDate) {
                this.dueDate = dueDate;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getNumberOfEmi() {
                return numberOfEmi;
            }

            public void setNumberOfEmi(String numberOfEmi) {
                this.numberOfEmi = numberOfEmi;
            }

            @Override
            public String toString() {

                /*         merchant_id = data.getString("merchant_id");
                    merchant_name = data.getString("merchant_business_name");
                   merchant_number = data.getString("merchant_business_no");
                    order_cart_id = data.getString("cart_id");
                  sub_total_price = data.getString("split_amount_x");
                   tax_price = data.getString("split_amount_x");
                  total_amount_due = data.getString("split_amount_x");
                   type = data.getString("type");
                  quantity = data.getString("split_amount_x");
                employee_sales_id = data.getString("split_amount_x");
                employee_slaes_name = data.getString("split_amount_x");
                order_id = data.getString("order_id");*/
                return "{" +
                        "title='" + title + '\'' +
                        ", text='" + text + '\'' +
                        ", iosStatus='" + iosStatus + '\'' +
                        ", sound='" + sound + '\'' +
                        ", badge='" + badge + '\'' +
                        ", type=" + type +
                        ", contentAvailable=" + contentAvailable +
                        ", cart_id='" + cartId + '\'' +
                        ", order_id='" + orderId + '\'' +
                        ", split_amount_x='" + splitAmountX + '\'' +
                        ", merchant_id='" + merchantId + '\'' +
                        ", merchant_business_no='" + merchantBusinessNo + '\'' +
                        ", merchant_business_name='" + merchantBusinessName + '\'' +
                        ", memberId='" + memberId + '\'' +
                        ", dueDate='" + dueDate + '\'' +
                        ", message='" + message + '\'' +
                        ", numberOfEmi='" + numberOfEmi + '\'' +
                        '}';
            }
        }


        public class JsonData {

            @SerializedName("wp_32pay_bill_id")
            @Expose
            private Integer wp32payBillId;

            public Integer getWp32payBillId() {
                return wp32payBillId;
            }

            public void setWp32payBillId(Integer wp32payBillId) {
                this.wp32payBillId = wp32payBillId;
            }

        }

        public class PayBillDatum {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("merchant_id")
            @Expose
            private String merchantId;
            @SerializedName("merchant_no")
            @Expose
            private String merchantNo;
            @SerializedName("member_id")
            @Expose
            private String memberId;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("ngcash")
            @Expose
            private String ngcash;
            @SerializedName("tip_amount")
            @Expose
            private String tipAmount;
            @SerializedName("total_amount")
            @Expose
            private String totalAmount;
            @SerializedName("card_id")
            @Expose
            private String cardId;
            @SerializedName("card_number")
            @Expose
            private String cardNumber;
            @SerializedName("card_brand")
            @Expose
            private String cardBrand;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("customer_id")
            @Expose
            private String customerId;
            @SerializedName("order_date")
            @Expose
            private String orderDate;
            @SerializedName("pay_bill_date")
            @Expose
            private String payBillDate;
            @SerializedName("month")
            @Expose
            private String month;
            @SerializedName("year")
            @Expose
            private String year;
            @SerializedName("created_date")
            @Expose
            private String createdDate;
            @SerializedName("employee_name")
            @Expose
            private String employeeName;
            @SerializedName("employee_id")
            @Expose
            private String employeeId;
            @SerializedName("reciept_url")
            @Expose
            private String recieptUrl;
            @SerializedName("ngcash_earned_by_member")
            @Expose
            private String ngcashEarnedByMember;
            @SerializedName("hide_status")
            @Expose
            private String hideStatus;
            @SerializedName("xcrud_fake_field_1")
            @Expose
            private String xcrudFakeField1;
            @SerializedName("add_to_cart_emai_id")
            @Expose
            private String addToCartEmaiId;
            @SerializedName("add_to_cart_type")
            @Expose
            private String addToCartType;
            @SerializedName("search_id")
            @Expose
            private String searchId;
            @SerializedName("paid_by_card")
            @Expose
            private String paidByCard;
            @SerializedName("member_detail")
            @Expose
            private List<MemberDetail> memberDetail;
            @SerializedName("b_name")
            @Expose
            private String bName;
            @SerializedName("symbol_amount")
            @Expose
            private String symbolAmount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getMerchantNo() {
                return merchantNo;
            }

            public void setMerchantNo(String merchantNo) {
                this.merchantNo = merchantNo;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getNgcash() {
                return ngcash;
            }

            public void setNgcash(String ngcash) {
                this.ngcash = ngcash;
            }

            public String getTipAmount() {
                return tipAmount;
            }

            public void setTipAmount(String tipAmount) {
                this.tipAmount = tipAmount;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getCardId() {
                return cardId;
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getCardBrand() {
                return cardBrand;
            }

            public void setCardBrand(String cardBrand) {
                this.cardBrand = cardBrand;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getOrderDate() {
                return orderDate;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
            }

            public String getPayBillDate() {
                return payBillDate;
            }

            public void setPayBillDate(String payBillDate) {
                this.payBillDate = payBillDate;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public String getEmployeeId() {
                return employeeId;
            }

            public void setEmployeeId(String employeeId) {
                this.employeeId = employeeId;
            }

            public String getRecieptUrl() {
                return recieptUrl;
            }

            public void setRecieptUrl(String recieptUrl) {
                this.recieptUrl = recieptUrl;
            }

            public String getNgcashEarnedByMember() {
                return ngcashEarnedByMember;
            }

            public void setNgcashEarnedByMember(String ngcashEarnedByMember) {
                this.ngcashEarnedByMember = ngcashEarnedByMember;
            }

            public String getHideStatus() {
                return hideStatus;
            }

            public void setHideStatus(String hideStatus) {
                this.hideStatus = hideStatus;
            }

            public String getXcrudFakeField1() {
                return xcrudFakeField1;
            }

            public void setXcrudFakeField1(String xcrudFakeField1) {
                this.xcrudFakeField1 = xcrudFakeField1;
            }

            public String getAddToCartEmaiId() {
                return addToCartEmaiId;
            }

            public void setAddToCartEmaiId(String addToCartEmaiId) {
                this.addToCartEmaiId = addToCartEmaiId;
            }

            public String getAddToCartType() {
                return addToCartType;
            }

            public void setAddToCartType(String addToCartType) {
                this.addToCartType = addToCartType;
            }

            public String getSearchId() {
                return searchId;
            }

            public void setSearchId(String searchId) {
                this.searchId = searchId;
            }

            public String getPaidByCard() {
                return paidByCard;
            }

            public void setPaidByCard(String paidByCard) {
                this.paidByCard = paidByCard;
            }

            public List<MemberDetail> getMemberDetail() {
                return memberDetail;
            }

            public void setMemberDetail(List<MemberDetail> memberDetail) {
                this.memberDetail = memberDetail;
            }

            public String getbName() {
                return bName;
            }

            public void setbName(String bName) {
                this.bName = bName;
            }

            public String getSymbolAmount() {
                return symbolAmount;
            }

            public void setSymbolAmount(String symbolAmount) {
                this.symbolAmount = symbolAmount;
            }

            public class MemberDetail {

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("username")
                @Expose
                private String username;
                @SerializedName("email")
                @Expose
                private String email;
                @SerializedName("phone")
                @Expose
                private String phone;
                @SerializedName("fullname")
                @Expose
                private String fullname;
                @SerializedName("password")
                @Expose
                private String password;
                @SerializedName("affiliate_name")
                @Expose
                private String affiliateName;
                @SerializedName("how_invited_you")
                @Expose
                private String howInvitedYou;
                @SerializedName("affiliate_number")
                @Expose
                private String affiliateNumber;
                @SerializedName("user_type")
                @Expose
                private String userType;
                @SerializedName("latitude")
                @Expose
                private String latitude;
                @SerializedName("longitude")
                @Expose
                private String longitude;
                @SerializedName("country_name")
                @Expose
                private String countryName;
                @SerializedName("touch_status")
                @Expose
                private String touchStatus;
                @SerializedName("member_image")
                @Expose
                private String memberImage;
                @SerializedName("country_id")
                @Expose
                private String countryId;
                @SerializedName("device_token")
                @Expose
                private String deviceToken;
                @SerializedName("gender")
                @Expose
                private String gender;
                @SerializedName("age")
                @Expose
                private String age;
                @SerializedName("social_id")
                @Expose
                private String socialId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getFullname() {
                    return fullname;
                }

                public void setFullname(String fullname) {
                    this.fullname = fullname;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getAffiliateName() {
                    return affiliateName;
                }

                public void setAffiliateName(String affiliateName) {
                    this.affiliateName = affiliateName;
                }

                public String getHowInvitedYou() {
                    return howInvitedYou;
                }

                public void setHowInvitedYou(String howInvitedYou) {
                    this.howInvitedYou = howInvitedYou;
                }

                public String getAffiliateNumber() {
                    return affiliateNumber;
                }

                public void setAffiliateNumber(String affiliateNumber) {
                    this.affiliateNumber = affiliateNumber;
                }

                public String getUserType() {
                    return userType;
                }

                public void setUserType(String userType) {
                    this.userType = userType;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getCountryName() {
                    return countryName;
                }

                public void setCountryName(String countryName) {
                    this.countryName = countryName;
                }

                public String getTouchStatus() {
                    return touchStatus;
                }

                public void setTouchStatus(String touchStatus) {
                    this.touchStatus = touchStatus;
                }

                public String getMemberImage() {
                    return memberImage;
                }

                public void setMemberImage(String memberImage) {
                    this.memberImage = memberImage;
                }

                public String getCountryId() {
                    return countryId;
                }

                public void setCountryId(String countryId) {
                    this.countryId = countryId;
                }

                public String getDeviceToken() {
                    return deviceToken;
                }

                public void setDeviceToken(String deviceToken) {
                    this.deviceToken = deviceToken;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getAge() {
                    return age;
                }

                public void setAge(String age) {
                    this.age = age;
                }

                public String getSocialId() {
                    return socialId;
                }

                public void setSocialId(String socialId) {
                    this.socialId = socialId;
                }

            }


        }

        public class TransferRequestDatum {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("member_id")
            @Expose
            private String memberId;
            @SerializedName("transfer_request_user_id")
            @Expose
            private String transferRequestUserId;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("amount_by_card")
            @Expose
            private String amountByCard;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("ngcash")
            @Expose
            private String ngcash;
            @SerializedName("comment")
            @Expose
            private String comment;
            @SerializedName("card_id")
            @Expose
            private String cardId;
            @SerializedName("card_number")
            @Expose
            private String cardNumber;
            @SerializedName("card_brand")
            @Expose
            private String cardBrand;
            @SerializedName("customer_id")
            @Expose
            private String customerId;
            @SerializedName("timezone")
            @Expose
            private String timezone;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("created_date")
            @Expose
            private String createdDate;
            @SerializedName("reciept_url")
            @Expose
            private String recieptUrl;
            @SerializedName("ngcash_earned")
            @Expose
            private String ngcashEarned;
            @SerializedName("search_id")
            @Expose
            private String searchId;
            @SerializedName("total_amount")
            @Expose
            private String totalAmount;
            @SerializedName("paid_by_card")
            @Expose
            private String paidByCard;
            @SerializedName("member_detail")
            @Expose
            private List<NotificationBeanNew.TransferRequestDatum.MemberDetail> memberDetail;
            @SerializedName("b_name")
            @Expose
            private String bName;
            @SerializedName("symbol_amount")
            @Expose
            private String symbolAmount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getTransferRequestUserId() {
                return transferRequestUserId;
            }

            public void setTransferRequestUserId(String transferRequestUserId) {
                this.transferRequestUserId = transferRequestUserId;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAmountByCard() {
                return amountByCard;
            }

            public void setAmountByCard(String amountByCard) {
                this.amountByCard = amountByCard;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNgcash() {
                return ngcash;
            }

            public void setNgcash(String ngcash) {
                this.ngcash = ngcash;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getCardId() {
                return cardId;
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getCardBrand() {
                return cardBrand;
            }

            public void setCardBrand(String cardBrand) {
                this.cardBrand = cardBrand;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getRecieptUrl() {
                return recieptUrl;
            }

            public void setRecieptUrl(String recieptUrl) {
                this.recieptUrl = recieptUrl;
            }

            public String getNgcashEarned() {
                return ngcashEarned;
            }

            public void setNgcashEarned(String ngcashEarned) {
                this.ngcashEarned = ngcashEarned;
            }

            public String getSearchId() {
                return searchId;
            }

            public void setSearchId(String searchId) {
                this.searchId = searchId;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getPaidByCard() {
                return paidByCard;
            }

            public void setPaidByCard(String paidByCard) {
                this.paidByCard = paidByCard;
            }

            public List<NotificationBeanNew.TransferRequestDatum.MemberDetail> getMemberDetail() {
                return memberDetail;
            }

            public void setMemberDetail(List<NotificationBeanNew.TransferRequestDatum.MemberDetail> memberDetail) {
                this.memberDetail = memberDetail;
            }

            public String getbName() {
                return bName;
            }

            public void setbName(String bName) {
                this.bName = bName;
            }

            public String getSymbolAmount() {
                return symbolAmount;
            }

            public void setSymbolAmount(String symbolAmount) {
                this.symbolAmount = symbolAmount;
            }


            public class MemberDetail {

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("username")
                @Expose
                private String username;
                @SerializedName("email")
                @Expose
                private String email;
                @SerializedName("phone")
                @Expose
                private String phone;
                @SerializedName("fullname")
                @Expose
                private String fullname;
                @SerializedName("password")
                @Expose
                private String password;
                @SerializedName("affiliate_name")
                @Expose
                private String affiliateName;
                @SerializedName("how_invited_you")
                @Expose
                private String howInvitedYou;
                @SerializedName("affiliate_number")
                @Expose
                private String affiliateNumber;
                @SerializedName("user_type")
                @Expose
                private String userType;
                @SerializedName("latitude")
                @Expose
                private String latitude;
                @SerializedName("longitude")
                @Expose
                private String longitude;
                @SerializedName("country_name")
                @Expose
                private String countryName;
                @SerializedName("touch_status")
                @Expose
                private String touchStatus;
                @SerializedName("member_image")
                @Expose
                private String memberImage;
                @SerializedName("country_id")
                @Expose
                private String countryId;
                @SerializedName("device_token")
                @Expose
                private String deviceToken;
                @SerializedName("gender")
                @Expose
                private String gender;
                @SerializedName("age")
                @Expose
                private String age;
                @SerializedName("social_id")
                @Expose
                private String socialId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getFullname() {
                    return fullname;
                }

                public void setFullname(String fullname) {
                    this.fullname = fullname;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getAffiliateName() {
                    return affiliateName;
                }

                public void setAffiliateName(String affiliateName) {
                    this.affiliateName = affiliateName;
                }

                public String getHowInvitedYou() {
                    return howInvitedYou;
                }

                public void setHowInvitedYou(String howInvitedYou) {
                    this.howInvitedYou = howInvitedYou;
                }

                public String getAffiliateNumber() {
                    return affiliateNumber;
                }

                public void setAffiliateNumber(String affiliateNumber) {
                    this.affiliateNumber = affiliateNumber;
                }

                public String getUserType() {
                    return userType;
                }

                public void setUserType(String userType) {
                    this.userType = userType;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getCountryName() {
                    return countryName;
                }

                public void setCountryName(String countryName) {
                    this.countryName = countryName;
                }

                public String getTouchStatus() {
                    return touchStatus;
                }

                public void setTouchStatus(String touchStatus) {
                    this.touchStatus = touchStatus;
                }

                public String getMemberImage() {
                    return memberImage;
                }

                public void setMemberImage(String memberImage) {
                    this.memberImage = memberImage;
                }

                public String getCountryId() {
                    return countryId;
                }

                public void setCountryId(String countryId) {
                    this.countryId = countryId;
                }

                public String getDeviceToken() {
                    return deviceToken;
                }

                public void setDeviceToken(String deviceToken) {
                    this.deviceToken = deviceToken;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getAge() {
                    return age;
                }

                public void setAge(String age) {
                    this.age = age;
                }

                public String getSocialId() {
                    return socialId;
                }

                public void setSocialId(String socialId) {
                    this.socialId = socialId;
                }

            }

        }



    }


}







