package ba.unsa.etf.rma.rma20babicamina92.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.presenters.GraphsPresenter;
import ba.unsa.etf.rma.rma20babicamina92.providers.AdapterProvider;


public class GraphsFragment extends Fragment {

    private BarChart paymentChart;
    private BarChart incomeChart;
    private BarChart allChart;
    private GraphsPresenter presenter;
    private Spinner intervalSpinner;
    private ArrayAdapter<String> intervalSpinnerAdapter;
    private ArrayList<String> intervals = new ArrayList<>(Arrays.asList("Month", "Week", "Day"));

    public GraphsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        paymentChart = view.findViewById(R.id.paymentChart);
        incomeChart = view.findViewById(R.id.incomeChart);
        allChart = view.findViewById(R.id.allChart);
        intervalSpinner = view.findViewById(R.id.intervalSpinner);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initializeBarChart(paymentChart);
        initializeBarChart(incomeChart);
        initializeBarChart(allChart);

        presenter = GraphsPresenter.getInstance();
        presenter.init(this);
        intervalSpinnerAdapter = AdapterProvider.provideIntervalSpinnerAdapter(getActivity(), intervals);
        intervalSpinner.setAdapter(intervalSpinnerAdapter);
        intervalSpinner.setSelection(0);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setInterval((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private static void initializeBarChart(BarChart paymentChart) {
        paymentChart.setDrawBarShadow(false);
        paymentChart.setDrawValueAboveBar(true);
        paymentChart.getDescription().setEnabled(false);
        paymentChart.setPinchZoom(false);
        paymentChart.setDrawGridBackground(false);
        ValueFormatter xAxisFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%d", (int) (value));
            }
        };
        XAxis xAxis = paymentChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setAxisMinimum(0.5f);


        YAxis leftAxis = paymentChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.2f KM", value);
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        Legend l = paymentChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(25f);
        l.setXEntrySpace(4f);

    }


    public void setPaymentChartData(BarData barData) {
        reloadBarChart(barData, paymentChart);
    }

    private static void reloadBarChart(BarData barData, BarChart chart) {
        chart.setData(barData);
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
    }

    public void setIncomeChartData(BarData barData) {
        reloadBarChart(barData, incomeChart);
    }

    public void setAllChartData(BarData barData) {
        reloadBarChart(barData, allChart);
    }
}
