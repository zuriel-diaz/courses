package com.zurieldiaz.courses.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zurieldiaz.courses.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByCourseId(Long courseId);
	List<Review> findByUserId(Long studentId);
	List<Review> findByUserIdAndCourseId(Long studentId, Long courseId);
}
