package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.domain.IOperationDomain;
import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OperationsController {
    private static final int PAGE_SIZE = 4;

    private IOperationDomain bankDomain;

    public OperationsController(IOperationDomain bankDomain) {
        this.bankDomain = bankDomain;
    }

    @GetMapping("/admin/operations")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        --page;

        Page<Operation> operations = bankDomain.allOperations(page, PAGE_SIZE);
        model.addAttribute("pageNumbers", getPageNumbers(operations));
        model.addAttribute("operations", operations);

        return "admin/operations/index";
    }

    private int[] getPageNumbers(Page page) {
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i - 1] = i;
        return pageNumbers;
    }
}
