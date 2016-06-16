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

import java.io.IOException;
import java.io.InputStream;

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
        JavaScript javaScript;
        JSONTransform transform;
        public MyT(MyDTO dto)
        {
            this.dto=dto;
            javaScript=new JavaScript(R.raw.php_crud_api_transform,MainActivity.this,new String[]{"php_crud_api_transform"});
            transform=new JSONTransform(R.raw.php_crud_api_transform,MainActivity.this,new String[]{"php_crud_api_transform"});
        }
        @Override
        public void onIconClick(View view) {
            String jsontext ="{\"post\":{\"columns\":[\"id_post\",\"id_user\"],\"records\":[[\"2\",\"2\"]]},\"users\":{\"relations\":{\"id_user\":\"post.id_user\"},\"columns\":[\"id_user\",\"first_name\",\"photo\"],\"records\":[[\"2\",null,null]]},\"files\":{\"relations\":{\"id_post\":\"post.id_post\",\"id_user\":\"users.id_user\"},\"columns\":[\"id_files_data\",\"id\",\"id_user\",\"id_post\",\"id_event\",\"id_resource\",\"id_photo_album\",\"id_resource_data\",\"label\",\"url\",\"preview\",\"size\",\"color\",\"w\",\"h\",\"is_attachment\",\"is_video\",\"location\",\"longitude\",\"latitude\",\"time_stamp\"],\"records\":[[\"1\",null,\"2\",\"2\",null,null,null,null,\"OK\",null,null,null,null,null,null,null,null,null,null,null,\"2016-06-02 13:11:17\"],[\"2\",null,\"10\",\"2\",null,null,null,null,\"NEW FILE\",null,null,null,null,null,null,null,null,null,null,null,\"2016-06-14 05:51:18\"]]}}";
            String data=javaScript.call("php_crud_api_transform",new Object[]{jsontext}).toString();
            System.out.println(data);

            data=transform.transform(jsontext);
            System.out.println(data);


        }
    }


}
