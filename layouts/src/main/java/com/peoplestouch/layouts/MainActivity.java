package com.peoplestouch.layouts;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.peoplestouch.layouts.databinding.DemoLayBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DemoLayBinding
        //Demolay binding =


        DemoLayBinding bind= DataBindingUtil.setContentView(this, R.layout.demo_lay);
        final MyDTO dto = new MyDTO();
        dto.setLable("Testing");
        Handler h= new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                dto.setLable("Testing123");
            }
        };
        h.postDelayed(r,3000);
        bind.setDto(dto);
        bind.setTrigger(new MyT(dto));
    }

    public class MyT extends BaseTrigger implements MyTrigger
    {
        MyDTO dto;
        public MyT(MyDTO dto)
        {
            this.dto=dto;
        }
        @Override
        public void onIconClick(View view) {
            Toast.makeText(MainActivity.this, dto.getLable(), Toast.LENGTH_SHORT).show();
        }
    }
}
