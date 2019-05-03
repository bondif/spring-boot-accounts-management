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
    public String index(Model model,
                        @RequestParam(name = "account", defaultValue = "0") String accountCode,
                        @RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;
        model.addAttribute("account", null);
        if (!accountCode.equals("0")) {
            model.addAttribute("operations", bankDomain.operations(accountCode, page, PAGE_SIZE));
            model.addAttribute("pageNumbers", getPageNumbers(bankDomain.operations(accountCode, page, PAGE_SIZE)));
        }

        return "admin/index";
    }

    @GetMapping("/admin/getAccount")
    public String getAccount(String code, Model model,
                             @RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;
        model.addAttribute("account", bankDomain.retrieveAccount(code));
        model.addAttribute("operations", bankDomain.operations(code, page, PAGE_SIZE));
        model.addAttribute("pageNumbers", getPageNumbers(bankDomain.operations(code, page, PAGE_SIZE)));
        return "admin/index";
    }

    @PostMapping("/admin/addOperation")
    public String addOperation(Model model, String operation, Double amount, String accountCode,
                               @RequestParam(name = "page", defaultValue = "1") int page) {
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

        page = page - 1;
        Page<Operation> operationPage = bankDomain.operations(accountCode, page, PAGE_SIZE);

        model.addAttribute("operations", operationPage);
        model.addAttribute("account", bankDomain.retrieveAccount(accountCode));
        model.addAttribute("pageNumbers", getPageNumbers(operationPage));

        return "redirect:/admin/getAccount?code=" + accountCode;
    }

    private int[] getPageNumbers(Page page) {
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i-1] = i;
        return pageNumbers;
    }
}
