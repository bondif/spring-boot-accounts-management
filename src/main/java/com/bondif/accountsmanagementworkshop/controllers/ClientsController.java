package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.domain.IClientDomain;
import com.bondif.accountsmanagementworkshop.domain.exceptions.ClientDoesNotExistException;
import com.bondif.accountsmanagementworkshop.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ClientsController {
    private static final int PAGE_SIZE = 4;

    private IClientDomain clientDomain;

    public ClientsController(IClientDomain clientDomain) {
        this.clientDomain = clientDomain;
    }

    @GetMapping("/admin/clients")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;

        Page<Client> clients = clientDomain.clients(page, PAGE_SIZE);
        model.addAttribute("pageNumbers", getPageNumbers(clients));
        model.addAttribute("clients", clients);

        return "admin/clients/index";
    }

    @GetMapping("/admin/clients/create")
    public String create(Client client, Model model) {
        model.addAttribute("client", client);
        return "admin/clients/create";
    }

    @PostMapping("/admin/clients/store")
    public String store(@Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/clients/create";
        }

        clientDomain.saveClient(client);

        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Client client = null;
        try {
            client = clientDomain.retrieveClient(id);
        } catch (ClientDoesNotExistException e) {
            e.printStackTrace();
        }
        model.addAttribute("client", client);
        return "admin/clients/edit";
    }

    @PutMapping("/admin/clients/update/{id}")
    public String update(@PathVariable Long id, @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/clients/edit";
        }

        clientDomain.updateClient(id, client);

        return "redirect:/admin/clients";
    }

    @DeleteMapping("/admin/clients/destroy/{id}")
    public String destroy(@PathVariable Long id) {
        clientDomain.removeClient(id);
        return "redirect:/admin/clients";
    }

    private int[] getPageNumbers(Page page) {
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i - 1] = i;
        return pageNumbers;
    }
}
