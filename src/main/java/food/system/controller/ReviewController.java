package food.system.controller;

import food.system.dto.ReviewDto;
import food.system.service.main.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> findAllReviews() {
        return reviewService.findAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReviewById(@PathVariable Integer id) {
        return reviewService.findReviewById(id);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteReviewById(@RequestParam List<Integer> ids) {
        return reviewService.deleteReviews(ids);
    }

}
