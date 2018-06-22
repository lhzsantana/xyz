package br.com.socialbase.ml.digester.repository.graph;

import br.com.socialbase.ml.digester.model.graph.GroupGraph;
import br.com.socialbase.ml.digester.model.graph.UserGraph;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GroupGraphRepository extends Neo4jRepository<GroupGraph, Long> {
}
