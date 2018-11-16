package com.zhangqie.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.zhangqie.cart.itemclick.OnClickAddCloseListenter;
import com.zhangqie.cart.widget.LinearLayoutForListView;
import com.zhangqie.cart.R;
import com.zhangqie.cart.entity.CartInfo;
import com.zhangqie.cart.itemclick.OnClickListenterModel;
import com.zhangqie.cart.itemclick.OnItemMoneyClickListener;
import com.zhangqie.cart.itemclick.OnViewItemClickListener;

import java.util.List;

/**
 * Created by zhangqie on 2018/11/15
 * Describe:
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartInfo.DataBean> list;
    public ListBaseAdapter listBaseAdapter;
    public boolean isCheck = false;

    public CartAdapter(Context context, List<CartInfo.DataBean> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_group, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvChild.setText(list.get(position).getShop_name());
        holder.cbChild.setChecked(list.get(position).ischeck());
        listBaseAdapter = new ListBaseAdapter(context, position, list.get(position).getItems());
        holder.listView.setAdapter(listBaseAdapter);
        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.cbChild.isChecked(), v, position);
            }
        });

        //店铺下的checkbox
        listBaseAdapter.setOnClickListenterModel(new OnClickListenterModel() {
            @Override
            public void onItemClick(boolean isFlang, View view, int onePosition, int position) {
                list.get(onePosition).getItems().get(position).setIscheck(isFlang);
                int length = list.get(onePosition).getItems().size();
                if (length == 1) {
                    mOnItemClickListener.onItemClick(isFlang, view, onePosition);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (list.get(onePosition).getItems().get(i).ischeck()) {//true,true,true
                            isCheck = true;
                        } else {
                            isCheck = false;
                            break;
                        }
                    }
                    list.get(onePosition).setIscheck(isCheck);
                    onItemMoneyClickListener.onItemClick(view, onePosition);
                    notifyDataSetChanged();
                }
            }
        });
        /***
         * 数量增加和减少
         */
        listBaseAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
            @Override
            public void onItemClick(View view, int index, int onePosition, int position,int num) {
                if (index==1){
                    if (num>1) {
                        list.get(onePosition).getItems().get(position).setNum((num - 1));
                        notifyDataSetChanged();
                    }
                }else {
                    list.get(onePosition).getItems().get(position).setNum((num + 1));
                    notifyDataSetChanged();
                }
                onItemMoneyClickListener.onItemClick(view, onePosition);
            }
        });

    }

    // CheckBox全选的方法
    private OnViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    // 计算价钱
    private OnItemMoneyClickListener onItemMoneyClickListener = null;

    public void setOnItemMoneyClickListener(OnItemMoneyClickListener listener) {
        this.onItemMoneyClickListener = listener;
    }




    /**
     * 删除选中item
     */
    public void removeChecked() {
        int iMax = list.size() - 1;
        //这里要倒序，因为要删除mDatas中的数据，mDatas的长度会变化
        for (int i = iMax; i >= 0; i--) {
            if (list.get(i).ischeck()) {
                list.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, list.size());
            } else {
                int length = list.get(i).getItems().size() - 1;
                for (int j = length; j >= 0; j--) {
                    if (list.get(i).getItems().get(j).ischeck()) {
                        list.get(i).getItems().remove(j);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, list.size());
                    }
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvChild;
        public CheckBox cbChild;
        public LinearLayoutForListView listView;

        public ViewHolder(View view) {
            super(view);
            tvChild = (TextView) view.findViewById(R.id.tv_group);
            cbChild = (CheckBox) view.findViewById(R.id.cb_group);
            listView = view.findViewById(R.id.listview_cart);

        }
    }

}
