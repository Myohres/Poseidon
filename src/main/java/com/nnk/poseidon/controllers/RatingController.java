package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

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
        List<RatingEntity> list = ratingService.findAll();
        model.addAttribute("list", list);
        return "rating/list";
    }

    /**
     * Get adding ratting page.
     * @param rating ratting to add
     * @param model ratting to pass
     * @return adding page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(final RatingEntity rating, final Model model) {
        model.addAttribute("rating", rating);
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
        if (result.hasErrors()) {
            model.addAttribute("rating", rating);
            //TODO display errors
            return "rating/add";
        }
        ratingService.add(rating);
        model.addAttribute("list", ratingService.findAll());
        return "rating/list";
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
        RatingEntity ratingEntity = ratingService.findById(id);
        model.addAttribute("rating", ratingEntity);
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
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingService.update(rating);
        model.addAttribute("list", ratingService.findAll());
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
        ratingService.delete(id);
        model.addAttribute("list", ratingService.findAll());
        return "redirect:/rating/list";
    }
}
