package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.message.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate, UserService userService,
                               PaymentService paymentService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    public void notify(NotificationMessage message, String username) {
        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/notify",
                message
        );
    }
}
