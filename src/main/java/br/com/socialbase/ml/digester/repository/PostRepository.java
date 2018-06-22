package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.Like;
import br.com.socialbase.ml.digester.model.Post;
import br.com.socialbase.ml.digester.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    List<Post> findAllByCreatedAtGreaterThanEqual(Date createdAt);

    List<Post> findAllByCreatedAtGreaterThanEqualOrderByLikesDesc(Date createdAt);

    Long countAllByUserAndCreatedAtGreaterThanEqual(User user, Date createdAt);


}