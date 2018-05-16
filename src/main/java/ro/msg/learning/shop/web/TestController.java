package ro.msg.learning.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.msg.learning.shop.service.OrderService;

@Controller
@RequestMapping("/shop")
public class TestController {

    private final OrderService orderService;

    public TestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/first")
    public String first() {
        return "first";
    }

}
