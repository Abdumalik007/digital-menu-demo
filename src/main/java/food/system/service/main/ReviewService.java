package food.system.service.main;

import food.system.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<ReviewDto> createReview(ReviewDto reviewDto);
    ResponseEntity<List<ReviewDto>> findAllReviews();
    ResponseEntity<?> deleteReviews(List<Integer> reviewsId);
    ResponseEntity<ReviewDto> findReviewById(Integer id);
}
