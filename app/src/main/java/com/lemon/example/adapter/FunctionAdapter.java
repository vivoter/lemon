package com.lemon.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.LemonContext;
import com.lemon.LemonMessage;
import com.lemon.bean.BeanFactory;
import com.lemon.example.R;
import com.lemon.example.model.FunctionModel;
import com.lemon.util.ParamUtils;

import java.util.List;
import java.util.UUID;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.adapter]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/1 21:29]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/1 21:29]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class FunctionAdapter extends BaseAdapter {

    private List<FunctionModel> models;
    private Handler handler;
    private Context mContext;
    private LayoutInflater mInflater;

    public FunctionAdapter(Handler handler, Context context,
                           List<FunctionModel> models) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.handler = handler;
        this.mContext = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return ParamUtils.isEmpty(models) ? 0 : models.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final FunctionModel model = models.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.function_item, null);
            holder = new ViewHolder();
            holder.tvFunction = (TextView) convertView
                    .findViewById(R.id.tvFunction);

            holder.tvFunction.setText(model.getName());
            holder.tvFunction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LemonContext.getBean(LemonMessage.class).sendMessage(model.getActivityCls().getName());
                    Intent intent = new Intent(mContext,model.getActivityCls());
                    ((LemonActivity)mContext).startNextActivity(intent, false);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tvFunction;
    }
}
