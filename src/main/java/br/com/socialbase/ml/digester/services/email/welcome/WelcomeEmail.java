package br.com.socialbase.ml.digester.services.email.welcome;

import br.com.socialbase.ml.digester.repository.GroupRepository;
import br.com.socialbase.ml.digester.repository.ViewRepository;
import br.com.socialbase.ml.digester.services.email.EmailFormatter;
import br.com.socialbase.ml.digester.services.email.EmailSender;
import br.com.socialbase.ml.digester.model.Group;
import br.com.socialbase.ml.digester.model.Post;
import br.com.socialbase.ml.digester.model.User;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WelcomeEmail extends EmailFormatter {

    public WelcomeEmail(User user, List<Post> posts, List<User> users, List<Group> groups) {
        super(user, posts, users, groups);
    }

    public String createBody() throws IOException {

        String templateBody = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/welcome/template-principal.html")));
        String templatePost1 = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/welcome/template-post1.html")));
        String templatePost2e3 = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/welcome/template-post2e3.html")));
        String templateUsers = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/welcome/template-users.html")));
        String templateUser = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/welcome/template-user.html")));
        String templateNumber = new String(Files.readAllBytes(Paths.get("/home/luiz/IdeaProjects/email-digester/src/main/java/br/com/socialbase/ml/digester/services/email/welcome/template-number.html")));

        String body = templateBody.replace("NOME", user.getName());

        body = body.replace("POSTS123", getPostsHTML(templatePost1, templatePost2e3));
        body = body.replace("USERS", templateUsers.replace("USERS", getUsersHTML(templateUser)));

        return body;
    }

}
