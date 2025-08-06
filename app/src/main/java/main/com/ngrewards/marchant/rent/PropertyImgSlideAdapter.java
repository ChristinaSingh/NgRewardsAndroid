package main.com.ngrewards.marchant.rent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.constant.BaseUrl;

public class PropertyImgSlideAdapter extends PagerAdapter {

    private final Context mContext;
    private final List<PropertyListModel.Datum.File> productImages;

        public PropertyImgSlideAdapter(Context context, List<PropertyListModel.Datum.File> productImages) {
        mContext = context;
        this.productImages = productImages;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_property_img_slider, collection,
                false);
        collection.addView(itemView);
        ImageView productimg = itemView.findViewById(R.id.productimg);
        String image_url = productImages.get(position).getFileUrl();
        if (image_url != null && !image_url.equalsIgnoreCase("") && !image_url.equalsIgnoreCase(BaseUrl.image_baseurl)) {
            Glide.with(mContext).load(image_url).placeholder(R.drawable.placeholder).into(productimg);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = new Intent(FragItemDetails.this, FullScreenImagesActivity.class);
                i.putExtra("position", position);
                i.putExtra("status", "");
                startActivity(i);*/
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return productImages == null ? 0 : productImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}

