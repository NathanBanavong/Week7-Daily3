package com.example.consultants.cmake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private TextView tvMain;
    private TextView tvSum;
    private EditText etNum2;
    private EditText etNum1;
    int num1;
    int num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        onBindView();
        tvMain.setText(stringFromJNI());
//        int Sum = showFROMNJI(num1, num2);
//        tvSum.setText(String.valueOf(Sum));
        tvSum.setText(String.valueOf(showFROMNJI(num1, num2)));
    }

    public void onBindView() {
        tvMain = findViewById(R.id.tvMain);
        tvSum = findViewById(R.id.tvSum);
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);

        num1 = Integer.parseInt(etNum1.getText().toString());
        num2 = Integer.parseInt(etNum2.getText().toString());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native int showFROMNJI(int a, int b);
}
