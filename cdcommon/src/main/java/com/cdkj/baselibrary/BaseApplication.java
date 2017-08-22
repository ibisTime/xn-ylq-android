
package com.cdkj.baselibrary;

import android.app.Application;
import android.content.Context;

import com.cdkj.baselibrary.utils.LogUtil;

public class BaseApplication extends Application {
	/**
	 * Global application context.
	 */
	private static Context sContext;

	/**
	 * Construct of LitePalApplication. Initialize application context.
	 */
	public BaseApplication() {
		sContext = this;
	}

	public static void initialize(Context context,boolean isDebug) {
		LogUtil.isDeBug =isDebug;
        sContext = context;
    }

	public static Context getContext() {
		return sContext;
	}

}
