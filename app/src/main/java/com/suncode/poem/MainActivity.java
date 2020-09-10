package com.suncode.poem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.suncode.poem.adapter.PoemAdapter;
import com.suncode.poem.api.ApiClient;
import com.suncode.poem.api.ApiService;
import com.suncode.poem.model.Poem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService mApiService;
    private RecyclerView mPoemRecycleview;
    private PoemAdapter mAdapter;
    private TextView mTitleTextview;
    private ShimmerFrameLayout mLoadingShimmer;

    public static final String TAG = "CHECKTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        getSupportActionBar().hide();
        //transparent status/notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //change color text in status/notification bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mApiService = ApiClient.builder().create(ApiService.class);
        mPoemRecycleview = findViewById(R.id.recycleview_poem);
        mTitleTextview = findViewById(R.id.textView_title);
        mLoadingShimmer = findViewById(R.id.shimmer_poem);
        showShimmer(true);

        mTitleTextview.setOnClickListener(v -> {
            showShimmer(true);
            mAdapter.clear();
            getData();
        });

        getData();
    }

    private void showShimmer(boolean b) {
        if (b) {
            mLoadingShimmer.startShimmer();
            mLoadingShimmer.showShimmer(true);
            mLoadingShimmer.setVisibility(View.VISIBLE);
        } else {
            mLoadingShimmer.stopShimmer();
            mLoadingShimmer.showShimmer(false);
            mLoadingShimmer.setVisibility(View.GONE);
        }
    }

    private void getData() {
        Call<List<Poem>> poemCall = mApiService.getPoem();

        poemCall.enqueue(new Callback<List<Poem>>() {
            @Override
            public void onResponse(Call<List<Poem>> call, Response<List<Poem>> response) {

                if (response.code() != 200) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    showShimmer(false);
                    return;
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };

                mPoemRecycleview.setLayoutManager(layoutManager);

                DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation());
                mPoemRecycleview.addItemDecoration(itemDecoration);

                mAdapter = new PoemAdapter(MainActivity.this, response.body(), position -> {
                    Poem poem = new Poem(
                            response.body().get(position).getTitle(),
                            response.body().get(position).getContent(),
                            response.body().get(position).getUrl(),
                            response.body().get(position).getPoet()
                    );

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("POEM", poem);
                    startActivity(intent);

                });

                mPoemRecycleview.setAdapter(mAdapter);

                showShimmer(false);
            }

            @Override
            public void onFailure(Call<List<Poem>> call, Throwable t) {

            }
        });
    }
}