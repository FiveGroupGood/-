package com.bwei.demo.di.component;

import android.app.Activity;

import com.bwei.demo.di.module.DetailsModule;

import dagger.Component;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

@Component(modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(Activity activity);
}
