package duc.workmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import duc.workmanager.R;

/**
 * Created by Duc on 5/17/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.RecyclerViewHolderDetail> {
    private ArrayList list;
    private Context context;

    public DetailAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerViewHolderDetail onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_detail, viewGroup, false);
        return new RecyclerViewHolderDetail(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderDetail holder, int position) {
        Detail detail = new Detail();
        holder.txtTittle.setText(detail.getDetailTittle());
        holder.txtContent.setText(detail.getDetailContent());
    }

    public class RecyclerViewHolderDetail extends RecyclerView.ViewHolder {

        public TextView txtTittle, txtContent;
        public CheckBox checkDetail;

        // for show menu edit and delete
        private PopupMenu popupMenu;

        public RecyclerViewHolderDetail(View itemView) {
            super(itemView);

            txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            txtContent = (TextView) itemView.findViewById(R.id.txt_content);
            checkDetail = (CheckBox) itemView.findViewById(R.id.checkbox_detail);
            checkDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    txtTittle.setTextColor(Color.GRAY);
                    txtContent.setTextColor(Color.GRAY);
                }
            });
        }
    }
}