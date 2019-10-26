package hello;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class Hello {

    @RequestMapping("/")
    @ResponseBody
    String home(){
        return "hello word!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Hello.class,args);
    }
}
