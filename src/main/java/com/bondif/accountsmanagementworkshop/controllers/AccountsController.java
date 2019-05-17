package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.domain.IAccountDomain;
import com.bondif.accountsmanagementworkshop.domain.IClientDomain;
import com.bondif.accountsmanagementworkshop.domain.exceptions.AccountDoesNotExistException;
import com.bondif.accountsmanagementworkshop.entities.Account;
import com.bondif.accountsmanagementworkshop.entities.CheckingAccount;
import com.bondif.accountsmanagementworkshop.entities.Client;
import com.bondif.accountsmanagementworkshop.entities.SavingsAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class AccountsController {
    private static final int PAGE_SIZE = 4;

    private IAccountDomain accountDomain;
    private IClientDomain clientDomain;

    public AccountsController(IAccountDomain accountDomain, IClientDomain clientDomain) {
        this.accountDomain = accountDomain;
        this.clientDomain = clientDomain;
    }

    @GetMapping("/admin/accounts")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        --page;
        Page<Account> accounts = accountDomain.accounts(page, PAGE_SIZE);
        model.addAttribute("accounts", accounts);
        model.addAttribute("pageNumbers", getPageNumbers(accounts));

        return "admin/accounts/index";
    }

    @GetMapping("/admin/accounts/create")
    public String create(Model model) {
        model.addAttribute("clients", clientDomain.clients());
        return "admin/accounts/create";
    }

    @PostMapping("/admin/accounts/store")
    public String store(String code, Long clientCode, String type, Double balance, Double special) {
        Account account;
        Client client = new Client();
        client.setCode(clientCode);
        if (type.equals("CA")) {
            account = new CheckingAccount(code, balance, new Date(), client, special);
        } else {
            account = new SavingsAccount(code, balance, new Date(), client, special);
        }
        accountDomain.saveAccount(account);

        return "redirect:/admin/accounts";
    }


    @GetMapping("/admin/accounts/{code}/edit")
    public String edit(@PathVariable String code, Model model) {
        Account account = null;
        try {
            account = accountDomain.retrieveAccount(code);
        } catch (AccountDoesNotExistException e) {
            e.printStackTrace();
        }
        model.addAttribute("account", account);
        model.addAttribute("accountCode", code);

        return "admin/accounts/edit";
    }

    @PutMapping("/admin/accounts/{code}/update")
    public String update(@PathVariable String code, String type, Double balance, Double special) {
        Account oldAccount = accountDomain.retrieveAccount(code);

        if(oldAccount instanceof CheckingAccount && type.equals("CA")) {
            oldAccount.setBalance(balance);
            ((CheckingAccount) oldAccount).setOverdraft(special);
            accountDomain.saveAccount(oldAccount);
        } else if(oldAccount instanceof SavingsAccount && type.equals("SA")) {
            oldAccount.setBalance(balance);
            ((SavingsAccount) oldAccount).setRate(special);
            accountDomain.saveAccount(oldAccount);
        } else {
            Date d = oldAccount.getCreatedAt();
            Client client = oldAccount.getClient();
            accountDomain.removeAccount(code);
            Account account;
            if (type.equals("CA")) {
                account = new CheckingAccount(code, balance, d, client, special);
            } else {
                account = new SavingsAccount(code, balance, d, client, special);
            }
            accountDomain.saveAccount(account);
        }

        return "redirect:/admin/accounts";
    }

    @DeleteMapping("/admin/accounts/{code}/destroy")
    public String destroy(@PathVariable String code) {
        accountDomain.removeAccount(code);
        return "redirect:/admin/accounts";
    }

    private int[] getPageNumbers(Page page) {
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i - 1] = i;
        return pageNumbers;
    }
}
