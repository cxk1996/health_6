package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {

        checkItemDao.add(checkItem);

    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        // 1.设置分页参数(当前页码,每页显示记录数)
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        // 2.需要分页表查询语句,有条件的要带条件
         Page<CheckItem> checkItemPage = checkItemDao.queryByCondition(queryPageBean.getQueryString());

        return new PageResult(checkItemPage.getTotal(),checkItemPage.getResult());
    }

    /**
     * 查询检查项是否添加检查组
     * @param id
     * @return
     */
    @Override
    public int findById(int id) {

        return checkItemDao.findById(id);
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) {

        checkItemDao.deleteById(id);
    }

    /**
     * 编辑检查项回显查询
     * @param id
     * @return
     */
    @Override
    public CheckItem editFindById(Integer id) {
        return checkItemDao.editFindById(id);
    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


}
