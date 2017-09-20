package com.chengdai.ehealthproject.weigit.popwindows;

import android.view.View;

/**
 * Created by 李先俊 on 2017/6/14.
 */

public interface BasePopup {
    View getPopupView();
    View getAnimaView();
    void onPopupDismiss();

}