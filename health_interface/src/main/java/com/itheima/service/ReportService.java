package com.itheima.service;

import java.util.List;
import java.util.Map;

public interface ReportService {

    List<Integer> getMembersByYearMoth(List<String> stringList);

    Map getSetmealReport();

    Map<String,Object> getBusinessReportData() throws Exception;
}
