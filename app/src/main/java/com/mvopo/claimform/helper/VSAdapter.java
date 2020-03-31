package com.mvopo.claimform.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.mvopo.claimform.R;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class VSAdapter extends PagerAdapter {

    private Context mContext;
    private ViewGroup.LayoutParams chartParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

    private JSONObject vitalSign = null;

    private TextView valueTv;

    public VSAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public VSAdapter(Context mContext, JSONObject vitalSign) {
        this.mContext = mContext;
        this.vitalSign = vitalSign;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vital_sign_item, container, false);

        TextView labelTv, metricTv;
        ImageView iconIv;
        RelativeLayout chartContainerRl;

        labelTv = view.findViewById(R.id.vs_item_label);
        valueTv = view.findViewById(R.id.vs_item_value);
        metricTv = view.findViewById(R.id.vs_item_metric);
        iconIv = view.findViewById(R.id.vs_item_icon);
        chartContainerRl = view.findViewById(R.id.vs_item_chart_container);

        switch (position) {
            case 0: //Blood Pressure
                labelTv.setText(R.string.blood_pressure);
                valueTv.setText(R.string.blood_pressure_value);
                metricTv.setText(R.string.blood_pressure_metric);

                iconIv.setImageResource(R.drawable.blood_pressure_icon);

                chartContainerRl.addView(getBarChart());
                break;

            case 1: //Body Temp
                labelTv.setText(R.string.body_temp);
                valueTv.setText(R.string.body_temp_value);
                metricTv.setText(R.string.body_temp_metric);

                iconIv.setImageResource(R.drawable.body_temp_icon);

                List<Entry> temps = new ArrayList<>();

                temps.add(new Entry(1f, 33));
                temps.add(new Entry(2f, 38));
                temps.add(new Entry(3f, 37));
                temps.add(new Entry(4f, 39));

                try {
                    if (vitalSign != null)
                        temps.add(new Entry(5f, Integer.parseInt(vitalSign.getString("temp"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(temps, "Body Temp", mContext.getResources().getString(R.string.body_temp_metric)));
                break;

            case 2: //Resp Rate
                labelTv.setText(R.string.resp_rate);
                valueTv.setText(R.string.resp_rate_value);
                metricTv.setText(R.string.resp_rate_metric);

                iconIv.setImageResource(R.drawable.resp_rate_icon);

                List<Entry> resp_rates = new ArrayList<>();

                resp_rates.add(new Entry(1f, 10));
                resp_rates.add(new Entry(2f, 13));
                resp_rates.add(new Entry(3f, 10));
                resp_rates.add(new Entry(4f, 12));

                try {
                    if (vitalSign != null)
                        resp_rates.add(new Entry(5f, Integer.parseInt(vitalSign.getString("resp_rate"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(resp_rates, "Resp Rate", mContext.getResources().getString(R.string.resp_rate_metric)));
                break;

            case 3: //Pulse Rate
                labelTv.setText(R.string.pulse_rate);
                valueTv.setText(R.string.pulse_rate_value);
                metricTv.setText(R.string.pulse_rate_metric);

                iconIv.setImageResource(R.drawable.pulse_rate_icon);

                List<Entry> pulse_rates = new ArrayList<>();

                pulse_rates.add(new Entry(1f, 90));
                pulse_rates.add(new Entry(2f, 87));
                pulse_rates.add(new Entry(3f, 85));
                pulse_rates.add(new Entry(4f, 88));

                try {
                    if (vitalSign != null)
                        pulse_rates.add(new Entry(5f, Integer.parseInt(vitalSign.getString("pulse_rate"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(pulse_rates, "Pulse Rate", mContext.getResources().getString(R.string.pulse_rate_metric)));
                break;

            case 4: //Height
                labelTv.setText(R.string.height);
                valueTv.setText(R.string.height_value);
                metricTv.setText(R.string.height_metric);

                iconIv.setImageResource(R.drawable.height_icon);

                List<Entry> heights = new ArrayList<>();

                heights.add(new Entry(1f, 169));
                heights.add(new Entry(2f, 170));
                heights.add(new Entry(3f, 170));
                heights.add(new Entry(4f, 170));

                try {
                    if (vitalSign != null)
                        heights.add(new Entry(5f, Integer.parseInt(vitalSign.getString("height"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(heights, "Height", mContext.getResources().getString(R.string.height_metric)));
                break;

            case 5: //Weight
                labelTv.setText(R.string.weight);
                valueTv.setText(R.string.weight_value);
                metricTv.setText(R.string.weight_metric);

                iconIv.setImageResource(R.drawable.weight_icon);

                List<Entry> weights = new ArrayList<>();

                weights.add(new Entry(1f, 60));
                weights.add(new Entry(2f, 63));
                weights.add(new Entry(3f, 63));
                weights.add(new Entry(4f, 65));

                try {
                    if (vitalSign != null)
                        weights.add(new Entry(5f, Integer.parseInt(vitalSign.getString("weight"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(weights, "Weight", mContext.getResources().getString(R.string.weight_metric)));
                break;

            case 6: //BMI
                labelTv.setText(R.string.bmi);
                valueTv.setText(R.string.bmi_value);
                metricTv.setText("");

                iconIv.setImageResource(R.drawable.body_mass_icon);

                List<Entry> bmi = new ArrayList<>();

                bmi.add(new Entry(1f, 21));
                bmi.add(new Entry(2f, 21));
                bmi.add(new Entry(3f, 21));
                bmi.add(new Entry(4f, 22));

                try {
                    if (vitalSign != null)
                        bmi.add(new Entry(5f, Integer.parseInt(vitalSign.getString("bmi"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(bmi, "BMI", ""));
                break;

            case 7: //Pain Score
                labelTv.setText(R.string.pain_score);
                valueTv.setText(R.string.pain_score_value);
                metricTv.setText("");

                iconIv.setImageResource(R.drawable.pain_score_icon);

                List<Entry> score = new ArrayList<>();

                score.add(new Entry(1f, 3));
                score.add(new Entry(2f, 1));
                score.add(new Entry(3f, 5));
                score.add(new Entry(4f, 2));

                try {
                    if (vitalSign != null)
                        score.add(new Entry(5f, Integer.parseInt(vitalSign.getString("pain_score"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(score, "Pain Score", ""));
                break;

            case 8: //O2 Sat
                labelTv.setText(R.string.o2_sat);
                valueTv.setText(R.string.o2_sat_value);
                metricTv.setText("");

                iconIv.setImageResource(R.drawable.o2_sat_icon);

                List<Entry> sat = new ArrayList<>();

                sat.add(new Entry(1f, 90));
                sat.add(new Entry(2f, 83));
                sat.add(new Entry(3f, 87));
                sat.add(new Entry(4f, 90));

                try {
                    if (vitalSign != null)
                        sat.add(new Entry(5f, Integer.parseInt(vitalSign.getString("o2_sat"))));
                } catch (Exception e) {
                }

                chartContainerRl.addView(getLineChart(sat, "O2 Sat", ""));
                break;
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public BarChart getBarChart() {
        BarChart barChart = new BarChart(mContext);
        barChart.setLayoutParams(chartParam);

        List<String> barLabel = new ArrayList<>();
        barLabel.add("06/10/19");
        barLabel.add("06/24/19");
        barLabel.add("07/02/19");
        barLabel.add("07/10/19");

        try {
            if (vitalSign != null) barLabel.add(vitalSign.getString("date"));
        } catch (Exception e) {
        }

        List<BarEntry> systolicEntry = new ArrayList<>();
        List<BarEntry> diastolicEntry = new ArrayList<>();

        systolicEntry.add(new BarEntry(0, 110));
        systolicEntry.add(new BarEntry(1, 100));
        systolicEntry.add(new BarEntry(2, 105));
        systolicEntry.add(new BarEntry(3, 90));

        try {
            if (vitalSign != null)
                systolicEntry.add(new BarEntry(4, Integer.parseInt(vitalSign.getString("bp_sys"))));
        } catch (Exception e) {
        }


        diastolicEntry.add(new BarEntry(0, 87));
        diastolicEntry.add(new BarEntry(1, 90));
        diastolicEntry.add(new BarEntry(2, 85));
        diastolicEntry.add(new BarEntry(3, 65));

        try {
            if (vitalSign != null)
                diastolicEntry.add(new BarEntry(4, Integer.parseInt(vitalSign.getString("bp_dias"))));
        } catch (Exception e) {
        }

        BarDataSet systolicSet = new BarDataSet(systolicEntry, "Systolic");
        systolicSet.setColor(mContext.getResources().getColor(R.color.green));

        BarDataSet diastolicSet = new BarDataSet(diastolicEntry, "Diastolic");
        diastolicSet.setColor(mContext.getResources().getColor(R.color.red));
        diastolicSet.setValueTextColor(mContext.getResources().getColor(R.color.white));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(systolicSet);
        dataSets.add(diastolicSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("#");
                return mFormat.format(value);
            }
        });

        barChart.setData(data);

        Legend legend = barChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(14);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barLabel));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setTypeface(Typeface.DEFAULT);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0);
        barChart.getAxisRight().setEnabled(false);

        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();

        String latestVal = (int) systolicEntry.get(systolicEntry.size() - 1).getY() +
                "/" + (int) diastolicEntry.get(diastolicEntry.size() - 1).getY();
        valueTv.setText(latestVal);

        return barChart;
    }

    public LineChart getLineChart(List<Entry> entries, String setLabel, String metric) {
        List<String> lineLabel = new ArrayList<>();
        lineLabel.add("");
        lineLabel.add("06/10/19");
        lineLabel.add("06/24/19");
        lineLabel.add("07/02/19");
        lineLabel.add("07/10/19");

        try {
            if (vitalSign != null) lineLabel.add(vitalSign.getString("date"));
        } catch (Exception e) {
        }

        LineChart lineChart = new LineChart(mContext);
        lineChart.setLayoutParams(chartParam);

        LineDataSet dataSet = new LineDataSet(entries, setLabel);

        LineData data = new LineData(dataSet);
        data.setValueTextSize(10f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("#");
                return mFormat.format(value) + metric;
            }
        });


        lineChart.setData(data);

        Legend legend = lineChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(14);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0.8f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lineLabel));

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setTypeface(Typeface.DEFAULT);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawGridLines(false);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("#");
                return mFormat.format(value);
            }
        });

        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();

        String latestVal = (int) entries.get(entries.size() - 1).getY() + "";
        valueTv.setText(latestVal);
        return lineChart;
    }
}
