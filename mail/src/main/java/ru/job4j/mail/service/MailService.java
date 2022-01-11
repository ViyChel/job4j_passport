package ru.job4j.mail.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.mail.domain.Passport;

import java.util.List;

/**
 * Class MailService.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 29.12.2021
 */
@Service
public class MailService {

    @KafkaListener(topics = "mail", groupId = "passport", containerFactory = "passportListener")
    void checkPassports(List<Passport> data) {
        data.forEach(passport -> System.out.printf("У паспорта серии %s на имя %s истек строк %s. Необходимо заменить"
                + " паспорт.\n", passport.getSeries(), passport.getName(), passport.getExpirationDate()));
    }
}
