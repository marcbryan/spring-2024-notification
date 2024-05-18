package edu.uoc.epcsd.notification.controllers;

import edu.uoc.epcsd.notification.kafka.ProductMessage;
import edu.uoc.epcsd.notification.services.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifyProductAvailable")
    public ResponseEntity<Long> notifyProductAvailable(@RequestParam Long productId) {
        log.trace("notifyProductAvailable");

        notificationService.notifyProductAvailable(new ProductMessage(productId));

        // Como el método notifyProductAvailable no devuelve nada, simplemente mandamos el código de estado 200 OK
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
