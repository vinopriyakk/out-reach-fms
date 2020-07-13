package com.cts.fms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cts.fms.domain.EventInformation;
import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.domain.FeedbackChoiceAnswer;
import com.cts.fms.domain.FeedbackQuestion;
import com.cts.fms.domain.FeedbackRatingQuestion;
import com.cts.fms.domain.UserFeedbackAnswer;
import com.cts.fms.repository.EventUserInfoRepository;
import com.cts.fms.repository.FeedbackQuestionRepository;
import com.cts.fms.repository.FeedbackRatingQuestionRepository;
import com.cts.fms.repository.UserFeedbackAnswerRepository;
import com.cts.fms.service.dto.FeedBackRequest;
import com.cts.fms.service.dto.FeedbackAnswerRequest;



@Named
@Transactional
public class FeedBackServiceImpl implements FeedBackService{
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	
	@Inject
	private FeedbackRatingQuestionRepository feedbackRatingQuestionRepository;
	
	@Inject
	private FeedbackQuestionRepository feedbackQuestionRepository;
	
	@Inject
	private com.cts.fms.repository.FeedbackChoiceAnswerRepository feedbackChoiceAnswerRepository;
	
	@Inject
	private EventUserInfoRepository eventUserInfoRepository;
	
	@Inject
	private EventService eventService;
	
	@Inject
	private UserFeedbackAnswerRepository userFeedbackAnswerRepository;

	@Override
	public FeedBackRequest addRatingQuestion(String ratingQues, String likeQues, String dislikeQues) {
				FeedbackRatingQuestion question = new FeedbackRatingQuestion();
				question.setRatingQuestion(ratingQues);
				question.setLikeQuestion(likeQues);
				question.setDislikeQuestion(dislikeQues);
				feedbackRatingQuestionRepository.save(question);

				FeedbackQuestion feedbackQuestion = new FeedbackQuestion();
				feedbackQuestion.setQuestion(ratingQues);
				feedbackQuestion.setQuestionType("R");
				feedbackQuestion.setParticipantType("PC");
				feedbackQuestion.setFeedbackRatingQuestion(question);
				feedbackQuestionRepository.save(feedbackQuestion);
				FeedBackRequest response = new FeedBackRequest();
				response.setStatus("SUCCESS");
				response.setMessage("Feedback question for participated user saved successfully");
				return response;
	}
	
	@Override
	public FeedBackRequest addUnregisterNotAttendedQuestion(String question, String[] ans, String participationType) {
		FeedbackQuestion feedbackQuestion = new FeedbackQuestion();
		feedbackQuestion.setQuestion(question);
		feedbackQuestion.setQuestionType("M");
		feedbackQuestion.setParticipantType(participationType);
		feedbackQuestionRepository.save(feedbackQuestion);

		List<FeedbackChoiceAnswer> answers = new ArrayList<FeedbackChoiceAnswer>();

		for (String str : ans) {
			com.cts.fms.domain.FeedbackChoiceAnswer choiceAnswer = new FeedbackChoiceAnswer();
			choiceAnswer.setQuestion(feedbackQuestion);
			choiceAnswer.setAnswer(str);
			answers.add(choiceAnswer);
		}
		feedbackChoiceAnswerRepository.saveAll(answers);
		FeedBackRequest response = new FeedBackRequest();
		response.setStatus("SUCCESS");
		response.setMessage("Questions added successfully");
		return response;
	}

	@Override
	public Page<FeedbackQuestion> getFeedbackQuestions(int page) {
		Pageable paging = PageRequest.of(page, 5);
		return feedbackQuestionRepository.findAll(paging);
	}

