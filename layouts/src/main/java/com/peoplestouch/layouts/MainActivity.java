package com.peoplestouch.layouts;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Toast;

import com.peoplestouch.layouts.databinding.DemoLayBinding;

public class MainActivity extends AppCompatActivity implements android.databinding.DataBindingComponent,PostTrigger
{
    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public MainActivity getMainActivity(){
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DemoLayBinding
        //Demolay binding =

        DataBindingUtil.setDefaultComponent(this);
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
    @Override
    public void share(MyDTO dto) {
        Toast.makeText(MainActivity.this, dto.getLable(), Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter("onClickAction")
    public void onClickAction(MainActivity activity,View view,MyDTO dto)
    {
        System.out.println(dto);
    }
    public class MyT extends BaseTrigger implements MyClickTrigger
    {
        MyDTO dto;
        public MyT(MyDTO dto)
        {
            this.dto=dto;
        }
        @Override
        public void onIconClick(View view) {
            share(dto);
        }
    }
}
