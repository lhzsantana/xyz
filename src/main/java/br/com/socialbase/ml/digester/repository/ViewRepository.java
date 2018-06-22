package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.Post;
import br.com.socialbase.ml.digester.model.User;
import br.com.socialbase.ml.digester.model.View;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.*;

public interface ViewRepository extends PagingAndSortingRepository<View, Integer> {

    View findFirstByUserAndPost(User user, Post post);

    Long countViewByUserAndViewedAtGreaterThanEqual(User user, Date viewedAt);

    List<View> findAllByViewedAtGreaterThanEqual(Date viewedAt);


}