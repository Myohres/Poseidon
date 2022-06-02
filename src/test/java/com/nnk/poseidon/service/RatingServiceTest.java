package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    RatingEntity ratingEntity;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        ratingEntity = new RatingEntity();
        ratingEntity.setId(1);
        ratingEntity.setMoodysRating("moodys");
        ratingEntity.setSandPRating("sandP");
        ratingEntity.setFitchRating("fitch");
        ratingEntity.setOrderNumber(1);
    }

    @Test
    void findAll() {
        when(ratingRepository.findAll()).thenReturn(new ArrayList<>());
        ratingService.findAll();
        verify(ratingRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntity));
        RatingEntity ratingEntity2 = ratingService.findById(1);
        assertEquals(1,ratingEntity2.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> ratingService.findById(1));
    }

    @Test
    void add() {
        when(ratingRepository.save(any())).thenReturn(ratingEntity);
        ratingService.add(ratingEntity);
        verify(ratingRepository,times(1)).save(ratingEntity);
    }

    @Test
    void update() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntity));
        RatingEntity ratingEntity2 = new RatingEntity();
        ratingEntity2.setId(1);
        ratingEntity2.setMoodysRating("moodys2");
        ratingEntity2.setSandPRating("sandP2");
        ratingEntity2.setFitchRating("fitch2");
        ratingEntity2.setOrderNumber(2);
        ratingService.update(ratingEntity2);
        assertEquals(1,ratingEntity.getId());
        assertEquals("moodys2",ratingEntity.getMoodysRating());
        assertEquals("sandP2",ratingEntity.getSandPRating());
        assertEquals("fitch2",ratingEntity.getFitchRating());
        assertEquals(2,ratingEntity.getOrderNumber());
    }

    @Test
    void delete() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntity));
        ratingService.delete(1);
        verify(ratingRepository,times(1)).delete(ratingEntity);
    }
}