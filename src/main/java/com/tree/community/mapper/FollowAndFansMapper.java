package com.tree.community.mapper;

import com.tree.community.model.FollowAndFans;
import com.tree.community.model.FollowAndFansExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowAndFansMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    long countByExample(FollowAndFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int deleteByExample(FollowAndFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int insert(FollowAndFans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int insertSelective(FollowAndFans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    List<FollowAndFans> selectByExampleWithRowbounds(FollowAndFansExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    List<FollowAndFans> selectByExample(FollowAndFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    FollowAndFans selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int updateByExampleSelective(@Param("record") FollowAndFans record, @Param("example") FollowAndFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int updateByExample(@Param("record") FollowAndFans record, @Param("example") FollowAndFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int updateByPrimaryKeySelective(FollowAndFans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_and_fans
     *
     * @mbg.generated Sun Jun 12 23:42:30 CST 2020
     */
    int updateByPrimaryKey(FollowAndFans record);
}