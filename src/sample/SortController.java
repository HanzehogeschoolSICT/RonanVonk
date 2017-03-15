package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.util.*;

public class SortController {

    @FXML

    Slider slider;

    @FXML
    TextField tf;

    @FXML
    CategoryAxis xAxis;

    @FXML
    NumberAxis yAxis;

    @FXML
    BarChart<String, Number> bc;

    @FXML
    ComboBox<String> cbb;

    XYChart.Series series = new XYChart.Series();

    ArrayList<Integer> list = new ArrayList<Integer>();
    boolean isSorted = false;
    int step = 0;


    public void initialize() {
        cbb.getItems().addAll("Bubblesort", "Insertsort", "Quicksort");
        xAxis.setLabel("Array Index");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Value");
        generateArray();
    }

    public void generateArray() {
        for( int i=0; i < new Random().nextInt(20); i++) {
            list.add(new Random().nextInt(30));
            updateChart();
        }
    }


    public void bubbleSort() {
        if (isSorted()) {
            tf.setText("Step count: " + step + ", The array is sorted.");
            return;
        }
        else
            tf.setText("Step count: " + step + ", The array is not yet sorted.");
        if((step + 1 <= list.size() - 1)) {
            if ((list.get(step) > list.get(step + 1))) {
                Collections.swap(list, step, step + 1);
                //Swap the elements if the latter one is smaller.
            }
        }
        else {
            step = 0;
            return;
        }
        step++;
        updateChart();

    }

    public void insertSort() {
        if (isSorted()) {
            tf.setText("Step count: " + step + ", The array is sorted.");
            return;
        }
        else
            tf.setText("Step count: " + step + ", The array is not yet sorted.");

        int j = list.size() -1 - step;
        if(j == -1) {
            step = 0;
            j = list.size() -1;
        }
        if(step >= 0 && list.get(j - 1) > list.get(j)) {
            Collections.swap(list, j, j -1);
        }

        updateChart();
        step++;
        return;
    }

    public void quickSort() {
        System.out.println("Not yet implemented");
    }

    public void step() {
        String value = cbb.getValue();
        if(value == null) {
            tf.setText("Select an algorithm");
            return;
        }
        switch (value) {
            case "Bubblesort" : bubbleSort();
                break;
            case "Insertsort": insertSort();
                break;
            case "Quicksort": quickSort();
                break;

        }
    }

    public void updateChart() {
        series = new XYChart.Series();
        for(int i=0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i),(Integer) list.get(i)));
        }
        bc.getData().clear();
        bc.getData().add(series);
    }

    public void timedSort() {
        if(cbb.getValue() == null) {
            tf.setText("Select an algorithm");
            return;
        }
        long interval = (long) slider.getValue();
        while (!isSorted) {
            try {
                step();
                updateChart();
                Thread.sleep(interval);
            }

            catch (java.lang.InterruptedException ex) {
                tf.setText("An error occured: " + ex.toString());
            }

        }
    }

    boolean isSorted() {
        boolean sorted = true;
        for (int i = 1; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i - 1)) {
                isSorted = false;
                return false;
            }
        }
        isSorted = true;
        return true;
    }

    public void resetStep() {
        step = 0;
    }

}
