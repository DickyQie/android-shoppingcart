 <div class="content" id="articleContent">
                                                <p>电商项目中常常有购物车这个功能，做个很多项目了，都有不同的界面，选了一个来讲一下。</p> 
<p><br> RecyclerView 模仿淘宝购物车功能(删除选择商品，商品计算，选择， 全选反选，商品数量加减等)</p> 
<p>&nbsp;</p> 
<p>看看效果图：</p> 
<p><img alt="" src="https://img2018.cnblogs.com/blog/1041439/201811/1041439-20181121194644407-1266059911.gif"></p> 
<p>&nbsp;</p> 
<p>Activity代码：</p> 
<pre><code class="language-java">/*****
 * RecyclerView 模仿淘宝购物车功能
 *
 * 删除选择商品，商品计算，选择，全选反选，商品数量加减等
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
        cartShoppMoular.setOnClickListener(new OnClickListener());
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
                for (int i = 0; i &lt; length; i++) {
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
        for (int i = 0; i &lt; cartInfo.getData().size(); i++) {
            for (int j = 0; j &lt; cartInfo.getData().get(i).getItems().size(); j++) {
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
            if (money.substring(money.indexOf("."), money.length()).length() &gt; 2) {
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
                        for (int i = 0; i &lt; length; i++) {
                            cartInfo.getData().get(i).setIscheck(true);
                            int lengthn = cartInfo.getData().get(i).getItems().size();
                            for (int j = 0; j &lt; lengthn; j++) {
                                cartInfo.getData().get(i).getItems().get(j).setIscheck(true);
                            }
                        }

                    } else {
                        int length = cartInfo.getData().size();
                        for (int i = 0; i &lt; length; i++) {
                            cartInfo.getData().get(i).setIscheck(false);
                            int lengthn = cartInfo.getData().get(i).getItems().size();
                            for (int j = 0; j &lt; lengthn; j++) {
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
                case R.id.cart_shopp_moular:
                    Toast.makeText(MainActivity.this,"提交订单:  "+cartMoney.getText().toString()+"元",Toast.LENGTH_LONG).show();
                    break;
            }
        }
}</code></pre> 
<p>这是我写的第二个购物车，基本的功能都有了的。</p> 
<p>有需要的小伙们，可以参考一下。</p> 
