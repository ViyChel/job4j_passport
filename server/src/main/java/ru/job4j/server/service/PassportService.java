package ru.job4j.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.server.domain.Passport;
import ru.job4j.server.repository.PassportRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Class PassportService.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 10.12.2021
 */
@Service
public class PassportService {
    private final PassportRepository repository;

    @Value("${periodInMonths}")
    private int period;

    @Autowired
    public PassportService(PassportRepository repository) {
        this.repository = repository;
    }

    public Passport save(Passport passport) {
        return repository.save(passport);
    }

    public boolean update(int id, Passport passport) {
        Optional<Passport> temp = repository.findById(id);
        boolean result = temp.isPresent();
        if (result) {
            passport.setId(id);
            repository.save(passport);
        }
        return result;
    }

    public boolean delete(int id) {
        Optional<Passport> temp = repository.findById(id);
        boolean result = temp.isPresent();
        if (result) {
            repository.deleteById(id);
        }
        return result;
    }

    public List<Passport> findAll() {
        return (List<Passport>) repository.findAll();
    }

    public Passport findBySeries(String series) {
        return repository.findBySeries(series);
    }

    public List<Passport> findExpiredPassport() {
        Timestamp timestamp = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        return repository.findPassportByExpirationDateBefore(timestamp);
    }

    public List<Passport> findReplaceablePassport() {
        Timestamp current = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp end = Timestamp.valueOf(LocalDate.now().plusMonths(period).atStartOfDay());
        return repository.findPassportByExpirationDateBetween(current, end);
    }
}
