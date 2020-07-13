package com.cts.fms.web.rest;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fms.domain.FeedbackQuestion;
import com.cts.fms.service.FeedBackService;
import com.cts.fms.service.dto.FeedbackAnswerRequest;

@RestController
@RequestMapping("/api")
public class FeedBackResource {

	@Inject
	private FeedBackService feedBackService;

	@RequestMapping(value = "/feedback/rating-question", method = RequestMethod.POST)
	public ResponseEntity<?> addRatingQuestion(@RequestParam(name = "ratingQues") String ratingQues,
			@RequestParam(name = "likeQues") String likeQues, @RequestParam(name = "dislikeQues") String dislikeQues,
			HttpServletRequest request) {
		return new ResponseEntity<>(feedBackService.addRatingQuestion(ratingQues, likeQues, dislikeQues),HttpStatus.OK);
	}

	@RequestMapping(value = "/feedback/unregister-not-attended-question", method = RequestMethod.POST)
	public ResponseEntity<?> addUnregisterNotAttendedQuestion(@RequestParam(name = "question") String question,
			@RequestParam(name = "answers") String answers,
			@RequestParam(name = "participationType") String participationType, HttpServletRequest request) {
		String[] ans = answers.split("\\,");
		return new ResponseEntity<>(feedBackService.addUnregisterNotAttendedQuestion(question, ans, participationType),HttpStatus.OK);
	}

	@GetMapping(value = "/feedback/questions")
	public Page<FeedbackQuestion> getFeedbackQuestions(@RequestParam(defaultValue = "0") int page,
			HttpServletRequest request) {
		return feedBackService.getFeedbackQuestions(page);
	}

	@GetMapping(value = "/feedback/form")
	public ResponseEntity<?> feedbackForm(@RequestParam(name = "eventId") String eventId,
			@RequestParam(name = "employeeId") String employeeId, @RequestParam(name = "secretCode") String secretCode,
			HttpServletRequest request) {
		return new ResponseEntity<>(feedBackService.getFeedbackFormDetails(eventId, employeeId, secretCode),HttpStatus.OK);

	}

	@RequestMapping(value = "/feedback/submit")
	public ResponseEntity<?> submitFeedback(@RequestBody FeedbackAnswerRequest feedbackAnswerRequest,
			HttpServletRequest request) {
		return new ResponseEntity<>(feedBackService.submitFeedback(feedbackAnswerRequest),HttpStatus.OK);
	}

}

