package com.peoplestouch.layouts;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.view.View;

/**
 * Created by BhavinPadhiyar on 07/06/16.
 */
public interface MyClickTrigger extends Observable
{
    void onIconClick(View view);
}
