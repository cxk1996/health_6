package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表业务层处理
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 会员折线图
     * @param monthList
     * @return
     */
    @Override
    public List<Integer> getMembersByYearMoth(List<String> monthList) {
        //sql:select count(*) from t_member where regTime <= '2019-04-31'
        List<Integer> memberCounts = new ArrayList<>();
        if(monthList != null){
            for (String yearMonth : monthList) {
                //拼接+“-31”
                String newYearMonth = yearMonth+"-31";
                int memberCount = memberDao.findMemberCountBeforeDate(newYearMonth);
                memberCounts.add(memberCount);
            }
        }
        return memberCounts;
    }
}
