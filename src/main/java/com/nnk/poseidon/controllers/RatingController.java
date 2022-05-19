package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin("http://localhost:4200")
public class RatingController {
    // TODO: Inject Rating service

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(RatingEntity rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingEntity rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingEntity rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }
}
