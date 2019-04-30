package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.dao.AccountRepository;
import com.bondif.accountsmanagementworkshop.entities.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    private AccountRepository accountRepository;

    public HomeController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("account", null);
        return "admin/index";
    }

    @GetMapping("/admin/getAccount")
    public String getAccount(String code, Model model) {
        Optional<Account> account = accountRepository.findById(code);
        if (account.isPresent())
            model.addAttribute("account", account.get());
        else model.addAttribute("account", null);

        return "admin/index";
    }
}
