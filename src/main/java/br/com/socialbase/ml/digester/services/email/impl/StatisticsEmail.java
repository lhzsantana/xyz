package br.com.socialbase.ml.digester.services.email.impl;

import br.com.socialbase.ml.digester.services.email.EmailSender;
import br.com.socialbase.ml.digester.model.Group;
import br.com.socialbase.ml.digester.model.Post;
import br.com.socialbase.ml.digester.model.User;

import java.util.List;

public class StatisticsEmail extends EmailSender {

    public StatisticsEmail(User user, List<Post> posts, List<User> users, List<Group> groups) {
        super(user, posts, users, groups);
    }

    public String createBody(){
        String body="<html>";

        body+="<br><div style='width=400px;background: #F6F6F6;'><h1 style='font-family=\".SFNSDisplay, .SF NS Display\";font-size=\"20\"' >Olá "+user.getName()+", faz tempo que não te vemos por aqui. Achamos que esses conteúdos podem lhe interessar.</h1>";

        for(Post post:posts){

            String postBody = "<div style='width=300px;background: #FFFFF; padding: 10px 15px; border-radius: 5px;'>";
            postBody+="<div style='color:#26A0F5'>"+post.getUser().getName()+"</div>";
            postBody+="<div style='fill=\"#737373\" font-family=\"Helvetica\" font-size=\"14\"'>"+(post.getBody().length()>200?post.getBody().substring(0,200)+"...":post.getBody())+"</div>";
            postBody+="<div font-family=\"Helvetica\" style=\"text-align: center; background-color: #26A0F5; padding: 10px 15px; border-radius: 5px;\">" +
                    "<a style=\"width=150px; color: #fff; font-size: 18px; letter-spacing: 1px; text-decoration: none; text-shadow: 0px 2px 2px #4db24c; font-family: Arial, Helvetica, sans-serif;\"" +
                    " href='https://socialbase.socialbase.com.br/#!/activities/"+post.getPostId()+"'>" +
                    "Ver publicação" +
                    "</a>" +
                    "</div>";
            postBody+="</div><br>";
            body = body.concat(postBody);
        }

        body+="</div>";

        return body.concat("</html>");
    }
}
