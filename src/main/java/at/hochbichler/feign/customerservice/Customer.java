package at.hochbichler.feign.customerservice;

import lombok.Data;

@Data
public class Customer {
    private final long id;
    private final String firstname;
    private final String lastname;
}
