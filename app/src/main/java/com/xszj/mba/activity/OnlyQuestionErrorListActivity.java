package com.xszj.mba.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xszj.mba.R;
import com.xszj.mba.adapter.OnlyQuestionErrorListAdapter;
import com.xszj.mba.adapter.QuestionErrorListAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：mba
 * 类描述：
 * 创建人：ybx
 * 创建时间：2018/1/24 11:10
 * 备注：
 */

public class OnlyQuestionErrorListActivity extends BaseActivity {
    @BindView(R.id.globalTitleView)
    GlobalTitleView globalTitleView;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;

    private String title;
    private OnlyQuestionErrorListAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_question_error_list_layout;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void bindViews() {
        globalTitleView.setTitle("" + title);
        globalTitleView.setTitleColor(Color.BLACK);
        globalTitleView.setBackVisible(true);
        globalTitleView.setBackIconImage(R.mipmap.btn_back_normal);
        globalTitleView.setTitleViewBackground(Color.WHITE);
        globalTitleView.setTitleRightTvShow(true);
        globalTitleView.setTitleRightTv("重做");
        globalTitleView.setTitleRightTvColor(Color.parseColor("#0091EA"));
        globalTitleView.setTitleViewListener(new GlobalTitleView.TitleViewListener() {
            @Override
            public void setOnBackBtn() {
                finish();
            }

            @Override
            public void setDoMore() {
                startActivity(new Intent(context, QuestionDetailActivity.class));
            }
        });
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        xrecyclerview.setLoadingMoreEnabled(true);
        adapter = new OnlyQuestionErrorListAdapter(null, context);
        xrecyclerview.setAdapter(adapter);

        getDataFromService();
    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                xrecyclerview.loadMoreComplete();
                for (int i = 0; i < 10; i++) {
                    list.add("1" + i);
                }
                adapter.setData(list);
            }
        });
    }


    private void getDataFromService() {
        /*for (int i = 0; i < 6; i++) {
            list.add("1" + i);
        }*/
        list.add("为了实现重回过去苗条身材的梦想，身材越来越胖的吴太太开始使用法国蔓莎减肥药，她严格按照药品使用说明服用，不敢有丝毫懈怠.可是，一个月过去了，她的体重仍然没有减轻.可见，法国蔓莎减肥药安全无效. 下面哪项如果为真，最能削弱上述结论？" + 0);
        list.add("在球类比赛中，利用回放决定判罚是错误的.因为无论有多少台摄像机跟踪拍摄场上的比赛，都难免会漏掉一些犯规动作，要对所发生的一切明察秋毫是不可能的.以下哪项论证的缺陷与上述论证最相似?" + 1);
        list.add("在过去五年里，新商品房的平均价格每平方米增加了25%.在同期的平均家庭预算中，购买商品房的费用所占的比例保持不变.所在在过去五年里，平均家庭预算也一定增加了25%. 以下哪项关于过去五年情况的陈述是上面论述所依赖的假设？" + 2);
        list.add("一个人在用餐之后昏昏欲睡还是精神饱满与所吃食物中的蛋白费有关.多数蛋白质中都含有一种叫酪氨酸的氨基酸，它进入大脑促使多巴胺和新肾上腺素的形成，从而使一个人兴奋.禽类和鱼类含酪氨酸，但脂肪妨碍了它的吸收. 由以上陈述可以推出：" + 3);
        list.add("埃伊、阿曼和得比这三个国家一个属于亚洲、一个属于欧洲、一个属于非洲，其中埃伊和其中的欧洲国家不一样大，得比比其中的非洲国家小，其中的欧洲国家比阿曼大. 那么这三个国家从大到小的次序是：" + 4);
        list.add("太阳能不像传统的煤、天然气和原子能那样，它不会产生污染，无需运输，没有辐射的危险，不受制于电力公司.所以，应该鼓励人们使用太阳能. 如果以下哪项陈述为真，能够最有力地削弱上述论证？" + 5);

        adapter.setData(list);
    }
}
