package com.dosssik.andrey.savemyeye.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.dosssik.andrey.savemyeye.R;
import com.dosssik.andrey.savemyeye.wiget.CustomTimerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dosssik on 11/4/16.
 */

public class TimerFragment extends Fragment implements ViewSwitcher.ViewFactory {

    @BindView(R.id.timer_activity_timer_view)
    CustomTimerView timerView;
    @BindView(R.id.timer_activity_start_button)
    Button startButton;
    @BindView(R.id.timer_activity_stop_button)
    Button stopButton;
    @BindView(R.id.timer_activity_hour_text_switcher)
    TextSwitcher hourTextSwitcher;
    @BindView(R.id.timer_activity_minutes_text_switcher)
    TextSwitcher minutesTextSwitcher;
    @BindView(R.id.timer_activity_seconds_text_switcher)
    TextSwitcher secondsTextSwitcher;
    @BindView(R.id.timer_activity_hour_minutes_separator)
    TextView hourMinutesSeparator;
    @BindView(R.id.timer_activity_minutes_seconds_separator)
    TextView minutesSecondsSeparator;

    // TODO -----------Debug controls-----------------------

    @BindView(R.id.timer_activity_minutes_increment_button)
    Button minIncrement;
    @BindView(R.id.timer_activity_seconds_increment_button)
    Button secIncrement;

    int minutes = 0;
    int seconds = 0;

    private Context context;

    public static TimerFragment newInstance() {

        Bundle args = new Bundle();

        TimerFragment fragment = new TimerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);

        context = getContext();

        ButterKnife.bind(this, v);
        startButton.setOnClickListener(click -> timerView.startCustomAnim(10));
        stopButton.setOnClickListener(click -> timerView.stopCustomAnim());

        minutesTextSwitcher.setFactory(this);
        secondsTextSwitcher.setFactory(this);

        minutesTextSwitcher.setInAnimation(context, R.anim.slide_in_top);
        minutesTextSwitcher.setOutAnimation(context, R.anim.slide_out_bottom);
        secondsTextSwitcher.setInAnimation(context, R.anim.slide_in_top);
        secondsTextSwitcher.setOutAnimation(context, R.anim.slide_out_bottom);

        minutesTextSwitcher.setText("00");
        secondsTextSwitcher.setText("00");

        minIncrement.setOnClickListener(click -> minIncr());
        secIncrement.setOnClickListener(click -> secIncr());

        return v;
    }


    private void secIncr() {
        seconds++;
        secondsTextSwitcher.setText("0" + seconds);
    }

    private void minIncr() {
        minutes++;
        minutesTextSwitcher.setText("0" + minutes);
    }

    @Override
    public void onStop() {
        timerView.stopCustomAnim();
        super.onStop();

    }

    @Override
    public View makeView() {
        TextView textView = new TextView(context);
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        textView.setTextSize(60);
        return textView;
    }
}
