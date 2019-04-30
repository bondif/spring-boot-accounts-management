package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.domain.BankDomainImpl;
import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private static final int PAGE_SIZE = 2;
    private BankDomainImpl bankDomain;

    public HomeController(BankDomainImpl bankDomain) {
        this.bankDomain = bankDomain;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("account", null);
        return "admin/index";
    }

    @GetMapping("/admin/getAccount")
    public String getAccount(String code, Model model,
                             @RequestParam(name = "page", defaultValue = "1") int page) {
        model.addAttribute("account", bankDomain.retrieveAccount(code));
        model.addAttribute("operations", bankDomain.operations(code, page, PAGE_SIZE));
        return "admin/index";
    }

    @PostMapping("/admin/addOperation")
    public String addOperation(Model model, String operation, Double amount, String accountCode,
                               @RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;
        Page<Operation> operationPage = bankDomain.operations(accountCode, page, PAGE_SIZE);
        int[] pageNumbers = new int[operationPage.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i-1] = i;

        model.addAttribute("operations", operationPage);
        model.addAttribute("account", bankDomain.retrieveAccount(accountCode));
        model.addAttribute("pageNumbers", pageNumbers);
        switch (operation) {
            case "deposit":
                bankDomain.deposit(accountCode, amount);
                break;
            case "withdrawal":
                bankDomain.withdrawal(accountCode, amount);
                break;
            case "transfer":
//                bankDomain.transfer();
//                break;
        }

        return "admin/index";
    }
}
