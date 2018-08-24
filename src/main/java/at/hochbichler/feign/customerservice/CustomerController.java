package at.hochbichler.feign.customerservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @RequestMapping("/customer")
    public Customer customer(@RequestParam(name = "id", defaultValue = "0") long id) {
        return new Customer(id, "Max " + id, "Muster " + id);
    }
}
