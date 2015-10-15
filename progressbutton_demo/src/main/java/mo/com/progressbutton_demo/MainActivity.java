package mo.com.progressbutton_demo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressButton mPg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPg = (ProgressButton) findViewById(R.id.pb_demo);
        mPg.setOnClickListener(this);
        //设置可以更新
        mPg.setIsEnableProgress(true);
    }
    /**
     * 点击开启进度
     * @param v
     */
    @Override
    public void onClick(View v) {
        startProcess();
    }

    private void startProcess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    SystemClock.sleep(200);

                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        /*主线程中更新进度*/
                        @Override
                        public void run() {
                            mPg.setProgress(finalI);
                        }
                    });
                }
            }
        }).start();
    }


}
