package comdadur604.github.muondetector;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {


    public GraphFragment() {
        // Required empty public constructor
    }

    Calendar calendar = null;
    GraphView graph = null;
    LineGraphSeries<DataPoint> series = null;
    Random rand = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rand = new Random();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph, container, false);

    }

    private int randomValue(){
        if (rand.nextFloat() > .9){
            return 5;
        } else if (rand.nextFloat() > .8){
            return 3;
        } else {
            return 0;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        series = new LineGraphSeries<>(new DataPoint[] {});
        // generate Dates
        calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        for(int i=0; i<20; i++){
            calendar.add(Calendar.MINUTE, 1);
            series.appendData(new DataPoint(calendar.getTime(), randomValue()), true, 100);
        }

        graph = (GraphView) getView().findViewById(R.id.graph);

        GridLabelRenderer glr = graph.getGridLabelRenderer();

        glr.setHorizontalAxisTitle("Time");
        glr.setVerticalAxisTitle("Intensity");
        glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        glr.setHumanRounding(false);
        glr.setPadding(50);

        Viewport vp = graph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(series.getLowestValueX());
        vp.setMaxX(series.getHighestValueX());
        vp.setYAxisBoundsManual(true);
        vp.setMinY(0);
        vp.setMaxY(5);




        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        graph.addSeries(series);



    }

}
