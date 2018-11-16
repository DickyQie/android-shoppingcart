package com.zhangqie.cart;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhangqie.cart.adapter.CartAdapter;
import com.zhangqie.cart.entity.CartInfo;
import com.zhangqie.cart.itemclick.OnClickAddCloseListenter;
import com.zhangqie.cart.itemclick.OnItemMoneyClickListener;
import com.zhangqie.cart.itemclick.OnViewItemClickListener;


/*****
 * 模仿淘宝购物车 功能
 *
 * 删除选择商品，商品计算，
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNestDemo;
    private CartAdapter cartAdapter;
    CartInfo cartInfo;
    double price;
    int num;

    TextView cartNum;
    TextView cartMoney;
    Button cartShoppMoular;
    CheckBox checkBox;


    private TextView btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNestDemo = (RecyclerView) findViewById(R.id.rv_nest_demo);
        cartNum = findViewById(R.id.cart_num);
        cartMoney = findViewById(R.id.cart_money);
        cartShoppMoular = findViewById(R.id.cart_shopp_moular);
        checkBox = findViewById(R.id.cbx_quanx_check);
        checkBox.setOnClickListener(new OnClickListener());
        btnDelete = (TextView) findViewById(R.id.btn_delete);
        initView();
    }


    private void initView() {
        btnDelete.setOnClickListener(new OnClickListener());
        showData();
        rvNestDemo.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_divider_inset));
        rvNestDemo.addItemDecoration(itemDecoration);
        cartAdapter = new CartAdapter(this, cartInfo.getData());
        rvNestDemo.setAdapter(cartAdapter);
        showExpandData();

    }

    private void showExpandData() {
        /**
         * 全选
         */
        cartAdapter.setOnItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                cartInfo.getData().get(position).setIscheck(isFlang);
                int length = cartInfo.getData().get(position).getItems().size();
                for (int i = 0; i < length; i++) {
                    cartInfo.getData().get(position).getItems().get(i).setIscheck(isFlang);
                }
                cartAdapter.notifyDataSetChanged();
                showCommodityCalculation();
            }
        });

        /**
         * 计算价钱
         */
        cartAdapter.setOnItemMoneyClickListener(new OnItemMoneyClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCommodityCalculation();
            }

        });
    }

    /***
     * 计算商品的数量和价格
     */
    private void showCommodityCalculation() {
        price = 0;
        num = 0;
        for (int i = 0; i < cartInfo.getData().size(); i++) {
            for (int j = 0; j < cartInfo.getData().get(i).getItems().size(); j++) {
                if (cartInfo.getData().get(i).getItems().get(j).ischeck()) {
                    price += Double.valueOf((cartInfo.getData().get(i).getItems().get(j).getNum() * Double.valueOf(cartInfo.getData().get(i).getItems().get(j).getPrice())));
                    num++;
                } else {
                    checkBox.setChecked(false);
                }
            }
        }
        if (price == 0.0) {
            cartNum.setText("共0件商品");
            cartMoney.setText("¥ 0.0");
            return;
        }
        try {
            String money = String.valueOf(price);
            cartNum.setText("共" + num + "件商品");
            if (money.substring(money.indexOf("."), money.length()).length() > 2) {
                cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 3)));
                return;
            }
            cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showData() {
        cartInfo = JSON.parseObject(JSONDATA(), CartInfo.class);
    }

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                //全选和不全选
                case R.id.cbx_quanx_check:
                    if (checkBox.isChecked()) {
                        int length = cartInfo.getData().size();
                        for (int i = 0; i < length; i++) {
                            cartInfo.getData().get(i).setIscheck(true);
                            int lengthn = cartInfo.getData().get(i).getItems().size();
                            for (int j = 0; j < lengthn; j++) {
                                cartInfo.getData().get(i).getItems().get(j).setIscheck(true);
                            }
                        }

                    } else {
                        int length = cartInfo.getData().size();
                        for (int i = 0; i < length; i++) {
                            cartInfo.getData().get(i).setIscheck(false);
                            int lengthn = cartInfo.getData().get(i).getItems().size();
                            for (int j = 0; j < lengthn; j++) {
                                cartInfo.getData().get(i).getItems().get(j).setIscheck(false);
                            }
                        }
                    }
                    cartAdapter.notifyDataSetChanged();
                    showCommodityCalculation();
                    break;
                case R.id.btn_delete:
                    //删除选中商品
                    cartAdapter.removeChecked();
                    showCommodityCalculation();
                    break;
            }
        }
    }

    public static final String JSONDATA() {
        return "{\"errcode\":0,\"errmsg\":\"success\",\"data\":[" +
                "{\"shop_id\":\"1636\",\"shop_name\":\"水城县阳光佳美食材经营部\",\"items\":[{\"itemid\":\"100189\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/06\\/20170609105502145359.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/06\\/20170609105502145359.jpg\",\"price\":\"3.00\",\"title\":\"油菜一斤\"}]}," +
                "{\"shop_id\":\"1666\",\"shop_name\":\"水城县美食材经营部\",\"items\":[{\"itemid\":\"100189\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/06\\/20170609105502145359.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/06\\/20170609105502145359.jpg\",\"price\":\"33.00\",\"title\":\"羊肉一斤\"}]}," +
                "{\"shop_id\":\"1669\",\"shop_name\":\"美食材经营部\",\"items\":[{\"itemid\":\"100189\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/06\\/20170609105502145359.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/06\\/20170609105502145359.jpg\",\"price\":\"32.00\",\"title\":\"狗肉一斤\"}]}," +
                "{\"shop_id\":\"1778\",\"shop_name\":\"商品测试专卖店\",\"items\":[{\"itemid\":\"103677\",\"quantity\":\"2\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/09\\/20170926150650687701.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/09\\/20170926150650687701.jpg\",\"price\":\"0.10\",\"title\":\"测试商品1\"},{\"itemid\":\"103629\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/10\\/20171016134627837135.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/10\\/20171016134627837135.jpg\",\"price\":\"2.50\",\"title\":\"测试商品2\"},{\"itemid\":\"104015\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/10\\/20171016135318646327.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/10\\/20171016135318646327.jpg\",\"price\":\"1.00\",\"title\":\"测试商品3\"}]}]}";
    }
}
