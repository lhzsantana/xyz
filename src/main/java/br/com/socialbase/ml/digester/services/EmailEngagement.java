package br.com.socialbase.ml.digester.services;

import br.com.socialbase.ml.digester.model.*;
import br.com.socialbase.ml.digester.model.graph.UserGraph;
import br.com.socialbase.ml.digester.repository.*;
import br.com.socialbase.ml.digester.repository.graph.UserGraphRepository;
import br.com.socialbase.ml.digester.services.email.digest.DigestEmail;
import br.com.socialbase.ml.digester.services.email.disappeared.DisappearedEmail;
import br.com.socialbase.ml.digester.services.email.statistics.StatisticsEmail;
import br.com.socialbase.ml.digester.services.engagement.EngagementService;
import br.com.socialbase.ml.digester.services.ml.collaborativeFiltering.ModelTrainer;
import com.sendgrid.SendGridException;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.spark.mllib.recommendation.Rating;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class EmailEngagement {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    GroupMembershipRepository groupMembershipRepository;

    @Autowired
    EngagementService engagementService;

    @Autowired
    GroupRepository groupRepository;

    Map<Integer, Group> groupMap = new HashMap<>();

    Map<Integer, Map<Integer, View>> postViewMap = new HashMap<>();

    final Integer DAYS_TO_DATE = 7;

    MultiMap multiMap = new MultiValueMap();

    static int countNotEngaged = 0;
    static int countNeverEntered = 0;
    static int countDigest = 0;
    static int countStatistics = 0;

    private Set<Post> getValidPosts(List<Post> postsForUser, User user, boolean view){

        Set<Post> posts = new HashSet<>();

        for(Post post: postsForUser){

            if(validate(user, post, view)){

                if(post.getObjectType().equals("group")){

                    Group group = groupMap.get(post.getObjectId());

                    if(group.getType().equals("announcement")){
                        post.getUser().setName(group.getTitle());
                    }
                }

                posts.add(post);

                if(posts.size()>3) break;
            }
        }
        return posts;
    }


    private void recommendAndSend(User user, List<Post> topPosts, List<Post> postsForUser, List<User> neverLogin, String nomeRede) throws IOException, SendGridException {

        Set<Post> posts = getValidPosts(postsForUser, user, true);

        if(posts.isEmpty()) {

            System.out.println("StatisticsEmail for " + user.getName());

            /*
            Date targetDate = new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate();

            Long totalPosts = postRepository.countAllByUserAndCreatedAtGreaterThanEqual(user, targetDate);
            Long totalViews = viewRepository.countViewByUserAndViewedAtGreaterThanEqual(user, targetDate);

            List<Integer> groupIds = user.getGroups().stream().map(x -> x.getGroupId()).collect(Collectors.toList());

            Long totalComments = commentRepository.countCommentForGroups(targetDate, groupIds);
            Long totalLikes = likeRepository.countLikesByGroups(targetDate, groupIds);

            String messages[] = {
                    totalPosts + " publicações e " + totalViews + " visualizações",
                    totalComments + " comentários e " + totalLikes + " curtidas"
            };

            */

            System.out.println("Getting valid posts for " + user.getName());

            Set<Post> validTopPosts = getValidPosts(topPosts, user, false);

            StatisticsEmail statisticsEmail = new StatisticsEmail(user, new ArrayList<Post>(validTopPosts), neverLogin, null);

            statisticsEmail.send(user.getName() + " reveja a semana na "+nomeRede);
            countStatistics++;

        }else {
            System.out.println("DigestEmail for " + user.getName());

            DigestEmail digestEmail = new DigestEmail(user, new ArrayList<Post>(posts), neverLogin, null);
            digestEmail.send(user.getName() + " veja o que você perdeu na "+nomeRede+"...");

            countDigest++;
        }
    }

    private boolean validate(User user, Post post, boolean view){

        boolean result =  validateAccess(user, post)
                && !validateBlacklist(user, post)
                && !post.getBody().toLowerCase().contains("carregando");

        if(view){
            result=result&&validateView(user, post);
        }

        return result;
    }

    private boolean validateView(User user, Post post){
        View view = new View();
        view.setPost(post);
        view.setUser(user);
        //return user.getViews().contains(view);

        return viewRepository.findFirstByUserAndPost(user, post)==null;

        //return postViewMap.get(user.getUserId()).get(post.getPostId())==null;
    }

    private boolean validateAccess(User user, Post post){

        if(post.getObjectType().equals("group")) {
            Group group = new Group();
            group.setGroupId(post.getObjectId());
            //return user.getGroups().contains(group);
            return groupMembershipRepository.findFirstByUserIdAndGroupIdAndActive(user.getUserId(), post.getObjectId(), 1) != null;
        }
        return true;
    }

    private boolean validateBlacklist(User user, Post post){

        Set<Integer> blacklist = new HashSet<>();
        //blacklist.add(1135);
        //blacklist.add(849);

        if(post.getObjectType().equals("group")){
            return blacklist.contains(post.getObjectId());
        }

        return true;
    }

    public void sendEmails(String nomeRede) throws Exception {

        System.out.println("Loading groups");

        for(Group group:groupRepository.findAll()){
            groupMap.put(group.getGroupId(), group);
        }
        System.out.println("Loading views");

        for(View view: viewRepository.findAllByViewedAtGreaterThanEqual(new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate())){
            Map<Integer, View> viewMap = new HashMap<>();

            if(view!=null&&view.getPost()!=null) {
                viewMap.put(view.getPost().getPostId(), view);

                postViewMap.put(view.getUser().getUserId(), viewMap);
            }
        }

        Date targetDate = new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate();

        System.out.println("Finding top engaged");
        List<User> topEngagedUsers = userRepository.findTopEngaged(targetDate);
        List<User> neverLogin = userRepository.findByEnabledAndLastActivityDateLessThanEqual(true, new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate());

        List<Rating> ratings = new ArrayList<>();

        List<Post> topPosts = postRepository.findAllByCreatedAtGreaterThanEqualOrderByLikesDesc(targetDate).subList(0,10);

        for(Like like: (List<Like>) likeRepository.findAllByCreatedAtGreaterThanEqual(new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate())){

            if(like.getUser()==null || like.getPost()==null) continue;

            ratings.add(new Rating(like.getUser().getUserId(), like.getPost().getPostId(), 1));
        }

        for(Comment comment: (List<Comment>) commentRepository.findAllByCreatedAtGreaterThanEqual(new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate())){
            ratings.add(new Rating(comment.getUserId(), comment.getPost().getPostId(), 1));
        }

        System.out.println("Training");
        ModelTrainer.train(ratings);

        System.out.println("Finding enabled");
        List<User> users = userRepository.findByEnabled(true);

        for(User user : users){

            if(user.getUserId()<590) continue;

            System.out.println("Checking user " + user.getName() + " (" + user.getUserId()+") out of "+users.size());

            Date lastActivityDate = null;

            List<Date> dates = userRepository.findAccesses(user);
            if(dates.size() > 0){
                lastActivityDate = dates.get(0);
            }

            if(lastActivityDate==null || lastActivityDate.before(new DateTime(new Date()).minusDays(DAYS_TO_DATE).toDate())){
                System.out.println("NotEngagedEmail for " + user.getName() +" "+ user.getLastActivityDate());

                List<Post> posts = new ArrayList<Post>(getValidPosts(topPosts, user, false));

                DisappearedEmail disappearedEmail = new DisappearedEmail(user, posts, topEngagedUsers, null);

                disappearedEmail.send(user.getName() + " a gente sente sua falta...");

                countNotEngaged++;
            }else {
                recommendAndSend(user, topPosts, (List<Post>) postRepository.findAll(ModelTrainer.recommend(user.getUserId())), neverLogin, nomeRede);
            }

        }

        System.out.print("countDigest "+countDigest);
        System.out.print("countNotEngaged "+countNotEngaged);
        System.out.print("countStatistics "+countStatistics);
        System.out.print("countNeverEntered "+countNeverEntered);

    }
}
