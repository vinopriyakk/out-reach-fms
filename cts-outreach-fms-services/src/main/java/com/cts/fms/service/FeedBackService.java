package com.cts.fms.service;

import org.springframework.data.domain.Page;

import com.cts.fms.domain.FeedbackQuestion;
import com.cts.fms.service.dto.FeedBackRequest;
import com.cts.fms.service.dto.FeedbackAnswerRequest;

public interface FeedBackService {

	FeedBackRequest addRatingQuestion(String ratingQues, String likeQues, String dislikeQues);

	FeedBackRequest addUnregisterNotAttendedQuestion(String question, String[] ans, String participationType);

	Page<FeedbackQuestion> getFeedbackQuestions(int page);

	FeedBackRequest getFeedbackFormDetails(String eventId, String employeeId, String secretCode);

	FeedBackRequest submitFeedback(FeedbackAnswerRequest feedbackAnswerResponse);
}
