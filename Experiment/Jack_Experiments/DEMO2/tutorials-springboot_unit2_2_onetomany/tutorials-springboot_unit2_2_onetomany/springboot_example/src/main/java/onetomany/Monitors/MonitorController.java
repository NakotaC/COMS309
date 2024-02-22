package onetomany.Monitors;

import java.util.List;

import onetomany.Monitors.Monitor;
import onetomany.Monitors.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class MonitorController {
    @Autowired
    MonitorRepository monitorRepository;
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/monitors")
    List<Monitor> getAllMonitors(){
        return monitorRepository.findAll();
    }

    @GetMapping(path = "/monitors/{id}")
    Monitor getMonitorById( @PathVariable int id){
        return monitorRepository.findById(id);
    }

    @PostMapping(path = "/monitors")
    String createMonitor(Monitor monitor){
        if (monitor == null)
            return failure;
        monitorRepository.save(monitor);
        return success;
    }

    @PutMapping("/monitors/{id}")
    Monitor updateMonitor(@PathVariable int id, @RequestBody Monitor request){
        Monitor monitor = monitorRepository.findById(id);
        if(monitor == null)
            return null;
        monitorRepository.save(request);
        return monitorRepository.findById(id);
    }
}
