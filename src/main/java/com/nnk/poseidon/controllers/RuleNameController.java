package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RuleNameEntity;
import com.nnk.poseidon.service.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@CrossOrigin("http://localhost:4200")
public class RuleNameController {

    /**
     * RuleName Service layer.
     */
    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Get ruleName list page.
     * @param model rating list to pass
     * @return ruleName list page
     */
    @RequestMapping("/ruleName/list")
    public String home(final Model model) {
        model.addAttribute("list", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Get adding ruleName page.
     * @param ruleNameEntity ruleName to add
     * @return adding page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameEntity ruleNameEntity) {
        return "ruleName/add";
    }

    /**
     * Validate ruleName for adding.
     * @param ruleName ratting to add
     * @param result binding
     * @param model ruleName list
     * @return ruleName list page
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid final RuleNameEntity ruleName,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.add(ruleName);
        model.addAttribute("list", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Get updating ruleName page.
     * @param id ruleName id
     * @param model ruleName to pass
     * @return ruleName update page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
       try {
           RuleNameEntity ruleNameEntity = ruleNameService.findById(id);
           model.addAttribute("ruleNameEntity", ruleNameEntity);
       } catch (NoSuchElementException e) {
           e.printStackTrace();
           model.addAttribute("list", ruleNameService.findAll());
           return "redirect:/ruleName/list";
       }
        return "ruleName/update";
    }

    /**
     * Update ruleName.
     * @param id ruleName id
     * @param ruleName ruleName to update
     * @param result binding
     * @param model ruleName list to pass
     * @return ruleName list page if ok else ruleName update page
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer id,
                                 @Valid final RuleNameEntity ruleName,
                                 final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "redirect:/ruleName/list";
        }
        ruleName.setId(id);
        ruleNameService.update(ruleName);
        model.addAttribute("list", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Delete ratting.
     * @param id ratting id
     * @param model ratting list
     * @return ratting list page
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer id,
                                 final Model model) {
        ruleNameService.delete(id);
        model.addAttribute("list", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Get all ruleName.
     * @return ruleName list
     */
    @GetMapping("/ruleName")
    public ResponseEntity<List<RuleNameEntity>> getAllRuleName() {
        log.info("GET/ruleName");
        return ResponseEntity.ok(ruleNameService.findAll());
    }

    /**
     * Get ruleName by id.
     * @param ruleNameId id ruleName
     * @return ruleName
     */
    @GetMapping("/ruleName/ruleNameId/{id}")
    public ResponseEntity<RuleNameEntity> getRuleNameById(@PathVariable("id") final Integer ruleNameId) {
        log.info("GET/ruleName/ruleNameId/" + ruleNameId);
        try {
            return ResponseEntity.ok(ruleNameService.findById(ruleNameId));
        } catch (NoSuchElementException e) {
            log.error("GetRuleNameById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a ruleName.
     * @param ruleNameEntity ruleName
     * @return ruleName added
     */
    @PostMapping("/ruleName/add")
    public ResponseEntity<RuleNameEntity> addRuleName(@RequestBody final RuleNameEntity ruleNameEntity) {
        log.info("POST/ruleName/add");
        try {
            return ResponseEntity.ok(ruleNameService.add(ruleNameEntity));
        } catch (NoSuchElementException e) {
            log.error("addRuleName error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update ruleName.
     * @param ruleNameEntity ruleName new information
     * @return ruleName updated
     */
    @PutMapping("/ruleName")
    public ResponseEntity<RuleNameEntity> updateRuleName(
            @RequestBody RuleNameEntity ruleNameEntity) {
        log.info("PUT/ruleName/ruleNameId/ " + ruleNameEntity.getId());
        try {
            return ResponseEntity.ok(ruleNameService.update(ruleNameEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateRuleName error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a ruleName by his id.
     * @param ruleNameId id ruleName
     * @return ruleName deleted
     */
    @DeleteMapping("/ruleName/ruleNameId/{id}")
    public ResponseEntity<?> deleteRuleName(@PathVariable("id") final Integer ruleNameId) {
        log.info("DEL/ruleName/ruleNameId/" + ruleNameId);
        try {
            ruleNameService.delete(ruleNameId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteRuleName error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
