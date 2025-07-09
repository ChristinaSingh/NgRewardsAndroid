package main.com.ngrewards.beanclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by technorizen on 15/10/18.
 */

public class NotificationBeanNew {
    String message;
    String chat_id;
    String image;
    String statuss;
    String msg_type;
    String chat_video;
    String video_thumb_img;
    String id;
    String senderid;
    String reciverid;
    String datetime;
    String time;
    String senderimg;
    String reciverimg;
    String sendername;
    String recname;
    String sender_online_status;
    String userimg;
    String username;
    String chat_image;
    String receiver_type;
    String notification_type;
    String user_id;
    String invite_user_id;
    String message_key;
    String status;
    String created_date;
    String type;
    String fullname;
    String timeago;
    String unseen_count;
    String business_name;
    String amount;
    private String chatMesssage;
    private String data;

    @SerializedName("transfer_request_data")
    @Expose
    private List<TransferRequestDatum> transferRequestData;

    public List<TransferRequestDatum> getTransferRequestData() {
        return transferRequestData;
    }

    public void setTransferRequestData(List<TransferRequestDatum> transferRequestData) {
        this.transferRequestData = transferRequestData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getUnseen_count() {
        return unseen_count;
    }

    public void setUnseen_count(String unseen_count) {
        this.unseen_count = unseen_count;
    }

    public String getTimeago() {
        return timeago;
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInvite_user_id() {
        return invite_user_id;
    }

    public void setInvite_user_id(String invite_user_id) {
        this.invite_user_id = invite_user_id;
    }

    public String getMessage_key() {
        return message_key;
    }

    public void setMessage_key(String message_key) {
        this.message_key = message_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getVideo_thumb_img() {
        return video_thumb_img;
    }

    public void setVideo_thumb_img(String video_thumb_img) {
        this.video_thumb_img = video_thumb_img;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getChat_video() {
        return chat_video;
    }

    public void setChat_video(String chat_video) {
        this.chat_video = chat_video;
    }


    public String getReceiver_type() {
        return receiver_type;
    }

    public void setReceiver_type(String receiver_type) {
        this.receiver_type = receiver_type;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public String getReciverid() {
        return reciverid;
    }

    public void setReciverid(String reciverid) {
        this.reciverid = reciverid;
    }

    public String getSender_online_status() {
        return sender_online_status;
    }

    public void setSender_online_status(String sender_online_status) {
        this.sender_online_status = sender_online_status;
    }

    public String getSenderimg() {
        return senderimg;
    }

    public void setSenderimg(String senderimg) {
        this.senderimg = senderimg;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReciverimg() {
        return reciverimg;
    }

    public void setReciverimg(String reciverimg) {
        this.reciverimg = reciverimg;
    }

    public String getRecname() {
        return recname;
    }

    public void setRecname(String recname) {
        this.recname = recname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getChatMesssage() {
        return chatMesssage;
    }

    public void setChatMesssage(String chatMesssage) {

        this.chatMesssage = chatMesssage;
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
}







