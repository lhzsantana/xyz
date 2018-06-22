package br.com.socialbase.ml.digester;

import br.com.socialbase.ml.digester.model.*;
import br.com.socialbase.ml.digester.services.EmailEngagement;
import br.com.socialbase.ml.digester.services.email.disappeared.DisappearedEmail;
import br.com.socialbase.ml.digester.services.email.statistics.StatisticsEmail;
import br.com.socialbase.ml.digester.services.ml.collaborativeFiltering.ModelTrainer;
import br.com.socialbase.ml.digester.model.graph.UserGraph;
import br.com.socialbase.ml.digester.repository.*;
import br.com.socialbase.ml.digester.repository.graph.UserGraphRepository;
import br.com.socialbase.ml.digester.services.email.digest.DigestEmail;
import br.com.socialbase.ml.digester.services.engagement.EngagementService;
import com.sendgrid.SendGridException;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.spark.mllib.recommendation.Rating;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@EnableNeo4jRepositories(basePackages = "br.com.socialbase.ml.digester.repository.graph")
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@Component
public class Main implements CommandLineRunner {

    @Autowired
    EmailEngagement emailEngagement;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        emailEngagement.sendEmails("SocialBase");

    }
}