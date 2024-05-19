package edu.uoc.epcsd.notification.services;

import edu.uoc.epcsd.notification.kafka.ProductMessage;
import edu.uoc.epcsd.notification.rest.dtos.GetUserResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Log4j2
@Component
public class NotificationService {

    @Value("${userService.getUsersToAlert.url}")
    private String userServiceUrl;

    public void notifyProductAvailable(ProductMessage productMessage) {
        // Utilizamos RestTemplate para buscar los usuarios que notificar del microservicio User
        ResponseEntity<GetUserResponse[]> response = new RestTemplate().getForEntity(userServiceUrl, GetUserResponse[].class, productMessage.getProductId(), LocalDate.now().toString());

        // Comprobamos si hay respuesta
        if (response.hasBody()) {
            GetUserResponse[] usersToNotify = response.getBody();
            assert usersToNotify != null;

            if (usersToNotify.length == 0)
                log.info("No users to notify");
            else {
                for (GetUserResponse user : usersToNotify)
                    log.info("Sending an email to user {} notifying that product ({}) item is available", user.getFullName(), productMessage.getProductId());
            }
        }
    }
}
