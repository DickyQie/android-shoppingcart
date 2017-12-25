### Android-----购物车（包含侧滑删除，商品筛选，商品增加和减少，价格计算，店铺分类等）

 <p>电商项目中常常有购物车这个功能，做个很多项目了，都有不同的界面，选了一个来讲一下。</p> 
<p>主要包含了 店铺分类，侧滑删除，商品筛选，增加和减少，价格计算等功能。</p> 
<p>看看效果图：</p> 
<p><img alt="" src="http://images2017.cnblogs.com/blog/1041439/201712/1041439-20171224162757162-1372463675.jpg" width="360"> <img alt="" src="http://images2017.cnblogs.com/blog/1041439/201712/1041439-20171224162831068-226571999.jpg" width="360"></p> 
<p><img alt="" src="http://images2017.cnblogs.com/blog/1041439/201712/1041439-20171224163031553-254559380.jpg" width="360"></p> 
<p>&nbsp;</p> 
<p>重要代码：</p> 
<pre><code class="language-java"> private void showExpandData(){
        cartExpandAdapter=new CartExpandAdapter(this,cartExpandablelistview,cartInfo.getData());
        cartExpandablelistview.setAdapter(cartExpandAdapter);
        int intgroupCount = cartExpandablelistview.getCount();
        for (int i=0; i&lt;intgroupCount; i++)
        {
            cartExpandablelistview.expandGroup(i);
        }
        /**
         * 全选
         */
        cartExpandAdapter.setOnItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                cartInfo.getData().get(position).setIscheck(isFlang);
                int length=cartInfo.getData().get(position).getItems().size();
                for (int i = 0; i &lt; length; i++) {
                    cartInfo.getData().get(position).getItems().get(i).setIscheck(isFlang);
                }
                cartExpandAdapter.notifyDataSetChanged();
                showCommodityCalculation();
            }
        });
        /**
         * 单选
         */
        cartExpandAdapter.setOnClickListenterModel(new OnClickListenterModel() {
            @Override
            public void onItemClick(boolean isFlang, View view,int onePosition, int position) {
                cartInfo.getData().get(onePosition).getItems().get(position).setIscheck(isFlang);
                int length=cartInfo.getData().get(onePosition).getItems().size();
                for (int i = 0; i &lt; length ; i++) {
                    if (! cartInfo.getData().get(onePosition).getItems().get(i).ischeck()){
                        if (!isFlang){
                            cartInfo.getData().get(onePosition).setIscheck(isFlang);
                        }
                        cartExpandAdapter.notifyDataSetChanged();
                        showCommodityCalculation();
                        return;
                    }else {
                        if (i== ( length-1)){
                            cartInfo.getData().get(onePosition).setIscheck(isFlang);
                            cartExpandAdapter.notifyDataSetChanged();
                        }
                    }
                }
                showCommodityCalculation();
            }
        });
        cartExpandAdapter.setOnClickDeleteListenter(new OnClickDeleteListenter() {
            @Override
            public void onItemClick(View view, int onePosition, int position) {
                Toast.makeText(MainActivity.this,"删除操作",Toast.LENGTH_LONG).show();
            }
        });
        /***
         * 数量增加和减少
         */
        cartExpandAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
            @Override
            public void onItemClick(View view, int index, int onePosition, int position,int num) {
                if (index==1){
                    if (num&gt;1) {
                        cartInfo.getData().get(onePosition).getItems()
                                .get(position).setNum((num - 1));
                        cartExpandAdapter.notifyDataSetChanged();
                    }
                }else {
                    cartInfo.getData().get(onePosition).getItems().get(position).setNum((num + 1));
                    cartExpandAdapter.notifyDataSetChanged();
                }
                showCommodityCalculation();
            }
        });
    }
    
<p>这是我项目中用到的购物车，基本的功能都有了的。</p> 
<p>有需要的小伙们，可以参考一下。</p> 
<span id="OSC_h3_1"></span>