	@Override
	public FeedBackRequest getFeedbackFormDetails(String eventId, String employeeId, String secretCode) {
		FeedBackRequest response = new FeedBackRequest();
		if (Objects.nonNull(eventUserInfoRepository.findByEventIdAndEmployeeIdAndIsFeedbackCompleted(
				eventId, employeeId, 1))) {
			response.setStatus("COMPLETED");
			response.setMessage("Many Thanks for the Feedback");
		} else {
			EventUserInfo feedbackStatus = eventUserInfoRepository
					.findByEventIdAndEmployeeId(eventId, employeeId);
			EventInformation eventInformation = eventService.getEvents(eventId);
			response.setEventName(eventInformation.getEventName());
			if (Objects.nonNull(feedbackStatus)) {
				if (feedbackStatus.getEventStatus().equals("NOT ATTENDED")
						|| feedbackStatus.getEventStatus().equals("UNREGISTERED")) {
					String qtype = feedbackStatus.getEventStatus().equals("NOT ATTENDED") ? "NA" : "UN";
					List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findByParticipantType(qtype);
					Collections.shuffle(feedbackQuestions);
					int choiceAnswerId = feedbackQuestions.get(0).getId();

					List<FeedbackChoiceAnswer> feedbackChoiceAnswers = feedbackChoiceAnswerRepository
							.findByQuestionId(choiceAnswerId);
					response.setStatus(qtype);
					response.setMessage("Feedback question");
					response.setFeedbackChoiceAnswers(feedbackChoiceAnswers);
				} else {
					String qtype = feedbackStatus.getEventStatus().equals("PARTICIPATED") ? "PC" : "";
					List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findByParticipantType(qtype);
					Collections.shuffle(feedbackQuestions);

					response.setStatus(qtype);
					response.setMessage("Feedback question");
					response.setFeedbackRatingQuestion(feedbackQuestions.get(0).getFeedbackRatingQuestion());
				}
			} else {
				response.setStatus("OK");
				response.setMessage("Link Invalid or Expired");
			}
		}
		return response;
	
	}

	@Override
	public FeedBackRequest submitFeedback(FeedbackAnswerRequest feedbackAnswerResponse) {
				UserFeedbackAnswer userFeedbackAnswer = new UserFeedbackAnswer();
				userFeedbackAnswer.setEmployeeId(feedbackAnswerResponse.getEmployeeId());
				userFeedbackAnswer.setEventId(feedbackAnswerResponse.getEventId());
				userFeedbackAnswer.setFeedbackType(feedbackAnswerResponse.getFeedbackType());
				userFeedbackAnswer.setQuestionId(feedbackAnswerResponse.getQuestionId());

				if (feedbackAnswerResponse.getFeedbackType().equals("UN")
						|| feedbackAnswerResponse.getFeedbackType().equals("NA")) {
					userFeedbackAnswer.setChoiceAnswer(feedbackAnswerResponse.getChoiceAnswer());
				} else {
					userFeedbackAnswer.setRating(feedbackAnswerResponse.getRating());
					userFeedbackAnswer.setLikeAnswer(feedbackAnswerResponse.getLikeAnswer());
					userFeedbackAnswer.setDislikeAnswer(feedbackAnswerResponse.getDislikeAnswer());
					List<UserFeedbackAnswer> answers = userFeedbackAnswerRepository.findByEventId(feedbackAnswerResponse.getEventId());
					int overallRating = 0;
					if (!answers.isEmpty()) {
						for (UserFeedbackAnswer answer : answers) {
							overallRating += answer.getRating();
						}
						overallRating = (overallRating + feedbackAnswerResponse.getRating());
						overallRating = (overallRating / (answers.size() + 1));
					} else {
						overallRating = feedbackAnswerResponse.getRating();
					}
				}
				userFeedbackAnswer.setDate(new Date());
				userFeedbackAnswerRepository.save(userFeedbackAnswer);

				EventUserInfo feedbackStatus = eventUserInfoRepository
						.findByEventIdAndEmployeeId(feedbackAnswerResponse.getEventId(),
								String.valueOf(feedbackAnswerResponse.getEmployeeId()));
				feedbackStatus.setIsFeedbackCompleted(1);
				eventUserInfoRepository.save(feedbackStatus);
				FeedBackRequest response = new FeedBackRequest();
				response.setStatus("SUCCESS");
				response.setMessage("Many Thanks for the Feedback");
				return response;
	}
	
	

}
