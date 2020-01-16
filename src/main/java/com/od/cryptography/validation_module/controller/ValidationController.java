package com.od.cryptography.validation_module.controller;

import com.od.cryptography.common.errors.ValidationError;
import com.od.cryptography.cryptography.model.CryptographyModel;
import com.od.cryptography.validation_module.model.ValidationModel;
import com.od.cryptography.validation_module.model.ValidationResult;
import com.od.cryptography.validation_module.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("validation")
public class ValidationController {

    private final ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping
    public String validation(Model model) {
        model.addAttribute("appModel", new ValidationModel());
        return "validation";
    }

    @PostMapping
    @RequestMapping(value = "/validatePesel")
    public String validatePesel(ValidationModel validationModel, RedirectAttributes redirectAttributes) throws ValidationError {
        ValidationResult result = validationService.validatePesel(validationModel.getPesel());
        redirectAttributes.addFlashAttribute("peselValidationResult", result);
        return "redirect:/validation";
    }

    @PostMapping
    @RequestMapping(value = "/luhnaAlgorithm")
    public String luhnaAlgorithm(ValidationModel validationModel, RedirectAttributes redirectAttributes) {
        ValidationResult result = validationService.calculateLuhna(validationModel.getLuhnaValue());
        redirectAttributes.addFlashAttribute("luhnaValidationResult", result);
        return "redirect:/validation";
    }

    @PostMapping
    @RequestMapping(value = "/crcSum")
    public String crcSum(ValidationModel validationModel, RedirectAttributes redirectAttributes) throws ValidationError {
        if (validationModel.getCrcDivider() == null || validationModel.getCrcSum() == null) {
            throw new ValidationError("Za mało danych");
        }
        ValidationResult result = validationService.calculateCrc(validationModel.getCrcSum(), validationModel.getCrcDivider());
        redirectAttributes.addFlashAttribute("crcInput", validationModel);
        redirectAttributes.addFlashAttribute("crcValidationResult", result);
        return "redirect:/validation";
    }
}
