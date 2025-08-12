package main.com.ngrewards.marchant.rent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class PropertyModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("merchant_id")
        @Expose
        private String merchantId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("unit_number")
        @Expose
        private String unitNumber;
        @SerializedName("floor_level")
        @Expose
        private String floorLevel;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("square_footage")
        @Expose
        private String squareFootage;
        @SerializedName("availability_date")
        @Expose
        private String availabilityDate;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("amenities")
        @Expose
        private List<Amenity> amenities;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("files")
        @Expose
        private List<File> files;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUnitNumber() {
            return unitNumber;
        }

        public void setUnitNumber(String unitNumber) {
            this.unitNumber = unitNumber;
        }

        public String getFloorLevel() {
            return floorLevel;
        }

        public void setFloorLevel(String floorLevel) {
            this.floorLevel = floorLevel;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSquareFootage() {
            return squareFootage;
        }

        public void setSquareFootage(String squareFootage) {
            this.squareFootage = squareFootage;
        }

        public String getAvailabilityDate() {
            return availabilityDate;
        }

        public void setAvailabilityDate(String availabilityDate) {
            this.availabilityDate = availabilityDate;
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

        public List<Amenity> getAmenities() {
            return amenities;
        }

        public void setAmenities(List<Amenity> amenities) {
            this.amenities = amenities;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<File> getFiles() {
            return files;
        }

        public void setFiles(List<File> files) {
            this.files = files;
        }



        public class Amenity implements Serializable{

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("icon")
            @Expose
            private String icon;
            @SerializedName("image")
            @Expose
            private String image;

            private boolean chk=true;

            public boolean isChk() {
                return chk;
            }

            public void setChk(boolean chk) {
                this.chk = chk;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

        }

        public class File implements Serializable {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("file_path")
            @Expose
            private String filePath;
            @SerializedName("file_type")
            @Expose
            private String fileType;
            @SerializedName("uploaded_at")
            @Expose
            private String uploadedAt;
            @SerializedName("file_url")
            @Expose
            private String fileUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            public String getUploadedAt() {
                return uploadedAt;
            }

            public void setUploadedAt(String uploadedAt) {
                this.uploadedAt = uploadedAt;
            }

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }

        }

    }

}