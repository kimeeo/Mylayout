package com.peoplestouch.layouts;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by BhavinPadhiyar on 07/06/16.
 */
public class MyDTO extends BaseObservable {
    @Bindable
    public String getLable() {
        return lable;

    }

    public void setLable(String lable) {
        this.lable = lable;

        notifyPropertyChanged(com.peoplestouch.layouts.BR.lable);
    }

    private String lable="ABC";

}
