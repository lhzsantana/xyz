package br.com.socialbase.ml.digester.repository.graph;

import br.com.socialbase.ml.digester.model.graph.UserGraph;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserGraphRepository extends Neo4jRepository<UserGraph, String> {
}
