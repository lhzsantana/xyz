package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.Comment;
import br.com.socialbase.ml.digester.model.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {

    List<Comment> findAllByCreatedAtGreaterThanEqual(Date createdAt);

    @Query("SELECT count(c) FROM Comment c " +
            "INNER JOIN c.post p " +
            "WHERE p.objectId IN :groups " +
            "AND c.createdAt > :lastActivityDate ")
    Long countCommentForGroups(
            @Param("lastActivityDate") Date lastActivityDate,
            @Param("groups") List<Integer> groups
    );
}