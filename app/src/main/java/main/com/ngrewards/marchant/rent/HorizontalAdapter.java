package main.com.ngrewards.marchant.rent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import main.com.ngrewards.R;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
    Context context;
    private  ArrayList<PropertyModel.Data.File> ImagePathArrayList_adp;
    private ArrayList<Bitmap> horizontalList;
    onPhotoClickListener listener;
    public HorizontalAdapter( Context context,ArrayList<PropertyModel.Data.File> ImagePathArrayList_adp,onPhotoClickListener listener) {
        this.horizontalList = horizontalList;
        this.context = context;
        this.ImagePathArrayList_adp = ImagePathArrayList_adp;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (ImagePathArrayList_adp.get(position) != null) {
            if (Build.VERSION.SDK_INT >= 33) {

                Log.e("TAG", "onBindViewHolder: ---------- " + ImagePathArrayList_adp.get(position));
                //  File tempfile = Tools.persistImage(bitmapImage, getApplicationContext());
                //  ppath = tempfile.getAbsolutePath();
                //   holder.ProductImageImagevies.setImageURI(Uri.fromFile(Tools.persistImage()));
               // holder.ProductImageImagevies.setImageURI(Uri.fromFile(new File(ImagePathArrayList_adp.get(position).getFileUrl())));
                Glide.with(context).load(ImagePathArrayList_adp.get(position).getFileUrl()).placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).into(holder.ProductImageImagevies);
            } else {
                //holder.ProductImageImagevies.setImageURI(Uri.fromFile(new File(ImagePathArrayList_adp.get(position).getFileUrl())));
                Glide.with(context).load(ImagePathArrayList_adp.get(position).getFileUrl()).placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).into(holder.ProductImageImagevies);
            }

        }
        holder.removeimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImagePathArrayList_adp != null && !ImagePathArrayList_adp.isEmpty()) {
                   // ImagePathArrayList_adp.remove(position);
                   // notifyDataSetChanged();
                   /* if (ImagePathArrayList_adp == null || ImagePathArrayList_adp.isEmpty()) {
                        binding.addProductList.setVisibility(View.GONE);
                    }*/
                    listener.onPhoto(position,"remove",ImagePathArrayList_adp.get(position));
                }

            }
        });


        holder.ProductImageImagevies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImagePathArrayList_adp != null && !ImagePathArrayList_adp.isEmpty()) {
                    listener.onPhoto(position,"edit",ImagePathArrayList_adp.get(position));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return ImagePathArrayList_adp == null ? 0 : ImagePathArrayList_adp.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ProductImageImagevies, removeimages;
        //   RelativeLayout RLRemovePhoto;

        public MyViewHolder(View view) {
            super(view);

            ProductImageImagevies = (ImageView) view.findViewById(R.id.productimage);
            removeimages = (ImageView) view.findViewById(R.id.removeimages);
            //    RLRemovePhoto = (RelativeLayout) view.findViewById(R.id.RLRemovePhoto);

        }
    }
}

