package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.domain.RuleNameEntity;
import com.nnk.poseidon.service.RuleNameService;
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
import java.util.List;


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
    public String home(final Model model)
    {
        List<RuleNameEntity> list = ruleNameService.findAll();
        model.addAttribute("list", list);
        return "ruleName/list";
    }

    /**
     * Get adding ruleName page.
     * @param ruleName ruleName to add
     * @param model ruleName to pass
     * @return adding page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameEntity ruleName, final Model model) {
        model.addAttribute("ruleName", ruleName);
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
    public String validate(@Valid final RuleNameEntity ruleName, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            //TODO display errors
        return "ruleName/add";
        }
            ruleNameService.add(ruleName);
            model.addAttribute("list", ruleNameService.findAll());
            return "ruleName/add";
    }

    /**
     * Get updating ruleName page.
     * @param id ruleName id
     * @param model ruleName to pass
     * @return ruleName update page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        RuleNameEntity ruleNameEntity = ruleNameService.findById(id);
        model.addAttribute("ruleName", ruleNameEntity);
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
    public String updateRuleName(@PathVariable("id") final Integer id, @Valid final RuleNameEntity ruleName,
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
    public String deleteRuleName(@PathVariable("id") final Integer id, final Model model) {
        ruleNameService.delete(id);
        model.addAttribute("list", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}
