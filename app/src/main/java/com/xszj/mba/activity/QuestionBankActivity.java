package com.xszj.mba.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.AnswerBean;
import com.xszj.mba.bean.AnswerBeanDao;
import com.xszj.mba.bean.DaoMaster;
import com.xszj.mba.bean.DaoSession;
import com.xszj.mba.bean.QuestionBean;
import com.xszj.mba.bean.QuestionBeanDao;
import com.xszj.mba.utils.MyAsyncTask;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.view.CircleProgressBar;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.PopuView;
import com.xszj.mba.view.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 * 题库选择类型界面
 */

public class QuestionBankActivity extends BaseActivity {

    private GlobalTitleView titleView;
    private RelativeLayout rl_subject;
    private RelativeLayout rl_knowledge;
    private RelativeLayout rl_questions;
    private TextView tv_knowledge;
    private TextView tv_subject;
    private TextView tv_questions;
    private WheelView wva;
    private PopupWindow popupWindow;
    private Button btn_start;
    private ProgressBar loadingProgressDb;
    private CircleProgressBar progress_bar;
    private PopuView popuView;
    private static final String[] SUBJECT = new String[]{"逻辑", "数学", "英语", "写作"};
    private static final String[] KNOWLEDGE = new String[]{ "陈君华写作高分指南", "写作预测八套卷", "历年真题名家详解"};
    private static final String[] QUESTION = new String[]{"论说文", "2015联考逻辑终极测试", "2014联考逻辑终极测试", "2013联考逻辑终极测试", "2012联考逻辑终极测试", "2011联考逻辑终极测试", "2010联考逻辑终极测试", "2009联考逻辑终极测试"};

