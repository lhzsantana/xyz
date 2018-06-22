package br.com.socialbase.ml.digester.repository;

import br.com.socialbase.ml.digester.model.GroupMembership;
import br.com.socialbase.ml.digester.model.View;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface GroupMembershipRepository extends PagingAndSortingRepository<GroupMembership, Integer> {

    GroupMembership findFirstByUserIdAndGroupIdAndActive(Integer userId, Integer groupId, Integer active);
}