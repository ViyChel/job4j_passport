package ru.job4j.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.server.domain.Passport;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PassportRepository extends CrudRepository<Passport, Integer> {
    Passport findBySeries(String series);

    List<Passport> findPassportByExpirationDateBefore(Timestamp expirationDate);

    List<Passport> findPassportByExpirationDateBetween(Timestamp startDate, Timestamp endDate);
}