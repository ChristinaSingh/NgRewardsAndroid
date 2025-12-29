package main.com.ngrewards.beanclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import main.com.ngrewards.Models.PropertyListModel;

/**
 * Created by technorizen on 19/7/18.
 */

public class CategoryBeanList {
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_name_spanish")
    @Expose
    private String category_name_spanish;
    @SerializedName("category_name_hindi")
    @Expose
    private String category_name_hindi;

    @SerializedName("sub_catagories_count")
    @Expose
    private Integer subCatagoriesCount;
    @SerializedName("product_list_count")
    @Expose
    private Integer productListCount;

    @SerializedName("sub_categories")
    @Expose
    private List<SubCategories> subCategories;

    public String getCategory_name_spanish() {
        return category_name_spanish;
    }

    public void setCategory_name_spanish(String category_name_spanish) {
        this.category_name_spanish = category_name_spanish;
    }

    public String getCategory_name_hindi() {
        return category_name_hindi;
    }

    public void setCategory_name_hindi(String category_name_hindi) {
        this.category_name_hindi = category_name_hindi;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSubCatagoriesCount() {
        return subCatagoriesCount;
    }

    public void setSubCatagoriesCount(Integer subCatagoriesCount) {
        this.subCatagoriesCount = subCatagoriesCount;
    }

    public Integer getProductListCount() {
        return productListCount;
    }

    public List<SubCategories> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategories> subCategories) {
        this.subCategories = subCategories;
    }

    public void setProductListCount(Integer productListCount) {
        this.productListCount = productListCount;




    }



    public class SubCategories implements Serializable {
        @SerializedName("subcategory_id")
        @Expose
        private Integer subcategoryId;
        @SerializedName("subcategory_name")
        @Expose
        private String subcategoryName;
        @SerializedName("product_count")
        @Expose
        private Integer productCount;

        public Integer getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(Integer subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public String getSubcategoryName() {
            return subcategoryName;
        }

        public void setSubcategoryName(String subcategoryName) {
            this.subcategoryName = subcategoryName;
        }

        public Integer getProductCount() {
            return productCount;
        }

        public void setProductCount(Integer productCount) {
            this.productCount = productCount;
        }
    }

}
