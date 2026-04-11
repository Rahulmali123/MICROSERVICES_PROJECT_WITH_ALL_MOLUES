package com.embarx.reviewms.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}

	@PostMapping
	public ResponseEntity<String> createReviews(@RequestBody Review review, @RequestParam Long companyId) {
		boolean isReviewSaved = reviewService.addReview(companyId, review);
		if (isReviewSaved) {
			return new ResponseEntity<>("Review Add Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Review not save Successfully", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReviews(@RequestBody Review review,@PathVariable Long reviewId) {
		boolean isReviewUpdate = reviewService.updateReviews(review, reviewId);
		if (isReviewUpdate) {
			return new ResponseEntity<>("Review update Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Review not update Successfully", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getByReviewsId(@PathVariable Long reviewId) {
		Review byReviewId = reviewService.getByReviewId(reviewId);
		return new ResponseEntity<>(byReviewId, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Review>> getallReviews(@RequestParam Long companyId) {
		List<Review> allReview = reviewService.getAllReview(companyId);
		return new ResponseEntity<>(allReview, HttpStatus.OK);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteByReviewsId(@PathVariable Long reviewId)
	{
		boolean deleteByReviewsId = reviewService.deleteByReviewsId(reviewId);
		if (deleteByReviewsId) {
			return new ResponseEntity<>("Review delete Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Review not delete Successfully", HttpStatus.NOT_FOUND);

	}

}
