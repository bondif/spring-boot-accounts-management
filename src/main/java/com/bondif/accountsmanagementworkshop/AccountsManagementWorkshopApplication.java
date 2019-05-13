package com.bondif.accountsmanagementworkshop;

import com.bondif.accountsmanagementworkshop.dao.AccountRepository;
import com.bondif.accountsmanagementworkshop.dao.ClientRepository;
import com.bondif.accountsmanagementworkshop.dao.OperationRepository;
import com.bondif.accountsmanagementworkshop.entities.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class AccountsManagementWorkshopApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AccountsManagementWorkshopApplication.class, args);
//        test(ctx);
    }

    private static void test(ApplicationContext ctx) {
        ClientRepository clientRepo = ctx.getBean(ClientRepository.class);

        clientRepo.save(new Client("Mohamed"));
        clientRepo.save(new Client("Omar"));
        clientRepo.save(new Client("Youssef"));

        AccountRepository accountRepo = ctx.getBean(AccountRepository.class);

        Client mohamed = clientRepo.findById(1L).get();

        accountRepo.save(new CheckingAccount("a1", 9000.0, new Date(), mohamed, 800.0));
        accountRepo.save(new SavingsAccount("a2", 7800.0, new Date(), mohamed, 5.0));

        OperationRepository operationRepo = ctx.getBean(OperationRepository.class);

        Account account = accountRepo.getOne("a1");

        // Faire des opérations sur le compte
        operationRepo.save(new Deposit(new Date(), 6000.0, account));
        operationRepo.save(new Deposit(new Date(), 7000.0, account));
        operationRepo.save(new Withdrawal(new Date(), 6000.0, account));
    }

}
