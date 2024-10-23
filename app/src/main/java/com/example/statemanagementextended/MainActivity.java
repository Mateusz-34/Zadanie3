package com.example.statemanagementextended;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private CountViewModel countViewModel;
    private TextView textViewCount;
    private EditText editText;
    private CheckBox checkBox;
    private Switch themeSwitch;
    private TextView checkBoxChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCount = findViewById(R.id.textViewCount);
        editText = findViewById(R.id.editText);
        checkBox = findViewById(R.id.checkBox);
        themeSwitch  = findViewById(R.id.themeSwitch);
        checkBoxChecked  = findViewById(R.id.checkBoxChecked);

        Button buttonIncrement = findViewById(R.id.buttonIncrement);

        countViewModel = new ViewModelProvider(this).get(CountViewModel.class); updateCountText();

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countViewModel.incrementCount();
                updateCountText();
            }
        });

        if (savedInstanceState != null) {
            textViewCount.setText(savedInstanceState.getString("counter", "Licznik: 0"));
            editText.setText(savedInstanceState.getString("editText", ""));
            checkBox.setChecked(savedInstanceState.getBoolean("checkBox", false));
            themeSwitch.setChecked(savedInstanceState.getBoolean("themeSwitch", false));

            checkBoxChecked.setVisibility(checkBox.isChecked() ? View.VISIBLE : View.GONE);
        }

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkBoxChecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setTheme(isChecked);
        });
    }

    private void setTheme(boolean darkMode) {
        if (darkMode) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void updateCountText() {
        textViewCount.setText("Licznik: " + countViewModel.getCount());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counter", textViewCount.getText().toString());
        outState.putString("editText", editText.getText().toString());
        outState.putBoolean("checkBox", checkBox.isChecked());
        outState.putBoolean("themeSwitch", themeSwitch.isChecked());
    }
}