package fr.epita.quiz_manager.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.epita.quiz_manager.datamodel.Question;
import fr.epita.quiz_manager.datamodel.Quiz;

public class QuizOperationsService {
	@Inject
	private QuizDAO quizdao;
	@Inject
	private QuestionDAO questiondao;
	@Inject
	private SessionFactory factory;

	public void createQuiz(Quiz quiz, ArrayList<Question> questions) {
		final Session session = factory.openSession();
		final Transaction transaction = session.beginTransaction();
		quizdao.create(quiz, session);
		quiz.setQuestionList(questions);
		for (Question question : questions) {
			questiondao.create(question, session);
		}
		transaction.commit();
		session.close();
	}

	public void updateQuiz(Quiz quiz, ArrayList<Question> questionList) {
		final Session session = factory.openSession();
		final Transaction transaction = session.beginTransaction();

		quiz.setQuestionList(questionList);
		// update quiz
		quizdao.update(quiz, session);

		transaction.commit();
		session.close();
	}
	
	public List<Quiz> search(Quiz criteria) {
		// Look for possible quizzes to match criteria
		return quizdao.search(criteria);
	}
	
	public void deleteQuiz(Quiz quiz) {
		final Session session = factory.openSession();
		final Transaction transaction = session.beginTransaction();

		quizdao.delete(quiz, session);
		transaction.commit();
		session.close();
	}

}