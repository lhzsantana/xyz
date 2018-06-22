package br.com.socialbase.ml.digester.services;

import org.springframework.stereotype.Component;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ReIndex {

    TransportClient client = null;

    public void reindexAll(){

    }
/*
    public ElasticsearchService() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

    }

    public void index(Post post) throws IOException {

        XContentBuilder content =
                XContentFactory.jsonBuilder()
                        .startObject()
                        .field("location")
                        .latlon( tweet.getLat(), tweet.getLon())
                        .endObject();

        indexer("tweet", content, tweet.getId());
    }

    public void index(Crash crash) throws IOException {

        XContentBuilder content =
                XContentFactory.jsonBuilder()
                        .startObject()
                        .field("location")
                        .latlon( crash.getLat(), crash.getLon())
                        .field("tipo", crash.getTipo())
                        .field("data", crash.getData())
                        .endObject()
                ;

        System.out.println(content.string());

        indexer("crash", content, crash.getId());

    }

    private void indexer(String type, XContentBuilder content, String id){

        try {
            IndexResponse response = client.prepareIndex()
                    .setId(id)
                    .setIndex("erbd")
                    .setType(type)
                    .setSource(content)
                    .execute().actionGet();
            System.out.println(response.status());

        }catch(Exception e){
            e.printStackTrace();
        }

    }*/
}