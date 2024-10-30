package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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

    private QuizQuestionsData quizQuestionsData;
    private QuizResponsesData quizResponsesData;

    private int correctAnswersCount = 0; // Track correct answers

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
        quizResponsesData = new QuizResponsesData(getActivity());
        quizResponsesData.open();
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

        questionText = view.findViewById(R.id.question_text);
        choice1 = view.findViewById(R.id.choice1);
        choice2 = view.findViewById(R.id.choice2);
        choice3 = view.findViewById(R.id.choice3);
        choicesRadioGroup = view.findViewById(R.id.choices_radio_group);

        // Get all quiz questions
        questions = quizQuestionsData.retrieveAllQuizQuestions();

        if (!questions.isEmpty()) {
            displayNextQuestion();
        } else {
            // Handle case where there are no quiz questions
            questionText.setText("No States Available");
        }

        // Detect swipe gestures
        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                checkAnswerAndMoveToNext();
            }

            @Override
            public void onSwipeRight() {
                checkAnswerAndMoveToNext();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quizQuestionsData.close(); // Close the database when fragment is destroyed
        quizResponsesData.close();
    }

    private void displayNextQuestion() {
        // Ensure we only display 6 questions
        if (currentQuestionIndex < questions.size() && currentQuestionIndex < 6) {
            currentQuestion = questions.get(currentQuestionIndex);
            questionText.setText(currentQuestion.getState());

            // Randomly assign city names to the choices
            List<Integer> numberList = new ArrayList<>();
            numberList.add(1);
            numberList.add(2);
            numberList.add(3);
            Random random = new Random();

            Integer randomNum = numberList.get(random.nextInt(numberList.size()));
            numberList.remove(randomNum);
            Integer randomNum2 = numberList.get(random.nextInt(numberList.size()));
            numberList.remove(randomNum2);
            Integer last = numberList.get(0);

            // Set the text for the radio buttons based on random selection
            if (randomNum == 1) {
                choice1.setText(currentQuestion.getFirstCity());
            } else if (randomNum == 2) {
                choice2.setText(currentQuestion.getFirstCity());
            } else {
                choice3.setText(currentQuestion.getFirstCity());
            }

            if (randomNum2 == 1) {
                choice1.setText(currentQuestion.getSecondCity());
            } else if (randomNum2 == 2) {
                choice2.setText(currentQuestion.getSecondCity());
            } else {
                choice3.setText(currentQuestion.getSecondCity());
            }

            if (last == 1) {
                choice1.setText(currentQuestion.getCapitalCity());
            } else if (last == 2) {
                choice2.setText(currentQuestion.getCapitalCity());
            } else {
                choice3.setText(currentQuestion.getCapitalCity());
            }

            currentQuestionIndex++;
        } else if (currentQuestionIndex == 6) {
            // Show results on the 7th screen
            showResults();
        }
    }

    private void checkAnswerAndMoveToNext() {
        int selectedId = choicesRadioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            // No answer selected, return
            return;
        }

        RadioButton selectedRadioButton = getView().findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        boolean isCorrect = selectedAnswer.equals(currentQuestion.getCapitalCity());

        // Save the response
        QuizResponses response = new QuizResponses(currentQuestion.getId(), currentQuestion.getId(), selectedAnswer);
        quizResponsesData.storeQuizResponses(response);

        if (isCorrect) {
            correctAnswersCount++; // Increment if correct
        }

        // Move to the next question or show results if we've reached 6 questions
        if (currentQuestionIndex < 6) {
            displayNextQuestion(); // Move to the next question if we haven't answered 6
        } else {
            // Navigate to ResultsFragment
            showResults();
        }
    }

    private void showResults() {
        AndroidVersionsPagerAdapter adapter = (AndroidVersionsPagerAdapter) ((MainActivity) getActivity()).getPagerAdapter();
        adapter.setCorrectAnswersCount(correctAnswersCount);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewpager, new ResultsFragment()) // Replace with a new instance, if needed
                .commit();
    }







}
