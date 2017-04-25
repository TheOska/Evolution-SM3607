package kaihcheng7_kmlee65.scm.evolution.modelgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

import kaihcheng7_kmlee65.scm.evolution.R;
import kaihcheng7_kmlee65.scm.evolution.modelgame.utils.SampleSlide;


public class IntroActivity extends AppIntro {
    private static final String title = "How to Play";
    private static final String desc = "Click two elements to create new, and defeat the enemy!";
//    private static final int image = R.drawable.gameintro;
    private static final int image = R.drawable.guide;
    private static int backgroundColor;
    private static int backgroundColor2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundColor = Color.parseColor("#64b891");
        backgroundColor2 = Color.parseColor("#1d7eb6");

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.
//        addSlide(first_fragment);
//        addSlide(second_fragment);
//        addSlide(third_fragment);
//        addSlide(fourth_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest
//        addSlide(AppIntroFragment.newInstance(title, desc, image, backgroundColor));
//        addSlide(AppIntro2Fragment.newInstance("Another page", "another desc", image, backgroundColor2));
        addSlide(SampleSlide.newInstance(R.layout.intro_fragment1));
        addSlide(SampleSlide.newInstance(R.layout.intro_fragment2));
        addSlide(SampleSlide.newInstance(R.layout.intro_fragment3));

        // OPTIONAL METHODS

        // Override bar/separator color
        setBarColor(Color.parseColor("#ff6f69"));
        setSeparatorColor(Color.parseColor("#ff6f69"));

        // SHOW or HIDE the statusbar
        showStatusBar(true);

        // Edit the color of the nav bar on Lollipop+ devices
//        setNavBarColor(Color.parseColor("#3F51B5"));

        // Hide Skip/Done button
        showSkipButton(true);
        showDoneButton(true);

        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest
        setVibrate(true);
        setVibrateIntensity(30);

        // Animations -- use only one of the below. Using both could cause errors.
//        setFadeAnimation(); // OR
//        setZoomAnimation(); // OR
//        setFlowAnimation(); // OR
//        setSlideOverAnimation(); // OR
        setDepthAnimation(); // OR
//        setCustomTransformer(yourCustomTransformer);

        // Permissions -- takes a permission and slide number
//        askForPermissions(new String[]{Manifest.permission.CAMERA}, 3);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
