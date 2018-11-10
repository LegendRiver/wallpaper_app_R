package eli.tech.wallri.Activity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;

import eli.tech.wallri.R;

/**
 * 这个类是失败页面
 */
public class FinishFailActivity extends AppCompatActivity {

    private RelativeLayout mFailFinishActivityBackImage;
    private Button mFailFinish_button;
    private SimpleDraweeView mFailFinishImage;
    private Typeface tf;
    private TextView mFailFinishActivityTextView;
    private TextView mFailSigntext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_fail);
        //隐藏ActionBar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        mFailFinishActivityBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFailFinish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {

        //设置字体
        //得到AssetManager
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        tf = Typeface.createFromAsset(mgr, "fonts/roboto_regular.ttf");
        mFailFinishActivityBackImage = findViewById(R.id.FailFinishActivityBackImage);
        mFailFinish_button = findViewById(R.id.FailFinish_button);
        mFailFinish_button.setTypeface(tf);
        mFailFinishImage = findViewById(R.id.FailFinishImage);
        mFailFinishActivityTextView = findViewById(R.id.FailFinishActivityTextView);
        mFailFinishActivityTextView.setTypeface(tf);
        mFailSigntext = findViewById(R.id.FailSigntext);
        mFailSigntext.setTypeface(tf);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.finish_image);
        mFailFinishImage.startAnimation(animation);
        mFailFinishImage.setImageURI("res://com.example.addemo/" + R.drawable.finish_no);
    }
}
