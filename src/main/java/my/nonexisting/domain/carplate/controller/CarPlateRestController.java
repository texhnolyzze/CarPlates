package my.nonexisting.domain.carplate.controller;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import my.nonexisting.domain.carplate.service.CarPlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Texhnolyze
 */

@RestController
@RequestMapping("/number")
public class CarPlateRestController {
    
    @Autowired
    private CarPlateService service;
    
    private final ConcurrentHashMap<String, String> prevNum = new ConcurrentHashMap<>();
    
    @GetMapping("/random")
    public ResponseEntity<String> random(HttpSession session) {
        return ResponseEntity.ok(
            prevNum.compute(session.getId(), (sessionId, oldVal) -> {
                return service.randomCarPlate();
            })
        );
    }
    
    @GetMapping("/next")
    public ResponseEntity<String> next(HttpSession session) {
        return ResponseEntity.ok(
            prevNum.compute(session.getId(), (sessionId, oldVal) -> {
                if (oldVal == null)
                    return service.randomCarPlate();
                return service.nextCarPlate(oldVal);
            })
        );
    }
    
}
