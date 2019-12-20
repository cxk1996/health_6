package com.itheima.dao;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberDao {

    void add(Member member);

    List<Member> selectByCondition(String telephone);

    int findMemberCountBeforeDate(String newYearMonth);
}
