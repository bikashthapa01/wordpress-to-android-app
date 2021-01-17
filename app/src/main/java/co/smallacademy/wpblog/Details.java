package co.smallacademy.wpblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        getSupportActionBar().setTitle(title);

        WebView contentView = findViewById(R.id.contentView);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        contentView.loadDataWithBaseURL("",content,mimeType,encoding,"");
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Toast.



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}