package ru.job4j.client.controller;

import org.springframework.web.bind.annotation.*;
import ru.job4j.client.domain.Passport;
import ru.job4j.client.service.PassportService;

import java.util.List;

/**
 * Class PassportController.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 20.12.2021
 */
@RestController
@RequestMapping("/api")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @GetMapping
    public List<Passport> findAll() {
        return passportService.findAll();
    }

    @GetMapping("/{series}")
    public Passport findBySeries(@PathVariable String series) {
        return passportService.findBySeries(series);
    }

    @PostMapping
    public Passport save(@RequestBody Passport passport) {
        return passportService.create(passport);
    }

    @PutMapping
    public boolean update(@RequestParam int id, @RequestBody Passport passport) {
        return passportService.update(id, passport);
    }

    @DeleteMapping
    public boolean delete(@RequestParam int id) {
        return passportService.delete(id);
    }

    @GetMapping("/unavailable")
    public List<Passport> getExpiredPassport() {
        return passportService.getExpiredPassport();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> getReplaceablePassport() {
        return passportService.getReplaceablePassport();
    }
}
