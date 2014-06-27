package com.michael;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.GridView;
import android.widget.TextView;
import com.kiritor.R;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends Activity implements MultiChoiceModeListener {

    private NoScrollGridView mGridView;
    private NoScrollListView mListView;
    private GridAdapter mGridAdapter;
    private TextView mActionText;
    private static final int MENU_SELECT_ALL = 0;
    private static final int MENU_UNSELECT_ALL = MENU_SELECT_ALL + 1;
    private Map<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();

    private int[] mImgIds = new int[]{R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_5,
            R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_5,
            R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_5,
            R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_3,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_5};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mGridView = (NoScrollGridView) findViewById(R.id.gridview);
        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);// 设置为多选模式
        mGridAdapter = new GridAdapter(this);
        mGridAdapter.setAdapterData(mImgIds, mSelectMap);
        mGridView.setAdapter(mGridAdapter);// 数据适配
        mGridView.setMultiChoiceModeListener(this);// 设置多选模式监听器


/*        mListView = (NoScrollListView) findViewById(R.id.listview);
        mListView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);// 设置为多选模式
        mGridAdapter = new GridAdapter(this);
        mGridAdapter.setAdapterData(mImgIds, null);
        mListView.setAdapter(mGridAdapter);// 数据适配*/


    }

    /**
     * Override MultiChoiceModeListener start *
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        // 得到布局文件的View
        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_layout,
                null);
        mActionText = (TextView) v.findViewById(R.id.action_text);
        // 设置显示内容为GridView选中的项目个数
        mActionText.setText(formatString(mGridView.getCheckedItemCount()));
        // 设置动作条的视图
        mode.setCustomView(v);
        // 得到菜单
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        /* 初始状态下,如果选中的项数不等于总共的项数,设置"全选"的状态为True */
        menu.getItem(MENU_SELECT_ALL).setEnabled(
                mGridView.getCheckedItemCount() != mGridView.getCount());
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // TODO Auto-generated method stub
        /*
		 * 当点击全选的时候,全选 点击全不选的时候全不选
		 */
        switch (item.getItemId()) {
            case R.id.menu_select:
                for (int i = 0; i < mGridView.getCount(); i++) {
                    mGridView.setItemChecked(i, true);
                    mSelectMap.put(i, true);
                }
                break;
            case R.id.menu_unselect:
                for (int i = 0; i < mGridView.getCount(); i++) {
                    mGridView.setItemChecked(i, false);
                    mSelectMap.clear();
                }
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // TODO Auto-generated method stub
        mGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position,
                                          long id, boolean checked) {
        // TODO Auto-generated method stub
        // 当每个项状态改变的时候的操作
        mActionText.setText(formatString(mGridView.getCheckedItemCount()));
        mSelectMap.put(position, checked);/* 放入选中的集合中 */
        mode.invalidate();
    }

    /**
     * Override MultiChoiceModeListener end *
     */

    private String formatString(int count) {
        return String.format(getString(R.string.selection), count);
    }


}