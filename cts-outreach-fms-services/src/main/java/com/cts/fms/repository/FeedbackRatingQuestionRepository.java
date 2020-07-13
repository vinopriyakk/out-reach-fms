package com.cts.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.fms.domain.FeedbackRatingQuestion;

@Repository
public interface FeedbackRatingQuestionRepository extends JpaRepository<FeedbackRatingQuestion, Integer> {

}