    private AnswerBeanDao answerBeanDao;
    private QuestionBeanDao questionBeanDao;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_questions_bank;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        rl_knowledge = (RelativeLayout) findViewById(R.id.rl_knowledge);
        rl_subject = (RelativeLayout) findViewById(R.id.rl_subject);
        rl_questions = (RelativeLayout) findViewById(R.id.rl_questions);
        tv_subject = (TextView) findViewById(R.id.tv_subject);
        tv_knowledge = (TextView) findViewById(R.id.tv_knowledge);
        tv_questions = (TextView) findViewById(R.id.tv_questions);
        btn_start = (Button) findViewById(R.id.btn_start);
        loadingProgressDb = (ProgressBar) findViewById(R.id.loading_progress_db);
        progress_bar = (CircleProgressBar) findViewById(R.id.progress_bar);
        popuView = (PopuView) findViewById(R.id.po);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("题库");
        titleView.setTitleColor(Color.BLACK);
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.mipmap.btn_back_normal);
        titleView.setTitleViewBackground(Color.WHITE);
        titleView.setTitleRightTvShow(true);
        titleView.setTitleRightTv("刷题历史");
        titleView.setTitleRightTvColor(Color.parseColor("#646A6D"));
        titleView.setTitleViewListener(new GlobalTitleView.TitleViewListener() {
            @Override
            public void setOnBackBtn() {
                finish();
            }

            @Override
            public void setDoMore() {
                startActivity(new Intent(context, QuestionHistoryActivity.class));
            }
        });
        progress_bar.setAnimProgress(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //检验用户是否选过并显示出来
        String questionSubject = SharedPreferencesUtils.getProperty(context, "question_subject");
        String questionKnowledge = SharedPreferencesUtils.getProperty(context, "question_knowledge");
        String questionQuestion = SharedPreferencesUtils.getProperty(context, "question_question");
        if (questionSubject == null || questionSubject.equals("")) {
            tv_subject.setText("选择科目");
        } else {
            tv_subject.setText(questionSubject);
        }
        if (questionKnowledge == null || questionKnowledge.equals("")) {
            tv_knowledge.setText("选择知识点");
        } else {
            tv_knowledge.setText(questionKnowledge);
        }
        if (questionQuestion == null || questionQuestion.equals("")) {
            tv_questions.setText("选择题库");
        } else {
            tv_questions.setText(questionQuestion);
        }
        if (isSelectQuestion()) {
            btn_start.setBackgroundColor(getResources().getColor(R.color.c0091EA));
        }
    }
    @Override
    protected void getDateForService() {
        super.getDateForService();

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();

        //答案表操作类
        answerBeanDao = daoSession.getAnswerBeanDao();
        //题库表操作类
        questionBeanDao = daoSession.getQuestionBeanDao();

        instertQusData();
    }

    @Override
    protected void initListeners() {
        rl_knowledge.setOnClickListener(this);
        rl_subject.setOnClickListener(this);
        rl_questions.setOnClickListener(this);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.rl_subject:
                chieckPop(1, new ArrayList<String>());
                break;
            case R.id.rl_knowledge:
                chieckPop(2, new ArrayList<String>());
                break;
            case R.id.rl_questions:
                chieckPop(3, new ArrayList<String>());
                break;
            case R.id.btn_start:
                if (isSelectQuestion()) {
                    startActivity(new Intent(context, QuestionDetailActivity.class));
                } else {
                    showToast("请先选择科目、知识点、题库");
                }
                break;

            default:
                break;
        }
    }

    private void chieckPop(final int type, List<String> lists) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_layout, null);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        wva = (WheelView) contentView.findViewById(R.id.wheelview);
        TextView tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        wva.setOffset(2);
        if (type == 1) {
            wva.setItems(Arrays.asList(SUBJECT));
            tv_title.setText("选择科目");
        } else if (type == 2) {
            wva.setItems(Arrays.asList(KNOWLEDGE));
            tv_title.setText("选择知识点");
        } else if (type == 3) {
            wva.setItems(Arrays.asList(QUESTION));
            tv_title.setText("选择题库");
        }

        wva.setSeletion(0);

        TextView tv_close = (TextView) contentView.findViewById(R.id.tv_close);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView tv_select = (TextView) contentView.findViewById(R.id.tv_select);
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (type == 1) {
                    tv_subject.setText(wva.getSeletedItem());
                    SharedPreferencesUtils.setProperty(context, "question_subject", wva.getSeletedItem());
                } else if (type == 2) {
                    tv_knowledge.setText(wva.getSeletedItem());
                    SharedPreferencesUtils.setProperty(context, "question_knowledge", wva.getSeletedItem());
                } else if (type == 3) {
                    tv_questions.setText(wva.getSeletedItem());
                    SharedPreferencesUtils.setProperty(context, "question_question", wva.getSeletedItem());
                }

                if (isSelectQuestion()) {
                    btn_start.setBackgroundColor(getResources().getColor(R.color.c0091EA));
                    popuView.setmText("Awesome, 立即开始答题");
                }
            }
        });
    }

    private void instertQusData() {
        new MyAsyncTask() {
            @Override
            public void preTask() {
                loadingProgressDb.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBack() {
                questionBeanDao.deleteAll();
                answerBeanDao.deleteAll();
                for (int i = 1; i < 101; i++) {
                    long num = i;
                    int type = 0;
                    int curId = (int) num % 5;
                    if (num % 2 == 0) {
                        type = 0;
                    } else {
                        type = 1;
                    }

                    String question ="有关数据显示，2005年以来，广东高校毕业生自主创业的数量约占当年高校毕业生的1%～2%。以2008年为例，应届高校毕业生中选择自主创业的仅占1.2%。而在西方发 达国家，这个数字为20%～30%。由此看来，西方发达国家的大学生更具有创业才能。 以下哪一项说法如果正确，最能对上述结论构成质疑？";
                    String offerAns = "题干是评价创业才能，C项提出了新的衡量标准，说明有无创业才能应看创业成功率而非创业人数所占的比例，这就直接削弱了题干。A项说明中国高校大学生自主创业人数所占比例少是“另有他因”，但可能题干结论也是原因之一，故削弱力度较弱；虽然比例高的绝对值未必大，但此处仅涉及比例之间的比较而不涉及数量比较，所以与基数无关，故B项是无关项；D项挑战自我与题干论证无关；E项可以解释为何西方发达国家的大学生创业比例高，但无法说明创业才能的问题。";
                    int aId = i;
                    questionBeanDao.insert(new QuestionBean(type, num, question, aId, -1, curId, offerAns));

                    answerBeanDao.insert(new AnswerBean(aId, 0, ""));
                    answerBeanDao.insert(new AnswerBean(aId, 1, ""));
                    answerBeanDao.insert(new AnswerBean(aId, 2, ""));
                    answerBeanDao.insert(new AnswerBean(aId, 3, ""));
                    answerBeanDao.insert(new AnswerBean(aId, 4, ""));

                }
            }

            @Override
            public void postTask() {
                loadingProgressDb.setVisibility(View.GONE);
            }
        }.execute();

    }

    //判断是否选择了知识点，科目，题库
    private boolean isSelectQuestion() {
        String TvSubject = tv_subject.getText().toString().trim();
        String TvKnowledge = tv_knowledge.getText().toString().trim();
        String TvQuestions = tv_questions.getText().toString().trim();
        if (TextUtils.isEmpty(TvSubject) || TvSubject.equals("选择科目") ||
                TextUtils.isEmpty(TvKnowledge) || TvKnowledge.equals("选择知识点") ||
                TextUtils.isEmpty(TvQuestions) || TvQuestions.equals("选择题库")) {
            return false;
        } else {
            return true;
        }
    }
}
