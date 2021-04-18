package com.latihan.posify.service;

import com.latihan.posify.dto.request.EmailRequest;
import com.latihan.posify.service.impl.MailServiceImpl;

public interface MailService {
public void send(EmailRequest emailRequest);
}
