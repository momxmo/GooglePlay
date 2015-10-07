package mo.com.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mo.com.googleplay.R;

/**
 * 作者：MoMxMo on 2015/10/5 12:14
 * 邮箱：xxxx@qq.com
 */


public class TabFragment extends Fragment {

    private String content;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item, container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        content = getArguments().getString("content");
        TextView tvContent = (TextView) view.findViewById(R.id.tv_tab_content);
        tvContent.setText(content + "");
    }
}