package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.domain.IAccountDomain;
import com.bondif.accountsmanagementworkshop.domain.IOperationDomain;
import com.bondif.accountsmanagementworkshop.domain.OperationDomainImpl;
import com.bondif.accountsmanagementworkshop.domain.exceptions.AccountDoesNotExistException;
import com.bondif.accountsmanagementworkshop.domain.exceptions.InsufficientBalanceException;
import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    private static final int PAGE_SIZE = 4;
    private IOperationDomain operationDomain;
    private IAccountDomain accountDomain;

    public HomeController(IOperationDomain operationDomain, IAccountDomain accountDomain) {
        this.operationDomain = operationDomain;
        this.accountDomain = accountDomain;
    }

    @GetMapping("/admin")
    public String index(Model model,
                        @RequestParam(name = "account", defaultValue = "0") String accountCode,
                        @RequestParam(name = "page", defaultValue = "1") int page) {
        --page;
        model.addAttribute("account", null);
        if (!accountCode.equals("0")) {
            model.addAttribute("operations", operationDomain.operations(accountCode, page, PAGE_SIZE));
            model.addAttribute("pageNumbers", getPageNumbers(operationDomain.operations(accountCode, page, PAGE_SIZE)));
        }

        return "admin/index";
    }

    @GetMapping("/admin/getAccount")
    public String getAccount(String code, Model model, RedirectAttributes redirectAttributes,
                             @RequestParam(name = "page", defaultValue = "1") int page) {
        --page;

        try {
            model.addAttribute("account", accountDomain.retrieveAccount(code));
        } catch (AccountDoesNotExistException e) {
            model.addAttribute("account", null);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }
        model.addAttribute("operations", operationDomain.operations(code, page, PAGE_SIZE));
        model.addAttribute("pageNumbers", getPageNumbers(operationDomain.operations(code, page, PAGE_SIZE)));

        return "admin/index";
    }

    @PostMapping("/admin/addOperation")
    public String addOperation(Model model, String operation, Double amount, String accountCode,
                               String receiverCode,
                               @RequestParam(name = "page", defaultValue = "1") int page,
                               RedirectAttributes redirectAttributes) {

        --page;
        Page<Operation> operationPage = operationDomain.operations(accountCode, page, PAGE_SIZE);

        model.addAttribute("operations", operationPage);
        model.addAttribute("pageNumbers", getPageNumbers(operationPage));

        switch (operation) {
            case "deposit":
                operationDomain.deposit(accountCode, amount);
                break;
            case "withdrawal":
                try {
                    operationDomain.withdrawal(accountCode, amount);
                } catch (InsufficientBalanceException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/admin/getAccount?code=" + accountCode;
                }
                break;
            case "transfer":
                try {
                    operationDomain.transfer(accountCode, receiverCode, amount);
                } catch (AccountDoesNotExistException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/admin/getAccount?code=" + accountCode;
                } catch (InsufficientBalanceException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/admin/getAccount?code=" + accountCode;
                }
                break;
        }

        return "redirect:/admin/getAccount?code=" + accountCode;
    }

    private int[] getPageNumbers(Page page) {
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i-1] = i;
        return pageNumbers;
    }
}
