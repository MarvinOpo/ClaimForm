package com.mvopo.claimform.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mvopo.claimform.R;
import com.mvopo.claimform.contracts.CalendarContract;
import com.mvopo.claimform.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private int[] colors;

    private CalendarContract.orderView orderView;

    public OrderAdapter(Context context, ArrayList<Order> orders, CalendarContract.orderView orderView) {
        this.orders = orders;
        this.orderView = orderView;

        colors = context.getResources().getIntArray(R.array.color_backgrounds);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.doctorTv.setText(order.getDrname());
        holder.orderTv.setText(order.getOrder());
        holder.departmentTv.setText(order.getDepartment());

        String dateFrom = order.getDateFrom();
        String dateTo = order.getDateTo();

        if(!order.getDateFrom().equals(order.getDateTo())) {
            String date = dateFrom.substring(0, 5) + " - " + dateTo.substring(0, 5);
            holder.dateTv.setText(date);
        }else{
            holder.dateTv.setText(dateFrom.substring(0,5));
        }

        int bgcolor = colors[4];
        switch (order.getDepartment()){
            case "Laboratory":
                bgcolor = colors[0];
                break;
            case "Pharmacy":
                bgcolor = colors[1];
                break;
            case "Ultrasound":
                bgcolor = colors[2];
                break;
            case "X-Ray":
                bgcolor = colors[3];
                break;
        }

        holder.orderContainerCl.setBackgroundColor(bgcolor);

        holder.orderDoneBtn.setOnClickListener(view -> {
            holder.orderDoneBtn.setVisibility(View.GONE);
            orderView.updateDateDecorator();
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView doctorTv, orderTv, dateTv, departmentTv;
        ConstraintLayout orderContainerCl;
        ImageView orderDoneBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorTv = itemView.findViewById(R.id.order_dr_tv);
            orderTv = itemView.findViewById(R.id.order_body_tv);
            dateTv = itemView.findViewById(R.id.order_date_tv);
            departmentTv = itemView.findViewById(R.id.order_dept_tv);
            orderDoneBtn = itemView.findViewById(R.id.order_done_btn_iv);

            orderContainerCl = itemView.findViewById(R.id.order_container);
        }
    }
}
