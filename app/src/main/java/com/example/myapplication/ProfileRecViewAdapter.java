package com.example.myapplication;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileRecViewAdapter extends RecyclerView.Adapter<ProfileRecViewAdapter.ViewHolder> {

    private ArrayList<Profile> profiles=new ArrayList<>();
    ImageCache cache = ImageCache.getInstance();
    public ProfileRecViewAdapter() {

        cache.initializeCache();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item,parent,false );
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                holder.profileName.setText(profiles.get(position).getName());
                String img = profiles.get(position).getImg();

                Bitmap bm =cache.getImageFromWarehouse(img);

        if(bm != null)
        {
            holder.profilePic.setImageBitmap(bm);
        }
        else
        {
            holder.profilePic.setImageBitmap(null);

            DownloadImageTask imgTask = new DownloadImageTask(this, 300, 300);//Since you are using it from `Activity` call second Constructor.

            imgTask.execute(img);
        }

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private TextView profileName;
        private CardView parent;
        private ImageView profilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileName=itemView.findViewById(R.id.profileId);
            parent=itemView.findViewById(R.id.parent);
            profilePic=itemView.findViewById(R.id.profilePic);

        }
    }
}
