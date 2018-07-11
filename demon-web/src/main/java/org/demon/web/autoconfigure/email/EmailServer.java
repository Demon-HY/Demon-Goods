//package org.demon.web.autoconfigure.email;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.internet.MimeMessage;
//import java.io.File;
//
//@Service
//public class EmailServer {
//
//    @Autowired
//    private JavaMailSender mailSender; //自动注入的Bean
//
//    @Value("${spring.mail.username}")
//    private String Sender; //读取配置文件中的参数
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * 发送Html邮件
//     */
//    public void sendHtmlMail() {
//        MimeMessage message = null;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(Sender);
//            helper.setTo(Sender);
//            helper.setSubject("标题：发送Html内容");
//
//            String sb = "<h1>大标题-h1</h1>" +
//                    "<p style='color:#F00'>红色字</p>" +
//                    "<p style='text-align:right'>右对齐</p>";
//            helper.setText(sb, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mailSender.send(message);
//    }
//
//    /**
//     * 发送带附件的邮件
//     */
//    public void sendAttachmentsMail() {
//        MimeMessage message = null;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(Sender);
//            helper.setTo(Sender);
//            helper.setSubject("主题：带附件的邮件");
//            helper.setText("带附件的邮件内容");
//            //注意项目路径问题，自动补用项目路径
//            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
//            //加入邮件
//            helper.addAttachment("图片.jpg", file);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        mailSender.send(message);
//    }
//
//    /**
//     * 发送带静态资源的邮件
//     */
//    public void sendInlineMail() {
//        MimeMessage message = null;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(Sender);
//            helper.setTo(Sender);
//            helper.setSubject("主题：带静态资源的邮件");
//            //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
//            helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);
//
//            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
//            helper.addInline("picture",file);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        mailSender.send(message);
//    }
//}
