package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.Group;
import br.com.socialbase.ml.digester.model.Like;
import br.com.socialbase.ml.digester.model.View;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface LikeRepository extends PagingAndSortingRepository<Like, Integer> {

    List<Like> findAllByCreatedAtGreaterThanEqual(Date createdAt);

    @Query("SELECT count(l) FROM Like l " +
            "INNER JOIN l.post p " +
            "WHERE p.objectId IN :groups " +
            "AND l.createdAt > :lastActivityDate ")
    Long countLikesByGroups(
            @Param("lastActivityDate") Date lastActivityDate,
            @Param("groups") List<Integer> groups
    );
}