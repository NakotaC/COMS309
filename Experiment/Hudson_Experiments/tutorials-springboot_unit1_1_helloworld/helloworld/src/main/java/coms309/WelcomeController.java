package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.lang.Math;


@RestController
class WelcomeController {

    public long timeConnected = System.currentTimeMillis();
    public String connected_minus_now(){
        double ret_value = (System.currentTimeMillis() - timeConnected) / 1000.0;
        String suffix;
        if(ret_value > 360) {
            suffix = " hours!!!";
            ret_value = Math.floor(ret_value / 360);
        } else if (ret_value > 60) {
            suffix = " minutes!!";
            ret_value = Math.floor(ret_value / 60);
        }
        else {
            suffix = " seconds!";
        }
        return ret_value + suffix;
    }
    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/uptime")
    public String tellUptime() {
        return "You have been connected for roughly " + connected_minus_now();
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
}


