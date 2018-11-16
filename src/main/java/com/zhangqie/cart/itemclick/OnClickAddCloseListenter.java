package com.zhangqie.cart.itemclick;

import android.view.View;

/**
 * Created by zhangqie on 2016/11/26.
 * 添加和减少 接口回调
 */

public interface OnClickAddCloseListenter {

    void onItemClick(View view, int index, int onePosition, int position, int num);

}
