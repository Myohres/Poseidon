package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@CrossOrigin("http://localhost:4200")
public class RatingController {

    /**
     * Ratting Service layer.
     */
    @Autowired
    private RatingService ratingService;

    /**
     * Get rating list page.
     * @param model rating list to pass
     * @return rating list page
     */
    @RequestMapping("/rating/list")
    public String home(final Model model) {
        log.info("GET/rating/list");
        model.addAttribute("list", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Get adding ratting page.
     * @param ratingEntity ratting to add
     * @return adding page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(final RatingEntity ratingEntity) {
        log.info("GET/rating/add");
        return "rating/add";
    }

    /**
     * Validate ratting for adding.
     * @param rating ratting to add
     * @param result binding
     * @param model ratting list
     * @return rating list page
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid final RatingEntity rating,
                           final BindingResult result, final Model model) {
        log.info("GET/rating/validate");
        if (result.hasErrors()) {
            log.info("validate error");
            return "rating/add";
        }
        log.info("validate success");
        ratingService.add(rating);
        model.addAttribute("list", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Get updating rating page.
     * @param id rating id
     * @param model rating to pass
     * @return rating update page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        log.info("GET/rating/update/" + id);
        try {
            RatingEntity ratingEntity = ratingService.findById(id);
            model.addAttribute("ratingEntity", ratingEntity);
        } catch (NoSuchElementException e) {
         log.error("Rating not found" + e.getMessage());
            model.addAttribute("list", ratingService.findAll());
            return "redirect:/rating/list";
        }
        return "rating/update";
    }

    /**
     * Update ratting.
     * @param id ratting id
     * @param rating ratting to update
     * @param result binding
     * @param model binding to pass
     * @return ratting list page if ok else ratting update page
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") final Integer id,
                               @Valid final RatingEntity rating,
                              final BindingResult result, final Model model) {
        log.info("POST/rating/update/" + id);
        if (result.hasErrors()) {
            log.info("update error");
            return "rating/update";
        }
        rating.setId(id);
        ratingService.update(rating);
        model.addAttribute("list", ratingService.findAll());
        log.info("update success");
        return "redirect:/rating/list";
    }

    /**
     * Delete ratting.
     * @param id ratting id
     * @param model ratting list
     * @return ratting list page
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer id,
                               final Model model) {
        log.info("DEL/rating/delete/" + id);
        ratingService.delete(id);
        model.addAttribute("list", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Get all rating.
     * @return rating list
     */
    @GetMapping("/rating")
    public ResponseEntity<List<RatingEntity>> getAllRating() {
        log.info("GET/rating");
        return ResponseEntity.ok(ratingService.findAll());
    }

    /**
     * Get rating by id.
     * @param ratingId id rating
     * @return rating
     */
    @GetMapping("/rating/ratingId/{id}")
    public ResponseEntity<RatingEntity> getRatingById(
            @PathVariable("id") final Integer ratingId) {
        log.info("GET/rating/ratingId/" + ratingId);
        try {
            return ResponseEntity.ok(ratingService.findById(ratingId));
        } catch (NoSuchElementException e) {
            log.error("GetRatingById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a rating.
     * @param ratingEntity rating
     * @return rating added
     */
    @PostMapping("/rating/add")
    public ResponseEntity<RatingEntity> addRating(
            @RequestBody final RatingEntity ratingEntity) {
        log.info("POST/rating/add");
        try {
            return ResponseEntity.ok(ratingService.add(ratingEntity));
        } catch (NoSuchElementException e) {
            log.error("addRating error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update rating.
     * @param ratingEntity rating new information
     * @return rating updated
     */
    @PutMapping("/rating")
    public ResponseEntity<RatingEntity> updateRating(
            @RequestBody final RatingEntity ratingEntity) {
        log.info("PUT/rating/ratingId/ " + ratingEntity.getId());
        try {
            return ResponseEntity.ok(ratingService.update(ratingEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateRating error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a rating by his id.
     * @param ratingId id rating
     * @return rating deleted
     */
    @DeleteMapping("/rating/ratingId/{id}")
    public ResponseEntity<?> deleteRating(
            @PathVariable("id") final Integer ratingId) {
        log.info("DEL/rating/ratingId/" + ratingId);
        try {
            ratingService.delete(ratingId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteRating error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
