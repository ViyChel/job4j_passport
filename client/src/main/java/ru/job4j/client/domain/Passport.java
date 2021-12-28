package ru.job4j.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Class Passport.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 20.12.2021
 */
@Getter
@Setter
public class Passport {

    private int id;
    private String series;
    private String name;
    private Timestamp expirationDate;
}
