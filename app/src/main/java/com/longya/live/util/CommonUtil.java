package com.longya.live.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.longya.live.CommonAppConfig;
import com.longya.live.model.ConfigurationBean;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/12
 */
public class CommonUtil {
    public static void forwardCustomer(Context context) {
        ConfigurationBean config = CommonAppConfig.getInstance().getConfig();
        if (config != null && !TextUtils.isEmpty(config.getCustomerService())) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(config.getCustomerService());
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }
}
