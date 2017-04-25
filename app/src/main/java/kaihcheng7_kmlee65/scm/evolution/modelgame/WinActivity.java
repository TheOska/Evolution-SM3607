package kaihcheng7_kmlee65.scm.evolution.modelgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kaihcheng7_kmlee65.scm.evolution.R;


/**
 * Created by TheOskaCKH on 11/19/2016.
 */

public class WinActivity extends AppCompatActivity {
    Button btnRestart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        btnRestart = (Button) findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinActivity.this, GamingMainActivity.class));
            }
        });
    }
}
