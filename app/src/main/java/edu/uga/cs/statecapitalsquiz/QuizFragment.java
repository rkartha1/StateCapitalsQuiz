package edu.uga.cs.statecapitalsquiz;




import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;




public class QuizFragment extends Fragment {




    String TAG = "FRAG";
    private List<QuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private QuizQuestions currentQuestion;
    private TextView questionText;
    private RadioGroup choicesRadioGroup;
    private RadioButton choice1, choice2, choice3;
    public int win = 0;
    private String userAnswer = null; // Store the user's answer
    private OnSwipeTouchListener swipeListener;




    private QuizQuestionsData quizQuestionsData;




    public QuizFragment() {
        // Required empty public constructor
    }




    public static QuizFragment newInstance(int versionNum) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizQuestionsData = new QuizQuestionsData(getActivity());
        quizQuestionsData.open();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        // Initialize views
        questionText = view.findViewById(R.id.question_text);
        choicesRadioGroup = view.findViewById(R.id.choices_radio_group);
        choice1 = view.findViewById(R.id.choice1);
        choice2 = view.findViewById(R.id.choice2);
        choice3 = view.findViewById(R.id.choice3);




        // Get all quiz questions
        List<QuizQuestions> quizQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();




        if (!quizQuestionsList.isEmpty()) {
            // Randomly select a question
            Random random = new Random();
            currentQuestion = quizQuestionsList.get(random.nextInt(quizQuestionsList.size()));




            // Set the title view to the selected state
            questionText.setText(currentQuestion.getState());




            // Shuffle the answer options
            setAnswerChoices(random);
        }
    }




    private void setAnswerChoices(Random random) {
        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);




        Integer randomNum = numberList.remove(random.nextInt(numberList.size()));
        Integer randomNum2 = numberList.remove(random.nextInt(numberList.size()));
        Integer last = numberList.get(0); // The last remaining index




        // Assign answer choices
        assignChoice(randomNum, currentQuestion.getFirstCity());
        assignChoice(randomNum2, currentQuestion.getSecondCity());
        assignChoice(last, currentQuestion.getCapitalCity());
    }




    private void assignChoice(int index, String city) {
        if (index == 1) {
            choice1.setText(city);
        } else if (index == 2) {
            choice2.setText(city);
        } else {
            choice3.setText(city);
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Swipe listener set up");


        // Add listener to RadioGroup to update userAnswer dynamically
        choicesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "RadioButton checked ID: " + checkedId);
                RadioButton selectedRadioButton = getView().findViewById(checkedId);
                userAnswer = selectedRadioButton.getText().toString();
                Log.d(TAG, "User selected answer: " + userAnswer);


                // Initialize swipe listener only after user has selected an answer
                if (userAnswer != null && swipeListener == null) {
                    swipeListener = new OnSwipeTouchListener(getContext(), userAnswer, currentQuestion.getCapitalCity(), win) {
                        public void onSwipeLeft() {
                            checkAnswerAndMoveNext();
                        }
                    };
                    getView().setOnTouchListener(swipeListener);
                }
            }
        });
    }






    private void checkAnswerAndMoveNext() {
        // Here, you can compare userAnswer with the correct answer
        if (userAnswer != null) {
            if (userAnswer.equals(currentQuestion.getCapitalCity())) {
                win++;
                Log.d(TAG, "Correct answer! Total wins: " + win);
            } else {
                Log.d(TAG, "Incorrect answer. User: " + userAnswer + ", Correct: " + currentQuestion.getCapitalCity());
            }
            // Move to the next question logic goes here
            currentQuestionIndex++;
            // Load the next question or finish the quiz
        } else {
            Log.d(TAG, "No answer selected. Please select an answer before swiping.");
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        quizQuestionsData.close(); // Close the database when fragment is destroyed
    }




    public static int getNumberOfVersions() {
        return 6;
    }
}
