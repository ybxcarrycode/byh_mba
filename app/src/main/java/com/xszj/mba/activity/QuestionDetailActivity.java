package com.xszj.mba.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.adapter.QuestionAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.AnswerBean;
import com.xszj.mba.bean.AnswerBeanDao;
import com.xszj.mba.bean.DaoMaster;
import com.xszj.mba.bean.DaoSession;
import com.xszj.mba.bean.QuestionBean;
import com.xszj.mba.bean.QuestionBeanDao;
import com.xszj.mba.listener.EndlessRecyclerOnScrollListener;
import com.xszj.mba.utils.MyAsyncTask;
import com.xszj.mba.utils.StoreUtils;
import com.xszj.mba.view.XsDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/2.
 */

public class QuestionDetailActivity extends BaseActivity {

    @BindView(R.id.recy1)
    RecyclerView recy1;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_last)
    TextView tvLast;
    @BindView(R.id.iv_black)
    ImageView ivBlack;
    private QuestionBeanDao questionBeanDao;
    private AnswerBeanDao answerBeanDao;
    private int currentPosition = 0;
    private List<QuestionBean> list;
    private QuestionAdapter questionAdapter;
    private HashMap<Integer, Integer> answerMap = new HashMap<>();
    private Chronometer chronometer;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_questions_detail;
    }

    @Override
    protected void bindViews() {
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
        chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
    }

    @Override
    protected void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy1.setLayoutManager(linearLayoutManager);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recy1);

        initData();
    }

    private void initData() {

        //c初始化greendao
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();

        questionBeanDao = daoSession.getQuestionBeanDao();

        answerBeanDao = daoSession.getAnswerBeanDao();

        //加载数据
        loadFromLocal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addGuideImageNew(0);
    }

    private void loadFromLocal() {
        new MyAsyncTask() {
            @Override
            public void preTask() {
                loadingProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBack() {
                //子线程
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list = questionBeanDao.queryBuilder()
                        .where(QuestionBeanDao.Properties.QId.between(0, 99)).limit(24).build()
                        .list();
            }

            @Override
            public void postTask() {
                loadingProgress.setVisibility(View.GONE);
                questionAdapter = new QuestionAdapter(list);
                recy1.setAdapter(questionAdapter);
            }
        }.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();
        chronometer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chronometer.stop();
    }

    //获取所有答案
    public List<AnswerBean> getAnswerS(int id) {

        List<AnswerBean> list = answerBeanDao.queryBuilder()
                .where(AnswerBeanDao.Properties.AnswerID.eq(id)).build().list();

        return list;

    }

    //保存用户答案
    public void updataAnswer(int id, int answerId) {
        answerMap.put(id, answerId);
        Log.e("ddddddHashmap", id + "///" + answerId);
        QuestionBean questionBean = questionBeanDao.queryBuilder()
                .where(QuestionBeanDao.Properties.QId.eq(id)).build().unique();
        if (questionBean != null) {
            questionBean.setAStuId(answerId);
            questionBeanDao.update(questionBean);
        }


    }

    //判断用户是否答题
    public int quaryAnswer(int id) {
        int position = -1;
        QuestionBean unique = questionBeanDao.queryBuilder()
                .where(QuestionBeanDao.Properties.QId.eq(id)).build().unique();
        if (unique != null && unique.getAStuId() != -1) {
            position = unique.getAStuId();
            return position;
        } else {
            return position;
        }

    }

    @Override
    protected void initListeners() {

        recy1.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                showToast("我是最后一个滑动");
                showNextDialog();
            }

            @Override
            public void onPrew() {
                //showToast("我是右滑");
            }

            @Override
            public void onNext() {
                //showToast("我是左滑");
            }

            @Override
            public void currentPosition(int position) {
                currentPosition = position;
                Log.e("ddddddd123", position + "ewrur df ");
            }
        });
    }

    private void showNextDialog() {
        final XsDialog noticeDialog;
        noticeDialog = new XsDialog(context, "温馨提示:", "是否查看答题结果", true, true, true);
        noticeDialog.setBtnOklistener(new XsDialog.BtnOKListener() {
            @Override
            public void clickOk() {
                startActivity(new Intent(context, QuestionResultActivity.class)
                        .putExtra("userSel", answerMap));
            }
        });
        noticeDialog.setBtnCancelListener(new XsDialog.BtnCancelListener() {
            @Override
            public void clickCancel() {

            }
        });
        noticeDialog.show();
    }


    @OnClick({R.id.tv_next, R.id.tv_last, R.id.iv_black})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_black:
                finish();
                break;
            case R.id.tv_next:
                if (currentPosition == 0) {
                    showToast("前面没有啦");
                    return;
                }
                Log.e("dddddqw11", currentPosition + "==");
                recy1.scrollToPosition(currentPosition - 1);
                currentPosition = currentPosition - 1;
                break;
            case R.id.tv_last:
                if (currentPosition == list.size() - 1) {
                    showNextDialog();
                    return;
                }
                recy1.scrollToPosition(currentPosition + 1);
                currentPosition = currentPosition + 1;
                Log.e("dddddqw22", currentPosition + "==");
                break;
        }
    }

    private int guideResourceId = 0;
    private void addGuideImageNew(int i) {
        guideResourceId = R.layout.guide_layout_question_detail;
        View view = getWindow().getDecorView().findViewById(R.id.ll_question);//查找通过setContentView上的根布局
        if (view == null) {
            return;
        }
        if (!StoreUtils.getIschecked(context, "tu_newguide_question_detail" + "/")) {
            //引导过了
            return;
        }
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout frameLayout = (FrameLayout) viewParent;
            if (guideResourceId != 0) {//设置了引导图片
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View viewG = inflater.inflate(guideResourceId, null);
                viewG.setLayoutParams(params);
                TextView iv_know = (TextView) viewG.findViewById(R.id.question_quide_yes);
                StoreUtils.setIschecked(context, "tu_newguide_question_detail" + "/", false);//设为已引导

                viewG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return;
                    }
                });

                iv_know.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        frameLayout.removeView(viewG);

                    }
                });
                frameLayout.addView(viewG);//添加引导图片

            }
        }
    }
}
