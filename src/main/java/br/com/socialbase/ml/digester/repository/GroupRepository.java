package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.Group;
import br.com.socialbase.ml.digester.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {

}