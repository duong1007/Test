package c1020g1.social_network.repository;

import c1020g1.social_network.model.GroupSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<GroupSocial, Integer> {

    @Query("select gr from GroupSocial gr")
    List<GroupSocial> findAllGroup();

    @Query("select gr from GroupSocial gr where gr.groupId=?1")
    GroupSocial findGroupById(Integer id);

    @Query("select gr from GroupSocial gr where gr.groupName like %?1%")
    List<GroupSocial> findGroupByGroupNameContaining(String name);

    void deleteGroupByGroupId(Integer id);

    @Modifying
    @Query(" UPDATE GroupSocial gr set gr.scope = ?3, gr.imageAvatarUrl= ?2," +
            " gr.imageBackground= ?1 where gr.groupId = ?4")
    void updateGroup(String imageBackground, String imageAvatarUrl, String scope ,Integer groupId);

    @Query(value = "select * from `group_social` where group_id = :id",nativeQuery = true)
    GroupSocial findByGroupId(@Param("id") int id);
}
