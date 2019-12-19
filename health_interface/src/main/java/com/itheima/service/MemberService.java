package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {

    List<Member> selectByCondition(String telephone);

    void add(Member member);
}
