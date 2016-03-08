package com.fydp.myoralvillage;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Usama on 2/22/2016.
 */
public class Level3ActivityGameExactChange extends AppCompatActivity {


    //text views being dragged and dropped onto
    public ImageView item, imageSandbox, bill500Snap, bill1000Snap, bill2000Snap, bill5000Snap, bill10000Snap, paid500, paid1000, paid2000, paid5000, paid10000, bill500, bill1000, bill2000, bill5000, bill10000;
    public int num500, num1000, num2000, num5000, num10000, totalCash, qNum;
    public TextView cashView, num500view, num1000view, num2000view, num5000view, num10000view, paid500view, paid1000view, paid2000view, paid5000view, paid10000view;
    int[] questions = {R.drawable.bananas, R.drawable.basket_fish, R.drawable.basketoranges, R.drawable.basketpears, R.drawable.bike, R.drawable.calculator, R.drawable.chair, R.drawable.chicken, R.drawable.clock, R.drawable.corn, R.drawable.flipflops, R.drawable.pencil, R.drawable.popcan, R.drawable.shirt, R.drawable.mobilephone};
    int[] answers = {500, 500, 1000, 2000, 1000, 1000, 1000, 1000, 1000, 500, 1000, 4500, 500, 1500, 1000};
    int[][] paidAmount = {{0, 1, 0, 0, 0}, {0, 1, 0, 1, 0}, {0, 0, 2, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 3, 1, 14}, {0, 0, 3, 0, 1}, {0, 0, 3, 1, 4}, {0, 0, 3, 1, 0}, {0, 0, 3, 1, 1}, {0, 1, 1, 0, 0}, {0, 0, 3, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 2}, {0, 0, 3, 1, 1}};
    public boolean userHasViewedDemo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gameexactchange);
        if (!userHasViewedDemo) {
            startDemo();
        }
        cashView = (TextView) findViewById(R.id.cashView);
        paid500view = (TextView) findViewById(R.id.paid500view);
        paid1000view = (TextView) findViewById(R.id.paid1000view);
        paid2000view = (TextView) findViewById(R.id.paid2000view);
        paid5000view = (TextView) findViewById(R.id.paid5000view);
        paid10000view = (TextView) findViewById(R.id.paid10000view);
        paid500 = (ImageView) findViewById(R.id.paid500);
        paid1000 = (ImageView) findViewById(R.id.paid1000);
        paid2000 = (ImageView) findViewById(R.id.paid2000);
        paid5000 = (ImageView) findViewById(R.id.paid5000);
        paid10000 = (ImageView) findViewById(R.id.paid10000);


        //get both sets of text views
        //views to drag
        bill500 = (ImageView) findViewById(R.id.bill500);
        bill1000 = (ImageView) findViewById(R.id.bill1000);
        bill2000 = (ImageView) findViewById(R.id.bill2000);
        bill5000 = (ImageView) findViewById(R.id.bill5000);
        bill10000 = (ImageView) findViewById(R.id.bill10000);


        //views to drop onto
        imageSandbox = (ImageView) findViewById(R.id.imageSandbox);


        //set touch listeners
        bill500.setOnTouchListener(new ChoiceTouchListener());
        bill1000.setOnTouchListener(new ChoiceTouchListener());
        bill2000.setOnTouchListener(new ChoiceTouchListener());
        bill5000.setOnTouchListener(new ChoiceTouchListener());
        bill10000.setOnTouchListener(new ChoiceTouchListener());


        //set drag listeners
        imageSandbox.setOnDragListener(new ChoiceDragListener());

        //initialize snap postiions
        bill500Snap = (ImageView) findViewById(R.id.bill500Snap);
        bill1000Snap = (ImageView) findViewById(R.id.bill1000Snap);
        bill2000Snap = (ImageView) findViewById(R.id.bill2000Snap);
        bill5000Snap = (ImageView) findViewById(R.id.bill5000Snap);
        bill10000Snap = (ImageView) findViewById(R.id.bill10000Snap);

        //setup question
        item = (ImageView) findViewById(R.id.item);
        setQuestion(qNum);

    }

    public void startDemo() {
               //method call to DemoActivity (separate activity)
                Intent intent = new Intent(this, Level3ActivityDemoExactChange.class);
        startActivity(intent);

    }

    public void setQuestion(int qNum) {
        paid500.setImageResource(R.drawable.black_background);
        paid1000.setImageResource(R.drawable.black_background);
        paid2000.setImageResource(R.drawable.black_background);
        paid5000.setImageResource(R.drawable.black_background);
        paid10000.setImageResource(R.drawable.black_background);
        item.setImageResource(questions[qNum]);
        paid500view.setText(String.valueOf(paidAmount[qNum][0]));
        if (paidAmount[qNum][0] > 0) {
            paid500.setImageResource(R.drawable.bill_500);
        }
        paid1000view.setText(String.valueOf(paidAmount[qNum][1]));
        if (paidAmount[qNum][1] > 0) {
            paid1000.setImageResource(R.drawable.bill_1000);
        }
        paid2000view.setText(String.valueOf(paidAmount[qNum][2]));
        if (paidAmount[qNum][2] > 0) {
            paid2000.setImageResource(R.drawable.bill_2000);
        }
        paid5000view.setText(String.valueOf(paidAmount[qNum][3]));
        if (paidAmount[qNum][3] > 0) {
            paid5000.setImageResource(R.drawable.bill_5000);
        }
        paid10000view.setText(String.valueOf(paidAmount[qNum][4]));
        if (paidAmount[qNum][4] > 0) {
            paid10000.setImageResource(R.drawable.bill_10000);
        }
        totalCash = 0;
        cashView.setText(String.valueOf(totalCash) + "/-Tsh");
        bill500Snap.setBackground(null);
        bill1000Snap.setBackground(null);
        bill2000Snap.setBackground(null);
        bill5000Snap.setBackground(null);
        bill10000Snap.setBackground(null);

    }

    public void resetBoard() {
        bill500Snap.setBackground(null);
        bill1000Snap.setBackground(null);
        bill2000Snap.setBackground(null);
        bill5000Snap.setBackground(null);
        bill10000Snap.setBackground(null);
        totalCash = 0;
        cashView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalCash)) + "/-Tsh");


    }

    public void checkAnswerPV(View v) {

        if (totalCash == answers[qNum]) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            ++qNum;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (qNum < 15) {
                setQuestion(qNum);
            } else {
                setQuestion(0);
            }

        } else {
            resetBoard();
        }

    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    private final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     */
    @SuppressLint("NewApi")
    public class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.VISIBLE);

                    //view being dragged and dropped

                    bill500Snap = (ImageView) findViewById(R.id.bill500Snap);
                    bill1000Snap = (ImageView) findViewById(R.id.bill1000Snap);
                    bill2000Snap = (ImageView) findViewById(R.id.bill2000Snap);
                    bill5000Snap = (ImageView) findViewById(R.id.bill5000Snap);
                    bill10000Snap = (ImageView) findViewById(R.id.bill10000Snap);


                    TextView cashView = (TextView) findViewById(R.id.cashView);
                    ImageView dropped = (ImageView) view;
                    String droppedId = dropped.getResources().getResourceName(dropped.getId());
                    //String boxId = imageBox1.getResources().getResourceName(imageBox1.getId());
                    System.out.println(droppedId);


                    //update the text in the target view to reflect the data being dropped
                    if (droppedId.equals("com.fydp.myoralvillage:id/bill500")) {
                        bill500Snap.setBackgroundResource(R.drawable.bill_500);
                        ++num500;
                        totalCash = totalCash + 500;
                        cashView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalCash)) + "/-Tsh");
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/bill1000")) {
                        bill1000Snap.setBackgroundResource(R.drawable.bill_1000);
                        ++num1000;
                        totalCash = totalCash + 1000;
                        cashView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalCash)) + "/-Tsh");

                    }


                    if (droppedId.equals("com.fydp.myoralvillage:id/bill2000")) {
                        bill2000Snap.setBackgroundResource(R.drawable.bill_2000);
                        ++num2000;
                        totalCash = totalCash + 2000;
                        cashView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalCash)) + "/-Tsh");
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/bill5000")) {
                        bill5000Snap.setBackgroundResource(R.drawable.bill_5000);
                        ++num5000;
                        totalCash = totalCash + 5000;
                        cashView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalCash)) + "/-Tsh");
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/bill10000")) {
                        bill10000Snap.setBackgroundResource(R.drawable.bill_10000);
                        ++num10000;
                        totalCash = totalCash + 10000;
                        cashView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalCash)) + "/-Tsh");
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
