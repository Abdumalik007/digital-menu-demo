package food.system.service.impl;

import food.system.dto.ReviewDto;
import food.system.entity.Review;
import food.system.mapper.ReviewMapper;
import food.system.repository.ReviewRepository;
import food.system.service.main.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.INTERNAL_ERROR;
import static food.system.helper.ResponseEntityHelper.OK_MESSAGE;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    public static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ResponseEntity<?> createReview(ReviewDto reviewDto) {
        try {
            Review review = reviewMapper.toEntity(reviewDto);
            review.setCreatedAt(LocalDate.now());
            reviewRepository.save(review);
            return OK_MESSAGE();
        }catch (Exception e) {
            logger.error("Error while creating review: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findAllReviews() {
        List<ReviewDto> reviews = reviewRepository.findAll().stream()
                .map(reviewMapper::toDto).toList();
        return ResponseEntity.ok(reviews);
    }

    @Override
    public ResponseEntity<?> deleteReviews(List<Integer> reviewsId) {
        try {
            reviewRepository.deleteAllById(reviewsId);
            return OK_MESSAGE();
        }catch (Exception e) {
            logger.error("Error while deleting review: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findReviewById(Integer id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(review);
    }


}
