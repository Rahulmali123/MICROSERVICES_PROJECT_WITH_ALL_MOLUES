package com.embarx.reviewms.review;

import java.util.List;

public interface ReviewService
{

	boolean addReview(Long companyId,Review review);

	Review getByReviewId(Long reviewId);

	List<Review> getAllReview(Long companyId);

	boolean updateReviews(Review review,Long reviewId);

	boolean deleteByReviewsId(Long reviewId);
}

