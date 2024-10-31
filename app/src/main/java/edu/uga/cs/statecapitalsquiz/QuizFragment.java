package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
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

    private List<QuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private QuizQuestions currentQuestion;
    private TextView questionText;
    private RadioGroup choicesRadioGroup;
    private RadioButton choice1, choice2, choice3;
    public static int win = 0;


    private QuizQuestionsData quizQuestionsData;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static edu.uga.cs.statecapitalsquiz.QuizFragment newInstance(int versionNum) {
        edu.uga.cs.statecapitalsquiz.QuizFragment fragment = new edu.uga.cs.statecapitalsquiz.QuizFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleView = view.findViewById(R.id.question_text);
        RadioButton choice1 = view.findViewById(R.id.choice1);
        RadioButton choice2 = view.findViewById(R.id.choice2);
        RadioButton choice3 = view.findViewById(R.id.choice3);

        // Get all quiz questions
        List<QuizQuestions> quizQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();

        if (!quizQuestionsList.isEmpty()) {
            // Randomly select a question
            Random random = new Random();
            QuizQuestions randomQuestion = quizQuestionsList.get(random.nextInt(quizQuestionsList.size()));

            // Set the title view to the selected state
            titleView.setText(randomQuestion.getState());

            Random random2 = new Random();
            List<Integer> numberList = new ArrayList<>();
            numberList.add(1);
            numberList.add(2);
            numberList.add(3);

            Integer randomNum = numberList.get(random.nextInt(numberList.size()));
            numberList.remove(randomNum);
            Integer randomNum2 = numberList.get(random.nextInt(numberList.size()));
            numberList.remove(randomNum2);
            Integer last = numberList.get(0);

            if (randomNum == 1) {
                choice1.setText(randomQuestion.getFirstCity());
            } else if (randomNum == 2) {
                choice2.setText(randomQuestion.getFirstCity());
            } else {
                choice3.setText(randomQuestion.getFirstCity());
            }

            if (randomNum2 == 1) {
                choice1.setText(randomQuestion.getSecondCity());
            } else if (randomNum2 == 2) {
                choice2.setText(randomQuestion.getSecondCity());
            } else {
                choice3.setText(randomQuestion.getSecondCity());
            }

            if (last == 1) {
                choice1.setText(randomQuestion.getCapitalCity());
                if (choice1.isChecked()) {
                    win = win + 1;
                }
            } else if (last == 2) {
                choice2.setText(randomQuestion.getCapitalCity());
                if (choice2.isChecked()) {
                    win = win + 1;
                }
            } else {
                choice3.setText(randomQuestion.getCapitalCity());
                if (choice3.isChecked()) {
                    win = win + 1;
                }
            }
        }
    }

    public static int getWin() {
        return win;
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

