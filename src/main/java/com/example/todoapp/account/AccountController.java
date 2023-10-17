package com.example.todoapp.account;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todoapp.common.BaseController;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(BaseController.API_VERSION + "/accounts")
public class AccountController {
    @Autowired
    private AccountRepository repository;

    @GetMapping("/list")
    public ResponseEntity listAccounts() {
        List<Account> accounts = this.repository.findAll();

        if (accounts.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no account registered.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody @Valid SignInDTO signInDTO) {
        Optional<Account> account = this.repository.findByEmail(signInDTO.email());

        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no account with such email.");
        }

        BCrypt.Result result = BCrypt.verifyer().verify(signInDTO.password().toCharArray(), account.get().getPassword());

        if (!result.verified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Wrong password.");
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Login successful.");
        response.put("account", account.get());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        Optional<Account> account = this.repository.findByEmail(signUpDTO.email());

        if (!account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already an account with this email.");
        }

        Account newAccount = new Account();
        BeanUtils.copyProperties(signUpDTO, newAccount);

        var hashedPassword = BCrypt.withDefaults()
                .hashToString(12, newAccount.getPassword().toCharArray());
        newAccount.setPassword(hashedPassword);

        this.repository.save(newAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

    @PutMapping("{email}")
    public ResponseEntity updateAccount(
            @PathVariable String email,
            @RequestBody @Valid UpdateAccountDTO updateAccountDTO
    ) {
        Optional<Account> account = this.repository.findByEmail(email);

        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such account.");
        }

        BeanUtils.copyProperties(updateAccountDTO, account.get());
        this.repository.save(account.get());
        return ResponseEntity.status(HttpStatus.OK).body(account.get());
    }

    @Transactional
    @DeleteMapping("{email}")
    public ResponseEntity deleteAccount(@PathVariable String email) {
        Optional<Account> account = this.repository.findByEmail(email);

        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such account.");
        }

        this.repository.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
