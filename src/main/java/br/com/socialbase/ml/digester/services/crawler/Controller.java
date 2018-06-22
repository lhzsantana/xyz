package br.com.socialbase.ml.digester.services.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.yaml.snakeyaml.error.Mark;

public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        String [] seeds = {
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=g",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=h",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=i",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=j",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=k",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=l",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=m",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=n",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=o",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=p",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=q",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=r",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=s",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=t",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=u",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=v",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=x",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=y",
            "http://www1.abracom.org.br/cms/opencms/abracom/pt/associados/resultado_busca.html?letra=w",
        };

        for(String seed: seeds) {
            controller.addSeed(seed);
        }

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MarketingCrawler.class, numberOfCrawlers);
    }
}