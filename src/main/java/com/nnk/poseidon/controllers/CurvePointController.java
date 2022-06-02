package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.service.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
@CrossOrigin("http://localhost:4200")
public class CurvePointController {

    /**
     * CurvePoint service layer.
     */
    @Autowired
    private CurvePointService curvePointService;

    /**
     * Show all curePoint.
     * @param model list curvePoint
     * @return curvePoint list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(final Model model) {
        model.addAttribute("list", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Go to adding CurvePoint page.
     * @param curve CurvePoint to add
     * @param model curvePoint to pass
     * @return curvePoint adding page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(final CurvePointEntity curve,
                                    final Model model) {
        model.addAttribute("curvePoint", curve);
        return "curvePoint/add";
    }

    /**
     * Validate fields curvePoint before adding.
     * @param curvePoint curvePoint to add
     * @param result binding
     * @param model curvePoint to pass
     * @return curvePoint adding page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid final CurvePointEntity curvePoint,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            model.addAttribute("curvePoint", curvePoint);
            //TODO display errors
            return "curvePoint/add";

        }
        curvePointService.add(curvePoint);
        model.addAttribute("liste", curvePointService.findAll());
        return "curvePoint/add";
    }

    /**
     * Go to updating curvePoint page.
     * @param id curvePoint id
     * @param model curvePoint to pass
     * @return curvePoint updating page
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        CurvePointEntity curvePointEntity = curvePointService.findById(id);
        model.addAttribute("curvePoint", curvePointEntity);
        return "curvePoint/update";
    }

    /**
     * updating curvePoint page.
     * @param id curvePoint id
     * @param curvePoint curvePoint to update
     * @param result binding
     * @param model list curvePoint
     * @return curvePoint list page
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") final Integer id,
                            @Valid final CurvePointEntity curvePoint,
                            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.update(curvePoint);
        model.addAttribute("liste", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }

    /**
     * Deleting curvePoint.
     * @param id curvePoint id
     * @param model list curvePoint
     * @return curvePoint list page
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") final Integer id,
                            final Model model) {
        curvePointService.delete(id);
        model.addAttribute("liste", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
}
