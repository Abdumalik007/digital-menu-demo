package food.system.service.main;

import food.system.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<?> createReview(ReviewDto reviewDto);
    ResponseEntity<?> findAllReviews();
    ResponseEntity<?> deleteReviews(List<Integer> reviewsId);
    ResponseEntity<?> findReviewById(Integer id);
}
