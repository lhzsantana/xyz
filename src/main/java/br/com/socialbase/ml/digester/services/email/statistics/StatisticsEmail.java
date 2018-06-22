package br.com.socialbase.ml.digester.services.email.statistics;

import br.com.socialbase.ml.digester.model.Group;
import br.com.socialbase.ml.digester.model.Post;
import br.com.socialbase.ml.digester.model.User;
import br.com.socialbase.ml.digester.services.email.EmailFormatter;
import br.com.socialbase.ml.digester.services.email.EmailSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatisticsEmail extends EmailFormatter {

    public StatisticsEmail(User user, List<Post> posts, List<User> users, List<Group> groups, String ... messages) {
        super(user, posts, users, groups, messages);
    }

    public String createBody() throws IOException {

        String templateBody = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/statistics/template-principal.html")));
        String templatePost1 = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/digest/template-post1.html")));
        String templatePost2e3 = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/digest/template-post2e3.html")));
        String templateUsers = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/digest/template-users.html")));
        String templateUser = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/digest/template-user.html")));
        String templateNumbers = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/statistics/template-numbers.html")));

        String body = templateBody.replace("NOME", user.getName());

        body = body.replace("POSTS123", getPostsHTML(templatePost1, templatePost2e3));
        body = body.replace("USERS", templateUsers.replace("USERS", getUsersHTML(templateUser)));
        body = body.replace("NUMBERS", getNumbers(templateNumbers));

        return body;
    }

    private String getNumbers(String templateNumbers){

        if(messages==null || messages.length==0) return "";

        return templateNumbers.replace("MESSAGE1", messages[0])
                        .replace("MESSAGE2", messages[1]);
    }
}
