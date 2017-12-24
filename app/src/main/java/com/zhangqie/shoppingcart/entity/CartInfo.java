package com.zhangqie.shoppingcart.entity;

import java.util.List;

/**
 * Created by zhangqie on 2016/11/26.
 * 购物车
 */

public class CartInfo extends User{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String shop_id;
        private String shop_name;
        private List<ItemsBean> items;
        private boolean ischeck=false;

        public boolean ischeck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {

            private String itemid;
            private String quantity;
            private String image;
            private String price;
            private String title;
            private boolean ischeck=false;
            private int num=1;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public boolean ischeck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public String getItemid() {
                return itemid;
            }

            public void setItemid(String itemid) {
                this.itemid = itemid;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
