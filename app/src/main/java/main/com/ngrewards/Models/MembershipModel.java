package main.com.ngrewards.Models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;






public class MembershipModel {

    @SerializedName("membership_data")
    @Expose
    private MembershipData membershipData;
    @SerializedName("membership_data_history")
    @Expose
    private List<MembershipDataHistory> membershipDataHistory;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result;

    public MembershipData getMembershipData() {
        return membershipData;
    }

    public void setMembershipData(MembershipData membershipData) {
        this.membershipData = membershipData;
    }

    public List<MembershipDataHistory> getMembershipDataHistory() {
        return membershipDataHistory;
    }

    public void setMembershipDataHistory(List<MembershipDataHistory> membershipDataHistory) {
        this.membershipDataHistory = membershipDataHistory;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class MembershipData {

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
        private Object amount;
        @SerializedName("receipt_url")
        @Expose
        private Object receiptUrl;
        @SerializedName("duration_name")
        @Expose
        private String durationName;
        @SerializedName("plan_name")
        @Expose
        private String planName;

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

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public Object getReceiptUrl() {
            return receiptUrl;
        }

        public void setReceiptUrl(Object receiptUrl) {
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
        private Object amount;
        @SerializedName("receipt_url")
        @Expose
        private Object receiptUrl;
        @SerializedName("duration_name")
        @Expose
        private String durationName;
        @SerializedName("plan_name")
        @Expose
        private String planName;

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

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public Object getReceiptUrl() {
            return receiptUrl;
        }

        public void setReceiptUrl(Object receiptUrl) {
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





    public class Result {

        @SerializedName("plan_id")
        @Expose
        private String planId;
        @SerializedName("plan_name")
        @Expose
        private String planName;
        @SerializedName("plan_created_at")
        @Expose
        private String planCreatedAt;
        @SerializedName("plan_updated_at")
        @Expose
        private String planUpdatedAt;
        @SerializedName("plan_deleted_at")
        @Expose
        private Object planDeletedAt;
        @SerializedName("duration_id")
        @Expose
        private String durationId;
        @SerializedName("duration_type")
        @Expose
        private String durationType;
        @SerializedName("duration_in_days")
        @Expose
        private String durationInDays;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("features")
        @Expose
        private List<String> features;
        @SerializedName("duration_created_at")
        @Expose
        private String durationCreatedAt;
        @SerializedName("duration_updated_at")
        @Expose
        private String durationUpdatedAt;
        @SerializedName("duration_deleted_at")
        @Expose
        private Object durationDeletedAt;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlanCreatedAt() {
            return planCreatedAt;
        }

        public void setPlanCreatedAt(String planCreatedAt) {
            this.planCreatedAt = planCreatedAt;
        }

        public String getPlanUpdatedAt() {
            return planUpdatedAt;
        }

        public void setPlanUpdatedAt(String planUpdatedAt) {
            this.planUpdatedAt = planUpdatedAt;
        }

        public Object getPlanDeletedAt() {
            return planDeletedAt;
        }

        public void setPlanDeletedAt(Object planDeletedAt) {
            this.planDeletedAt = planDeletedAt;
        }

        public String getDurationId() {
            return durationId;
        }

        public void setDurationId(String durationId) {
            this.durationId = durationId;
        }

        public String getDurationType() {
            return durationType;
        }

        public void setDurationType(String durationType) {
            this.durationType = durationType;
        }

        public String getDurationInDays() {
            return durationInDays;
        }

        public void setDurationInDays(String durationInDays) {
            this.durationInDays = durationInDays;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getFeatures() {
            return features;
        }

        public void setFeatures(List<String> features) {
            this.features = features;
        }

        public String getDurationCreatedAt() {
            return durationCreatedAt;
        }

        public void setDurationCreatedAt(String durationCreatedAt) {
            this.durationCreatedAt = durationCreatedAt;
        }

        public String getDurationUpdatedAt() {
            return durationUpdatedAt;
        }

        public void setDurationUpdatedAt(String durationUpdatedAt) {
            this.durationUpdatedAt = durationUpdatedAt;
        }

        public Object getDurationDeletedAt() {
            return durationDeletedAt;
        }

        public void setDurationDeletedAt(Object durationDeletedAt) {
            this.durationDeletedAt = durationDeletedAt;
        }

    }

}



