package com.mvopo.claimform.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mvopo.claimform.R;
import com.mvopo.claimform.contracts.CalendarContract;
import com.mvopo.claimform.helper.EventDecorator;
import com.mvopo.claimform.helper.OrderAdapter;
import com.mvopo.claimform.models.Constants;
import com.mvopo.claimform.models.Order;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PatientCalendarFragment extends Fragment implements CalendarContract.orderView, DatePickerDialog.OnDateSetListener {

    private MaterialCalendarView calendarView;
    private TextView currentDayTv;
    private RecyclerView orderRv;

    private Calendar calendar;

    private ArrayList<Order> mainOrders = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private EditText dateFromEdtx, dateToEdtx;

    private String dateQuery="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        String[] department = {
                "Laboratory",
                "X-Ray"
        };

        String[] drNames = {
                "Dr. Quack",
                "Dr. Iamsick"
        };

        String[] orderBody = {
                "Regular checking of patient for atleast once an hour",
                "Daily intake of medicine at 3PM with 500ml dosage"
        };

        String[] dateFrom = {
                "07/01/2019"
        };

        String[] dateTo = {
                "07/01/2019",
                "07/02/2019"
        };

        for (int i = 0; i < 2; i++) {
            Order order = new Order();
            order.setDepartment(department[i]);
            order.setDrname(drNames[i]);
            order.setOrder(orderBody[i]);
            order.setDateFrom(dateFrom[0]);
            order.setDateTo(dateTo[i]);

            if(i == 0) order.setTags(dateFrom[0]);
            else order.setTags(dateFrom[0] + "," + dateTo[1]);

            mainOrders.add(order);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_calendar, container, false);

        calendar = Calendar.getInstance();

        calendarView = view.findViewById(R.id.calendar_view);
        currentDayTv = view.findViewById(R.id.current_day_tv);
        orderRv = view.findViewById(R.id.order_recycler);

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            calendar.set(date.getYear(), (date.getMonth() - 1), date.getDay());
            setCurrentDay(calendar);

            orderAdapter.notifyDataSetChanged();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        orderRv.setLayoutManager(layoutManager);

        ArrayList<String> todo = new ArrayList<>();
        todo.add("07/01/2019");
        todo.add("07/02/2019");

        calendarView.addDecorator(new EventDecorator(getContext().getDrawable(R.drawable.todo_icon), todo));

        setCurrentDay(calendar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_order, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showAddOrderDialog();
        return super.onOptionsItemSelected(item);
    }

    public void setCurrentDay(Calendar calendar) {
        String currentDay = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + " - " +
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        dateQuery = String.format(Locale.getDefault(),"%02d", calendar.get(Calendar.MONTH) + 1) +
                "/" + String.format(Locale.getDefault(),"%02d", calendar.get(Calendar.DAY_OF_MONTH)) +
                "/" + calendar.get(Calendar.YEAR);

        orders.clear();

        for(int i = 0; i < mainOrders.size(); i++){
            if(mainOrders.get(i).getTags().contains(dateQuery)){
                orders.add(mainOrders.get(i));
            }
        }

        currentDayTv.setText(currentDay);

        orderAdapter = new OrderAdapter(getContext(), orders, this);
        orderRv.setAdapter(orderAdapter);
    }

    public void showAddOrderDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_order_dialog, null);
        dateFromEdtx = dialogView.findViewById(R.id.order_date_from_edtx);
        dateToEdtx = dialogView.findViewById(R.id.order_date_to_edtx);
        EditText orderEdtx = dialogView.findViewById(R.id.order_desc_edtx);
        EditText orderDeptEdtx = dialogView.findViewById(R.id.order_dept_edtx);
        Button addBtn = dialogView.findViewById(R.id.order_add_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        calendar = Calendar.getInstance();

        dateFromEdtx.setOnClickListener(view -> {
            calendar = Calendar.getInstance();

            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PatientCalendarFragment.this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setMinDate(calendar);
            dpd.show(getActivity().getSupportFragmentManager(), "date_from");
        });

        dateToEdtx.setOnClickListener(view -> {
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PatientCalendarFragment.this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setMinDate(calendar);
            dpd.show(getActivity().getSupportFragmentManager(), "date_to");
        });

        addBtn.setOnClickListener(view -> {
            String dateFrom = dateFromEdtx.getText().toString().trim();
            String dateTo = dateToEdtx.getText().toString().trim();
            String orderBody = orderEdtx.getText().toString().trim();
            String dept = orderDeptEdtx.getText().toString().trim();

            if (dateFrom.isEmpty()) {
                Toast.makeText(getContext(), "Please select order date.", Toast.LENGTH_SHORT).show();
            } else if (orderBody.isEmpty()) {
                Toast.makeText(getContext(), "Please add some order content.", Toast.LENGTH_SHORT).show();
            } else if (dept.isEmpty()) {
                Toast.makeText(getContext(), "Please specify order department.", Toast.LENGTH_SHORT).show();
            }

            ArrayList<String> orderDates = Constants.getInclusiveDates(dateFrom, dateTo);
            calendarView.addDecorator(new EventDecorator(getContext().getDrawable(R.drawable.todo_icon), orderDates));

            Order order = new Order();
            order.setOrder(orderBody);
            order.setDateFrom(dateFrom);
            order.setDateTo(dateTo);
            order.setDepartment(dept);
            order.setDrname("Dr. Zero");
            order.setTags(TextUtils.join(",", orderDates));

            mainOrders.add(0, order);

            if(order.getTags().contains(dateQuery)) orders.add(order);

            orderAdapter.notifyDataSetChanged();

            dialog.dismiss();
            Toast.makeText(getContext(), "Order successfully added.", Toast.LENGTH_SHORT).show();
        });


        orderDeptEdtx.setOnClickListener(view1 -> showOptionDialog(R.array.order_departments, orderDeptEdtx));
    }

    public void showOptionDialog(int resource, EditText edtxtView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.option_dialog, null);
        ScrollView optionHolder = view.findViewById(R.id.option_holder);
        Button optionBtn = view.findViewById(R.id.optionBtn);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        final RadioGroup radioGroup = new RadioGroup(getContext());

        LinearLayout.LayoutParams rbParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rbParam.bottomMargin = 5;
        rbParam.topMargin = 5;

        String[] labels = getContext().getResources().getStringArray(resource);

        for (int i = 0; i < labels.length; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setLayoutParams(rbParam);
            radioButton.setText(labels[i]);

            View lineView = new View(getContext());
            lineView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            lineView.setLayoutParams(params);

            radioGroup.addView(radioButton);
            radioGroup.addView(lineView);
        }

        //((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        optionHolder.addView(radioGroup);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        final AlertDialog optionDialog = builder.create();
        optionDialog.show();

        optionBtn.setOnClickListener(view1 -> {
            RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            if (radioButton != null) {
                edtxtView.setText(radioButton.getText());
            }

            optionDialog.dismiss();
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.format(Locale.getDefault(),"%02d", (monthOfYear + 1)) +
                "/" + String.format(Locale.getDefault(),"%02d", dayOfMonth) +
                "/" + year;

        switch (view.getTag()){
            case "date_from":
                dateFromEdtx.setText(date);
                dateToEdtx.setText(date);
                calendar.set(year, monthOfYear, dayOfMonth);
                break;
            case "date_to":
                dateToEdtx.setText(date);
                break;
        }
    }

    @Override
    public void updateDateDecorator() {
        CalendarDay calendarDay = calendarView.getSelectedDate();

        String selectedDate = String.format(Locale.getDefault(),"%02d", calendarDay.getMonth()) +
                "/" + String.format(Locale.getDefault(),"%02d", calendarDay.getDay()) +
                "/" + calendarDay.getYear();

        ArrayList<String> dates = new ArrayList<>();
        dates.add(selectedDate);
        calendarView.addDecorator(new EventDecorator(getContext().getDrawable(R.drawable.todo_done_icon), dates));
    }
}
