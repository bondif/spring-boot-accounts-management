package com.bondif.accountsmanagementworkshop.controllers;

import com.bondif.accountsmanagementworkshop.dao.ClientRepository;
import com.bondif.accountsmanagementworkshop.entities.Client;
import com.bondif.accountsmanagementworkshop.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ClientsController {
    private static final int PAGE_SIZE = 4;

    private ClientRepository clientRepository;

    public ClientsController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/admin/clients")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;

        Page<Client> clients = clientRepository.findAll(PageRequest.of(page, PAGE_SIZE));
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
        if(bindingResult.hasErrors()) {
            return "admin/clients/create";
        }

        clientRepository.save(client);

        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientRepository.findById(id).get());
        return "admin/clients/edit";
    }

    @PutMapping("/admin/clients/update/{id}")
    public String update(@PathVariable Long id, @Valid Client client, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "admin/clients/edit";
        }

        client.setCode(id);
        clientRepository.save(client);

        return "redirect:/admin/clients";
    }

    @DeleteMapping("/admin/clients/destroy/{id}")
    public String destroy(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/admin/clients";
    }

    private int[] getPageNumbers(Page page) {
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 1; i <= pageNumbers.length; i++) pageNumbers[i-1] = i;
        return pageNumbers;
    }
}
