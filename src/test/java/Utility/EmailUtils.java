package Utility;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.Message;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.Folder;


public class EmailUtils {

    public static String fetchActivationLink(String host, String user, String password, String subjectKeyword) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");

            Session session = Session.getInstance(properties);
            Store store = session.getStore();
            store.connect(host, user, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                if (message.getSubject().contains(subjectKeyword)) {
                    String content = getTextFromMessage(message);
                    Matcher matcher = Pattern.compile("href=['\"](https?://[^'\"]+)['\"]").matcher(content);
                    if (matcher.find()) {
                        return matcher.group(1); // activation link
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart part = mimeMultipart.getBodyPart(i);
            if (part.isMimeType("text/plain")) {
                result.append(part.getContent());
            } else if (part.isMimeType("text/html")) {
                return part.getContent().toString(); // return HTML if available
            } else if (part.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) part.getContent()));
            }
        }
        return result.toString();
    }
}