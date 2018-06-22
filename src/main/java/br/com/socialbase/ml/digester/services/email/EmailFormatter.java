package br.com.socialbase.ml.digester.services.email;

import br.com.socialbase.ml.digester.model.Group;
import br.com.socialbase.ml.digester.model.Post;
import br.com.socialbase.ml.digester.model.User;
import org.jsoup.Jsoup;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class EmailFormatter extends EmailSender {

    protected SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public EmailFormatter(User user, List<Post> posts, List<User> users, List<Group> groups, String... messages) {
        super(user, posts, users, groups, messages);
    }

    protected String post1(String templatePost1, Post post){

        String author = post.getUser().getName();

        String postHTML = templatePost1;

        String textBody = cleanMetion(Jsoup.parse(post.getBody()).text());

        String h = textBody.length()>200?textBody.substring(0,200)+"...":textBody;

        postHTML = postHTML.replace("BODY", h)
                .replace("DATE", dt.format(post.getCreatedAt()))
                .replace("AUTHOR", author.replace(".", " "))
                .replace("LINK", redeURL+"#!/activities/"+post.getPostId());

        String file = findImage(post);
        if(file!=null){
            postHTML=postHTML
                    .replace("IMAGE", "<img src='"+findImage(post)+"' width=\"500\" class=\"flexibleImage\" style=\"max-width:500px;width:100%;display:block;\" alt='"+post.getUser().getName()+"' title='"+post.getUser().getName()+"' />");
        }else{
            postHTML=postHTML
                    .replace("IMAGE", "");
        }



        return postHTML;
    }

    protected String findImageUser(User user){

        String strFile = "";

        if(user.getAvatar()!=null && !user.getAvatar().getPath().contains("profile_picture.png")){
            strFile = user.getAvatar().getPath();
            return "<img src='"+mediaURL+strFile+"' width=\"60\" class=\"flexibleImageSmall\" style=\"max-width:100%; border-radius: 50%;\" alt='"+user.getName()+"' title='"+user.getName()+"' />";
        }

        return "";
    }

    protected String findImage(Post post){

        String strFile = "";

        for(br.com.socialbase.ml.digester.model.File file : post.getAttachments()){

            if(file.getType().equals("image")){
                strFile = file.getPath();
                return mediaURL+strFile;
            }
        }

        return null;
    }

    protected String posts2e3(String templatePost2e3, Post post2, Post post3){

        String postHTML = templatePost2e3;

        String author2= post2.getUser().getName();
        String textBody2 = cleanMetion(Jsoup.parse(post2.getBody()).text());
        String h2 = textBody2.length()>200?textBody2.substring(0,200)+"...":textBody2;

        postHTML = postHTML.replace("BODY2", h2)
                .replace("DATE2", dt.format(post2.getCreatedAt()))
                .replace("AUTHOR2", author2.replace(".", " "))
                .replace("LINK2", redeURL+"#!/activities/"+post2.getPostId());

        String file2 = findImage(post2);
        if(file2!=null){
            postHTML=postHTML.
                    replace("IMAGE2", "<img src='"+file2+"' width=\"210\" class=\"flexibleImage\" style=\"height: 120px; max-width:500px;width:100%;display:block;\" alt=\"Text\" title=\"Text\" />");

        }else{
            postHTML=postHTML
                    .replace("IMAGE2", "");
        }


        String author3= post3.getUser().getName();
        String textBody3 = cleanMetion(Jsoup.parse(post3.getBody()).text());
        String h3 = textBody3.length()>200?textBody3.substring(0,200)+"...":textBody3;

        postHTML = postHTML.replace("BODY3", h3)
                .replace("DATE3", dt.format(post3.getCreatedAt()))
                .replace("AUTHOR3", author3.replace(".", " "))
                .replace("LINK3", redeURL+"#!/activities/"+post3.getPostId());


        String file3 = findImage(post3);
        if(file3!=null){
            postHTML=postHTML.
                    replace("IMAGE3", "<img src='"+file3+"' width=\"210\" class=\"flexibleImage\" style=\"height: 120px; max-width:500px;width:100%;display:block;\" alt=\"Text\" title=\"Text\" />");
        }else{
            postHTML=postHTML
                    .replace("IMAGE3", "");
        }

        return postHTML;
    }

    protected static String cleanMetion(String body){

        return body.replaceAll(	"\\[@[0-9]*:","").replace("]","");
    }

    private String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }

    protected String getPostsHTML(String templatePost1, String templatePost2e3){
        String postsHTML = "";

        List<Post> bestPosts = getBestPostsBody();

        if(bestPosts.size()>0) {
            postsHTML += post1(templatePost1, bestPosts.get(0));

            if (bestPosts.size() > 2) {
                postsHTML += posts2e3(templatePost2e3, bestPosts.get(1), bestPosts.get(2));
            }

            if (bestPosts.size() == 2) {
                postsHTML += post1(templatePost1, bestPosts.get(1));
            }
        }

        return postsHTML;
    }

    protected String getUsersHTML(String templateUser){

        String usersHTML = "";

        int count = 0;
        for(User user: users){

            Random gerador = new Random();

            if(gerador.nextInt()%2==0 && user.getUserId()>10) {
                String userHTML = templateUser.replace("NOME", user.getName().replace(".", " "))
                        .replace("IMAGE", findImageUser(user))
                        .replace("LINK", redeURL+"#!/members/"+user.getUserId()+"/profile");

                usersHTML += userHTML;

                if (++count == 3) break;
            }
        }

        return usersHTML;
    }

    private List<Post> getBestPostsBody(){

        if(posts.size()<=3){
            return posts;
        }

        List<Post> bestPosts = new ArrayList<>();
        List<Post> imagePosts = new ArrayList<>();
        List<Post> nonImagePosts = new ArrayList<>();

        for(Post post:posts){

            if(post.getAttachments()!=null && post.getAttachments().size()>0){
                imagePosts.add(post);
            }else{
                nonImagePosts.add(post);
            }
        }

        bestPosts.addAll(imagePosts);

        if(bestPosts.size()<3){
            for(Post post:nonImagePosts){
                bestPosts.add(post);

                if(bestPosts.size()==3) break;
            }
        }

        return bestPosts;
    }

}
