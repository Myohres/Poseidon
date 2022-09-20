package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.service.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
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
     * @param curvePointEntity CurvePoint to add
     * @return curvePoint adding page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(final CurvePointEntity curvePointEntity) {
        return "curvePoint/add";
    }

    /**
     * Validate fields curvePoint before adding.
     * @param curve curvePoint to add
     * @param result binding
     * @param model curvePoint to pass
     * @return curvePoint adding page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid final CurvePointEntity curve,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointService.add(curve);
        model.addAttribute("list", curvePointService.findAll());
        return "redirect:/curvePoint/list";
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
        try {
            CurvePointEntity curvePointEntity = curvePointService.findById(id);
            model.addAttribute("curvePointEntity", curvePointEntity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            model.addAttribute("List", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }
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
        model.addAttribute("list", curvePointService.findAll());
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
        model.addAttribute("list", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }

    /**
     * Get all curvePoint.
     * @return curvePoint list
     */
    @GetMapping("/curvePoint")
    public ResponseEntity<List<CurvePointEntity>> getAllCurvePoint() {
        log.info("GET/curvePoint");
        return ResponseEntity.ok(curvePointService.findAll());
    }

    /**
     * Get curvePoint by id.
     * @param curvePointId id curvePoint
     * @return curvePoint
     */
    @GetMapping("/curvePoint/curvePointId/{id}")
    public ResponseEntity<CurvePointEntity> getCurvePointyId(
            @PathVariable("id") final Integer curvePointId) {
        log.info("GET/curvePoint/curvePointId/" + curvePointId);
        try {
            return ResponseEntity.ok(curvePointService.findById(curvePointId));
        } catch (NoSuchElementException e) {
            log.error("GetCurvePointById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a curvePoint.
     * @param curvePointEntity curvePoint
     * @return curvePoint added
     */
    @PostMapping("/curvePoint/add")
    public ResponseEntity<CurvePointEntity> addCurvePoint(
            @RequestBody final CurvePointEntity curvePointEntity) {
        log.info("POST/curvePoint/add");
        try {
            return ResponseEntity.ok(curvePointService.add(curvePointEntity));
        } catch (NoSuchElementException e) {
            log.error("addCurvePoint error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update curvePoint.
     * @param curvePointEntity curvePoint new information
     * @return curvePoint updated
     */
    @PutMapping("/curvePoint")
    public ResponseEntity<CurvePointEntity> updateCurvePoint(
            @RequestBody final CurvePointEntity curvePointEntity) {
        log.info("PUT/curvePoint/curvePointId/ "
                + curvePointEntity.getCurveId());
        try {
            return ResponseEntity.ok(
                    curvePointService.update(curvePointEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateCurvePoint error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a curvePoint by his id.
     * @param curvePointId id curvePoint
     * @return curvePoint deleted
     */
    @DeleteMapping("/curvePoint/curvePointId/{id}")
    public ResponseEntity<?> deleteCurvePoint(
            @PathVariable("id") final Integer curvePointId) {
        log.info("DEL/curvePoint/curvePointId/" + curvePointId);
        try {
            curvePointService.delete(curvePointId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteCurvePoint error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
