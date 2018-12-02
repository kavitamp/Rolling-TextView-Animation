package textanimation.com.rollingtextanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String textSizeArray[] = new String[]{"20", "25", "30", "35", "40", "45"};
    final String textStyle[] = new String[]{"Bold", "Normal"};
    int[] animatingNumbers = {1, 6, 9, 5};
    LinearLayout linearLayout;
    Button tryAgain;
    TextView[] myTextViews, myTextViewsOut;
    RelativeLayout childLayout;
    LinearLayout.LayoutParams linearParams;
    TextView rowTextView, rowTextViewOut;
    EditText editText, animationDurationET, gapBetweenTwoNos;
    Spinner textSizeSpinner, textStyleSpinner;
    TextAnimationFields textAnimationFields;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textAnimationFields = new TextAnimationFields();

        editText = findViewById(R.id.editText);
        animationDurationET = findViewById(R.id.animationDurationET);
        gapBetweenTwoNos = findViewById(R.id.gapBetweenTwoNos);
        tryAgain = findViewById(R.id.tryAgain);
        textSizeSpinner = findViewById(R.id.textSizeSpinner);
        textStyleSpinner = findViewById(R.id.textStyleSpinner);
        linearLayout = findViewById(R.id.linearLayout);


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, textSizeArray);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textSizeSpinner.setAdapter(stringArrayAdapter);

        ArrayAdapter<String> textStyleArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, textStyle);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textStyleSpinner.setAdapter(textStyleArrayAdapter);

        textSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textAnimationFields.setTextSize(Integer.parseInt(textSizeArray[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        textStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textAnimationFields.setTextStyleString("" + textStyle[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        createNumberOfTextFields();

        tryAgain.setOnClickListener(this);

    }


    public void createNumberOfTextFields() {

        myTextViews = new TextView[textAnimationFields.getMaxNumbers()]; // create an empty array;
        myTextViewsOut = new TextView[textAnimationFields.getMaxNumbers()]; // create an empty array;

        for (int i = 0; i < textAnimationFields.getMaxNumbers(); i++) {
            childLayout = new RelativeLayout(MainActivity.this);

            linearParams = new LinearLayout.LayoutParams((textAnimationFields.getTextSize() * 3),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayout.setBackgroundDrawable(getBaseContext().getResources().getDrawable(R.drawable.bg));
            linearParams.setMargins(30, 0, 30, 0);

            childLayout.setLayoutParams(linearParams);

            // create a new textview
            rowTextView = new TextView(this);
            rowTextViewOut = new TextView(this);

            rowTextView.setLayoutParams(new TableLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            rowTextViewOut.setLayoutParams(new TableLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            rowTextView.setGravity(Gravity.CENTER);
            rowTextViewOut.setGravity(Gravity.CENTER);

            childLayout.addView(rowTextViewOut, 0);
            childLayout.addView(rowTextView, 0);

            linearLayout.addView(childLayout);
            rowTextView.setTextSize((float) textAnimationFields.getTextSize());
            rowTextViewOut.setTextSize((float) textAnimationFields.getTextSize());
            rowTextView.setTextColor(Color.WHITE);
            rowTextViewOut.setTextColor(Color.WHITE);

            if (textAnimationFields.getTextStyleString().equals(textStyle[0])) {
                rowTextView.setTypeface(Typeface.DEFAULT_BOLD);
                rowTextViewOut.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                rowTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                rowTextViewOut.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }

            myTextViews[i] = rowTextView;
            myTextViewsOut[i] = rowTextViewOut;

            animateTexts(animatingNumbers[i], 0, myTextViews[i], myTextViewsOut[i]);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tryAgain:
                setEditTextFields();
                createNumberOfTextFields();
                break;

        }
    }

    public void setEditTextFields() {
        String editedTextNumber = editText.getText().toString().trim();
        String vvv = animationDurationET.getText().toString().trim();
        String gapBetweenTwoNumbersDurationString = gapBetweenTwoNos.getText().toString().trim();

        if (editedTextNumber.equals("")) {
            editText.setText("1695");
        }
        if (vvv.equals("")) {
            vvv = "" + 200;
            animationDurationET.setText("" + vvv);
        }

        if (gapBetweenTwoNumbersDurationString.equals("")) {
            gapBetweenTwoNumbersDurationString = "" + 100;
            gapBetweenTwoNos.setText("" + textAnimationFields.getGapBetweenTwoNumbersDuration());
        }

        textAnimationFields.setGapBetweenTwoNumbersDuration(Integer.parseInt(gapBetweenTwoNumbersDurationString));
        textAnimationFields.setAnimationDuration(Integer.parseInt(vvv));
        if (textAnimationFields.getAnimationDuration() < 100) {
            textAnimationFields.setAnimationDuration(100);
            Toast.makeText(getApplicationContext(), "We have set 100 as minimum animation time!", Toast.LENGTH_LONG).show();
        }

        linearLayout.removeAllViews();
        if (editedTextNumber.length() != 0) {
            textAnimationFields.setMaxNumbers(editedTextNumber.length());
            char NArray[] = editedTextNumber.toCharArray();
            animatingNumbers = new int[7];
            for (int i = 0; i < editedTextNumber.length(); i++) {
                animatingNumbers[i] = Integer.parseInt("" + NArray[i]);
            }
        } else {
            animatingNumbers = new int[]{1, 6, 9, 5};
            textAnimationFields.setMaxNumbers(animatingNumbers.length);
        }
    }

    public void animateTexts(final int actualNo, final int loopNo, final TextView textView, final TextView textViewOut) {
        textViewOut.setText(" " + loopNo + " ");
        textView.setVisibility(View.GONE);


        if (actualNo == loopNo) {
            textViewOut.setText(" " + actualNo + " ");
            textView.setText(" " + actualNo + " ");
            textView.setVisibility(View.VISIBLE);
        } else {
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.setInterpolator(new LinearInterpolator());
            animatorSet2.playTogether(
                    ObjectAnimator.ofFloat(textViewOut, "translationY", 0, (textAnimationFields.getTextSize() * 3))
            );
            animatorSet2.setDuration(textAnimationFields.getAnimationDuration());
            animatorSet2.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            animatorSet2.start();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setVisibility(View.VISIBLE);
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.playTogether(
                            ObjectAnimator.ofFloat(textView, "translationY", -(textAnimationFields.getTextSize() * 3), 0)
                    );
                    animatorSet2.setDuration(textAnimationFields.getAnimationDuration());
                    animatorSet2.setInterpolator(new LinearInterpolator());
                    animatorSet2.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            if (actualNo < loopNo)
                                textView.setText(" " + (loopNo - 1) + " ");
                            else
                                textView.setText(" " + (loopNo + 1) + " ");
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (actualNo < loopNo)
                                animateTexts(actualNo, loopNo - 1, textView, textViewOut);
                            else
                                animateTexts(actualNo, loopNo + 1, textView, textViewOut);
                        }
                    });
                    animatorSet2.start();
                }
            }, textAnimationFields.getGapBetweenTwoNumbersDuration());
        }

    }
}
