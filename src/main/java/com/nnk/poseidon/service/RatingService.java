package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingService {

    /**
     * Rating Repository layer.
     */
    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Find all rating.
     * @return List of all rating
     */
    public List<RatingEntity> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * Find rating by id.
     * @param id rating id
     * @return rating
     */
    public RatingEntity findById(final Integer id) {
        return  ratingRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(
                        "No BidList found with id : " + id));
    }

    /**
     * Add a rating.
     * @param ratingEntity rating to add
     * @return adding rating
     */
    public RatingEntity add(final RatingEntity ratingEntity) {
        return ratingRepository.save(ratingEntity);
    }

    /**
     * Update rating.
     * @param newRatingEntity rating to update
     * @return rating updated
     */
    public RatingEntity update(final RatingEntity newRatingEntity) {
        RatingEntity oldRatingEntity = findById(newRatingEntity.getId());
        oldRatingEntity.setMoodysRating(newRatingEntity.getMoodysRating());
        oldRatingEntity.setSandPRating(newRatingEntity.getSandPRating());
        oldRatingEntity.setFitchRating(newRatingEntity.getFitchRating());
        oldRatingEntity.setOrderNumber(newRatingEntity.getOrderNumber());
        return ratingRepository.save(oldRatingEntity);
    }

    /**
     * Delete a rating by his id.
     * @param id id rating
     */
    public void delete(final Integer id) {
        RatingEntity ratingEntity = findById(id);
        ratingRepository.delete(ratingEntity);
    }
}
