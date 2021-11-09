package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView profileRecView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        profileRecView=findViewById(R.id.profileRecView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);
        getProfiles(layoutManager);


    }
    private void getProfiles(LinearLayoutManager layoutManager) {
        Call<List<PostList>> call = RetrofitClient.getInstance().getMyApi().getProfiles();
        call.enqueue(new Callback<List<PostList>>() {
            @Override
            public void onResponse(Call<List<PostList>> call, Response<List<PostList>> response) {
                List<PostList> myHeroList = response.body();
                ArrayList<Profile> profiles = new ArrayList<>();

                for (int i = 0; i < myHeroList.size(); i++) {
                    Profile pf=new Profile(myHeroList.get(i).getUser().getName(),myHeroList.get(i).getUser().getProfileImage().getSmall());
                    profiles.add(pf);
                }
                ProfileRecViewAdapter adapter=new ProfileRecViewAdapter();
                adapter.setProfiles(profiles);
                profileRecView.setAdapter(adapter);
                profileRecView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<PostList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }

        });
    }
}