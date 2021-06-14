package com.codex.shop.service.mail;

import com.codex.shop.entity.Basket;
import com.codex.shop.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailSenderService {
  private static final String ORDER = "order";
  private static final String ITEM = "item";
  private static final String ORDER_TEMPLATE = "purchased_order";
  private static final String ITEM_TEMPLATE = "item_change";
  private JavaMailSender emailSender;
  private SpringTemplateEngine templateEngine;
  private Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

  @Autowired
  public EmailSenderService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
  }

  public void sendPurchaseMail(String to,  Basket basket) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        StandardCharsets.UTF_8.name());
    Map<String, Object> model = new HashMap<>();
    model.put(ORDER, basket.getItems());
    Context context = new Context();
    context.setVariables(model);
    String html = templateEngine.process(ORDER_TEMPLATE, context);
    helper.setTo(to);
    helper.setText(html, true);
    helper.setSubject(ORDER);
    emailSender.send(message);
    logger.info("EmailSenderService sent mail about purchase to " + to);
  }

  public void sendChangedItemMail(String to,  Item item) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        StandardCharsets.UTF_8.name());
    Map<String, Object> model = new HashMap<>();
    model.put(ITEM, item);
    Context context = new Context();
    context.setVariables(model);
    String html = templateEngine.process(ITEM_TEMPLATE, context);
    helper.setTo(to);
    helper.setText(html, true);
    helper.setSubject(ITEM);
    emailSender.send(message);
    logger.info("EmailSenderService sent mail about changed item to " + to);
  }
}
