package com.hyunsungkr.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    ImageView imgAlarm;
    TextView txtTimer;
    EditText editTimer;
    Button btnCancel;
    Button btnStart;

    CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAlarm = findViewById(R.id.imgAlarm);
        txtTimer = findViewById(R.id.txtTimer);
        editTimer = findViewById(R.id.editTimer);
        btnCancel = findViewById(R.id.btnCancle);
        btnStart = findViewById(R.id.btnStart);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 스타트 버튼을 눌렀을 때 실행하고싶은 코드를 아래에 작성
                // 1. editText에 적힌 시간을 가져온다.
                // 1-1. 아무것도 안 적혀있을 때 처리
                String strTime= editTimer.getText().toString().trim();

                if (strTime.isEmpty()){
                    Toast.makeText(getApplicationContext(), "타이머 시간을 먼저 셋팅해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                // 1-2 문자열로 되어있는 초를 Long으로 바꿔준다.
                Long time=Long.valueOf(strTime).longValue();
                // 60을 입력했다면, => 60000
                time = time * 1000;

                // 2. 적힌 시간에 맞는 타이머를 만든다.
                timer = new CountDownTimer(time,1000) {
                    @Override
                    public void onTick(long l) {
                        // 화면에 표시!
                        long remain = l / 1000;
                        txtTimer.setText(remain+"");
                    }

                    @Override
                    public void onFinish() {

                        // 1. 애니메이션 효과
                        YoYo.with(Techniques.Shake).duration(400).repeat(4).playOn(imgAlarm);

                        // 2. 알람음 처리

                    }
                };

                // 3. 타이머를 시작한다.
                timer.start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //0. timer가 null인지 확인
                if(timer==null){
                    return;
                }


                //1. 타이머 취소
                timer.cancel();

                //2. 남은 초 화면을 유저가 셋팅한 값으로 보여준다.
                // editText에 셋팅한 값으로 보여준다.
                String strTime= editTimer.getText().toString().trim();

                if(strTime.isEmpty()){
                    txtTimer.setText("남은 시간");
                }else {

                    txtTimer.setText(strTime);
                }

            }
        });

    }
}