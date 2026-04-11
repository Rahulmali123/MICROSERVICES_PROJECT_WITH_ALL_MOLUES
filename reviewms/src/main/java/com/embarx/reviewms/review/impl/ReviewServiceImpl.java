package com.embarx.reviewms.review.impl;


import com.embarx.reviewms.review.Review;
import com.embarx.reviewms.review.ReviewRepo;
import com.embarx.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepo reviewRepo;

	public ReviewServiceImpl(ReviewRepo reviewRepo) {
		super();
		this.reviewRepo = reviewRepo;
	}

	@Override
	public boolean addReview(Long companyId, Review review)
	{
		if (companyId != null)
		{
			review.setCompanyId(companyId);
			reviewRepo.save(review);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Review> getAllReview(Long companyId)
	{
		return reviewRepo.findByCompanyId(companyId);
	}

	@Override
	public Review getByReviewId(Long reviewId)
	{
		return reviewRepo.findById(reviewId).orElseThrow(null);
	}

	@Override
	public boolean updateReviews(Review updateReview,Long reviewId)
	{
		Review review=reviewRepo.findById(reviewId).orElse(null);

		if (review != null)
		{
			review.setTitle(updateReview.getTitle());
			review.setDescription(updateReview.getDescription());
			review.setRating(updateReview.getRating());
			review.setCompanyId(updateReview.getCompanyId());
			reviewRepo.save(review);
			return true;
		}else {
			return false;
		}

	}

	@Override
	public boolean deleteByReviewsId(Long reviewId)
	{
		Review review = reviewRepo.findById(reviewId).orElseThrow(null);

		if (review !=null)
		{
			reviewRepo.delete(review);
			return true;
		}else {

			return false;
		}
	}



}
