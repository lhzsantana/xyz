package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    List<User> findByEnabled(boolean enabled);

    List<User> findByEnabledAndCreationDateGreaterThanEqual(boolean enabled, Date lastCreationDate);

    List<User> findByEnabledAndLastActivityDateLessThanEqual(boolean enabled, Date lastActivityDate);

    @Query("SELECT l.user FROM Post p " +
            "INNER JOIN p.likes l " +
            "WHERE l.user.enabled = true AND p.createdAt > :lastActivityDate " +
            "GROUP BY (l.user) " +
            "ORDER BY COUNT(l.user) DESC")
    List<User> findTopEngaged(@Param("lastActivityDate") Date lastActivityDate);


    @Query("SELECT a.activityDate FROM User u " +
            "INNER JOIN u.accesses a " +
            "WHERE u=:user " +
            "ORDER BY a.activityDate DESC")
    List<Date> findAccesses(@Param("user") User user);


}