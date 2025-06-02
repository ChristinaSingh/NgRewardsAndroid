package main.com.ngrewards.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PlanHistoryModel {


    @SerializedName("membership_data_history")
    @Expose
    private List<MembershipDataHistory> membershipDataHistory;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;



    public List<MembershipDataHistory> getMembershipDataHistory() {
        return membershipDataHistory;
    }

    public void setMembershipDataHistory(List<MembershipDataHistory> membershipDataHistory) {
        this.membershipDataHistory = membershipDataHistory;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("affiliate_name")
        @Expose
        private String affiliateName;
        @SerializedName("how_invited_you")
        @Expose
        private String howInvitedYou;
        @SerializedName("invited_user_name")
        @Expose
        private String invitedUserName;
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
        @SerializedName("currency_sign")
        @Expose
        private String currencySign;
        @SerializedName("currency_code")
        @Expose
        private String currencyCode;
        @SerializedName("member_stripe_account_id")
        @Expose
        private String memberStripeAccountId;
        @SerializedName("stripe_account_login_link")
        @Expose
        private String stripeAccountLoginLink;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("state")
        @Expose
        private Object state;
        @SerializedName("city")
        @Expose
        private Object city;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("unseen_count")
        @Expose
        private Integer unseenCount;
        @SerializedName("share_link")
        @Expose
        private String shareLink;
       /* @SerializedName("member_ngcash")
        @Expose
        private Integer memberNgcash;*/

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getInvitedUserName() {
            return invitedUserName;
        }

        public void setInvitedUserName(String invitedUserName) {
            this.invitedUserName = invitedUserName;
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

        public String getCurrencySign() {
            return currencySign;
        }

        public void setCurrencySign(String currencySign) {
            this.currencySign = currencySign;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getMemberStripeAccountId() {
            return memberStripeAccountId;
        }

        public void setMemberStripeAccountId(String memberStripeAccountId) {
            this.memberStripeAccountId = memberStripeAccountId;
        }

        public String getStripeAccountLoginLink() {
            return stripeAccountLoginLink;
        }

        public void setStripeAccountLoginLink(String stripeAccountLoginLink) {
            this.stripeAccountLoginLink = stripeAccountLoginLink;
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

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public Integer getUnseenCount() {
            return unseenCount;
        }

        public void setUnseenCount(Integer unseenCount) {
            this.unseenCount = unseenCount;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

      /*  public Integer getMemberNgcash() {
            return memberNgcash;
        }

        public void setMemberNgcash(Integer memberNgcash) {
            this.memberNgcash = memberNgcash;
        }*/

    }


    public class MembershipDataHistory {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("membership_duration_id")
        @Expose
        private String membershipDurationId;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("receipt_url")
        @Expose
        private String receiptUrl;
        @SerializedName("duration_name")
        @Expose
        private String durationName;
        @SerializedName("plan_name")
        @Expose
        private String planName;

        @SerializedName("features")
        @Expose
        private List<String> features;

        public List<String> getFeatures() {
            return features;
        }

        public void setFeatures(List<String> features) {
            this.features = features;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMembershipDurationId() {
            return membershipDurationId;
        }

        public void setMembershipDurationId(String membershipDurationId) {
            this.membershipDurationId = membershipDurationId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getReceiptUrl() {
            return receiptUrl;
        }

        public void setReceiptUrl(String receiptUrl) {
            this.receiptUrl = receiptUrl;
        }

        public String getDurationName() {
            return durationName;
        }

        public void setDurationName(String durationName) {
            this.durationName = durationName;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

    }

}

