package com.od.cryptography.cryptography.controller;

import com.od.cryptography.common.errors.ValidationError;
import com.od.cryptography.cryptography.model.Action;
import com.od.cryptography.cryptography.model.CryptographyModel;
import com.od.cryptography.cryptography.model.Process;
import com.od.cryptography.cryptography.service.CryptographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/cryptography")
public class CryptographyController {

    private final CryptographyService cryptographyService;

    @Autowired
    public CryptographyController(CryptographyService cryptographyService) {
        this.cryptographyService = cryptographyService;
    }

    @GetMapping
    public String cryptographyView(Model model) {
        model.addAttribute("appModel", new CryptographyModel());
        return "cryptography";
    }

    @PostMapping
    public String operation(@ModelAttribute CryptographyModel appModel, RedirectAttributes redirectAttributes) throws ValidationError {
        String result;

        if(Process.CESAR.equals(appModel.getProcess())){
            result = resolveCezar(appModel);
        } else if(Process.VINEGERE.equals(appModel.getProcess())){
            result = resolveVinegere(appModel);
        } else {
            throw new ValidationError("Nie wybrano szyfrowania");
        }

        redirectAttributes.addFlashAttribute("resultText", result);
        redirectAttributes.addFlashAttribute("inputModel", appModel);
        return "redirect:/cryptography";
    }

    private String resolveCezar(@ModelAttribute CryptographyModel appModel) throws ValidationError {
        String result;
        if(Action.ENCRYPTION.equals(appModel.getAction())){
            result = cryptographyService.cesarEncryption(appModel.getText(), appModel.getShift());
        } else if(Action.DECRYPTION.equals(appModel.getAction())){
            result = cryptographyService.cesarDecryption(appModel.getText(), appModel.getShift());
        } else {
            throw new ValidationError("Nie wybrano akcji");
        }
        return result;
    }

    private String resolveVinegere(@ModelAttribute CryptographyModel appModel) throws ValidationError {
        String result;
        if(Action.ENCRYPTION.equals(appModel.getAction())){
            result = cryptographyService.vinegereEncryption(appModel.getText(), appModel.getKey());
        } else if(Action.DECRYPTION.equals(appModel.getAction())){
            result = cryptographyService.vinegereDecryption(appModel.getText(), appModel.getKey());
        } else {
            throw new ValidationError("Nieznana akcja");
        }
        return result;
    }
}
