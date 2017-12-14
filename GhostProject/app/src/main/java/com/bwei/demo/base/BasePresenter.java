package com.bwei.demo.base;

import java.lang.ref.WeakReference;

/**
 * Created by ${李晨阳} on 2017/12/12.
 */

public class BasePresenter<T> {

    public WeakReference reference;

    public void attachView(T view) {
        //建立关联
        reference = new WeakReference<T>(view);
    }

    protected T getView() {
        //获得view

        return isAttach() ? (T) reference.get() : null;
    }

    public void detachView() {
        //解除关联
        if (reference != null && reference.get() != null) {
            reference.clear();
            reference = null;
        }
    }

    public boolean isAttach() {
        return reference != null ? true : false;
    }
}
