package com.ikee.pippy.notification.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.ikee.pippy.notification.domain.NotificationType;
import com.ikee.pippy.notification.domain.Recipient;

public interface EmailService {

	void send(NotificationType type, Recipient recipient, String attachment) throws MessagingException, IOException;

}
