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

/**
 * A fragment that represents the quiz interface, displaying questions and choices to the user.
 * This fragment manages the quiz questions, tracks user answers, and provides swipe functionality to proceed through the quiz.
 */
public class QuizFragment extends Fragment {

    String TAG = "FRAG";
    private List<QuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private QuizQuestions currentQuestion;
    private TextView questionText;
    private RadioGroup choicesRadioGroup;
    private RadioButton choice1, choice2, choice3;
    public int win = 0;
    private String userAnswer = null;
    private OnSwipeTouchListener swipeListener;
    private QuizQuestionsData quizQuestionsData;

    /**
     * Default constructor for the fragment.
     * This is a required empty public constructor for fragments.
     */
    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment.
     *
     * @param versionNum The version number for the quiz.
     * @return A new instance of the fragment.
     */
    public static QuizFragment newInstance(int versionNum) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes the fragment, opens the database connection for quiz data.
     *
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizQuestionsData = new QuizQuestionsData(getActivity());
        quizQuestionsData.open();
    }

    /**
     * Inflates the layout for the quiz fragment.
     *
     * @param inflater The LayoutInflater object to inflate the layout.
     * @param container The parent view group that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The root view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    /**
     * Initializes the UI elements and sets up the quiz question display.
     * Retrieves quiz questions and selects one randomly to display.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionText = view.findViewById(R.id.question_text);
        choicesRadioGroup = view.findViewById(R.id.choices_radio_group);
        choice1 = view.findViewById(R.id.choice1);
        choice2 = view.findViewById(R.id.choice2);
        choice3 = view.findViewById(R.id.choice3);

        List<QuizQuestions> quizQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();

        if (!quizQuestionsList.isEmpty()) {
            Random random = new Random();
            do {
                currentQuestion = quizQuestionsList.get(random.nextInt(quizQuestionsList.size()));
            } while (currentQuestion.getUsed() == 1);

            currentQuestion.setUsed(1);
            quizQuestionsData.updateQuizQuestion(currentQuestion);

            questionText.setText(currentQuestion.getState());

            setAnswerChoices(random);
        }
    }

    /**
     * Randomly assigns the answer choices to the radio buttons.
     *
     * @param random A Random object for generating random indices for answer choices.
     */
    private void setAnswerChoices(Random random) {
        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);

        Integer randomNum = numberList.remove(random.nextInt(numberList.size()));
        Integer randomNum2 = numberList.remove(random.nextInt(numberList.size()));
        Integer last = numberList.get(0);

        assignChoice(randomNum, currentQuestion.getFirstCity());
        assignChoice(randomNum2, currentQuestion.getSecondCity());
        assignChoice(last, currentQuestion.getCapitalCity());
    }

    /**
     * Assigns a city name to a specific radio button based on the index.
     *
     * @param index The index representing which radio button to assign the city to.
     * @param city The name of the city to assign to the radio button.
     */
    private void assignChoice(int index, String city) {
        if (index == 1) {
            choice1.setText(city);
        } else if (index == 2) {
            choice2.setText(city);
        } else {
            choice3.setText(city);
        }
    }

    /**
     * Sets up the swipe listener and updates the user's answer based on the selected radio button.
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Swipe listener set up");

        choicesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "RadioButton checked ID: " + checkedId);
                RadioButton selectedRadioButton = getView().findViewById(checkedId);
                userAnswer = selectedRadioButton.getText().toString();
                Log.d(TAG, "User selected answer: " + userAnswer);

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

    /**
     * Compares the user's selected answer with the correct answer and moves to the next question.
     */
    private void checkAnswerAndMoveNext() {
        if (userAnswer != null) {
            if (userAnswer.equals(currentQuestion.getCapitalCity())) {
                win++;
                Log.d(TAG, "Correct answer! Total wins: " + win);
            } else {
                Log.d(TAG, "Incorrect answer. User: " + userAnswer + ", Correct: " + currentQuestion.getCapitalCity());
            }
            currentQuestionIndex++;
        } else {
            Log.d(TAG, "No answer selected. Please select an answer before swiping.");
        }
    }

    /**
     * Closes the database connection when the fragment is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        quizQuestionsData.close();
    }

    /**
     * Returns the number of versions of the quiz available.
     *
     * @return The number of quiz versions.
     */
    public static int getNumberOfVersions() {
        return 6;
    }
}
