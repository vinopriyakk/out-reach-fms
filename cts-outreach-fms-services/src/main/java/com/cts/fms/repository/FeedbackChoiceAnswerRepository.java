package com.cts.fms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.fms.domain.FeedbackChoiceAnswer;

@Repository
public interface FeedbackChoiceAnswerRepository extends JpaRepository<FeedbackChoiceAnswer, Integer> {

	List<FeedbackChoiceAnswer> findByQuestionId(int choiceAnswerId);

}
