package kaihcheng7_kmlee65.scm.evolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import kaihcheng7_kmlee65.scm.evolution.modelgame.GamingMainActivity;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.TrainingMainActivity;


/**
 * Created by theoska on 4/25/17.
 */

public class SelectModelActivity extends AppCompatActivity {
    LinearLayout llTraining;
    LinearLayout llChallenge;
    LinearLayout llLeave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_model);
        llTraining = (LinearLayout) findViewById(R.id.ll_traning);
        llChallenge = (LinearLayout) findViewById(R.id.ll_challenge);
        llLeave = (LinearLayout) findViewById(R.id.ll_leave);

        llTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectModelActivity.this, TrainingMainActivity.class));
            }
        });
        llChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectModelActivity.this, GamingMainActivity.class));

            }
        });
        llLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
