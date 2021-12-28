package ru.job4j.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.server.domain.Passport;
import ru.job4j.server.service.PassportService;

import java.util.List;

/**
 * Class PassportController.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 10.12.2021
 */
@RestController
@RequestMapping("/api/passport")
public class PassportController {
    private final PassportService passportService;

    @Autowired
    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping
    public Passport save(@RequestBody Passport passport) {
        return passportService.save(passport);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam int id, @RequestBody Passport passport) {
        boolean status = passportService.update(id, passport);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        boolean status = passportService.delete(id);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping("/find/{series}")
    public ResponseEntity<Passport> findBySeries(@PathVariable String series) {
        var passport = passportService.findBySeries(series);
        return ResponseEntity
                .status(passport != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(passport);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        var passportList = passportService.findAll();
        return ResponseEntity
                .status(!passportList.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(passportList);
    }

    @GetMapping("/unavailable")
    public List<Passport> getExpiredPassport() {
        return passportService.findExpiredPassport();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> getReplaceablePassport() {
        return passportService.findReplaceablePassport();
    }
}
