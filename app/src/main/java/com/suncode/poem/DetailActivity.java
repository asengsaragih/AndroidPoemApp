package com.suncode.poem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.suncode.poem.model.Poem;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "CHECKTAG";

    private TextView mTitleTextview;
    private TextView mContentTextview;
    private TextView mAuthorTextview;
    private ImageView mBackImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //hide action bar
        getSupportActionBar().hide();
        //transparent status/notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //change color text in status/notification bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mTitleTextview = findViewById(R.id.textView_detail_title);
        mContentTextview = findViewById(R.id.textView_detail_content);
        mAuthorTextview = findViewById(R.id.textView_detail_author);

        //set back button
        mBackImageview = findViewById(R.id.imageView_detail_back);
        mBackImageview.setOnClickListener(v -> finish());

        showPoem(poemData());
    }

    private void showPoem(Poem poem) {
        String urlPoem = poem.getUrl();
        String urlAuthor = poem.getPoet().getUrl();

        mTitleTextview.setText(poem.getTitle());
        mContentTextview.setText(poem.getContent());
        mAuthorTextview.setText(poem.getPoet().getName());

        mTitleTextview.setOnClickListener(v -> intentBrowser(urlPoem));
        mAuthorTextview.setOnClickListener(v -> intentBrowser(urlAuthor));
    }

    private Poem poemData() {
        Poem poem = (Poem) getIntent().getSerializableExtra("POEM");
        return poem;
    }

    private void intentBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}