package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @param ids
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] ids) {

        // 添加检查组
        checkGroupDao.addCheckGroup(checkGroup);

        // 设置检查组与检查项的关系
        this.addCheckGroupAndCheckItem(checkGroup,ids);


    }

    /**
     * 检查组分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        // 1.设置分页参数(当前页码,每页显示记录数)
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        // 2.需要分页表查询语句,有条件的要带条件
        Page<CheckGroup> checkGroupPage = checkGroupDao.queryByCondition(queryPageBean.getQueryString());

        return new PageResult(checkGroupPage.getTotal(), checkGroupPage.getResult());
    }

    /**
     * 检查组编辑回显查询
     * @param groupId
     * @return
     */
    @Override
    public CheckGroup findGroupById(Integer groupId) {

        return checkGroupDao.findGroupById(groupId);
    }

    /**
     * 检查组关联检查项回显查询
     * @param groupId
     * @return
     */
    @Override
    public List<Integer> findItemIdsByGroupId(Integer groupId) {

        return checkGroupDao.findItemIdsByGroupId(groupId);
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param itemIds
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] itemIds) {

        checkGroupDao.editCheckGroup(checkGroup);

        checkGroupDao.deleteGroupAndItemByGroupId(checkGroup.getId());

        this.addCheckGroupAndCheckItem(checkGroup,itemIds);

    }

    /**
     * 删除检查组
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //删除检查组与检查项的关联
        checkGroupDao.deleteGroupAndItemByGroupId(id);

        //删除检查组
        checkGroupDao.delete(id);
    }

    @Override
    public List<CheckGroup> findAll() {

        return checkGroupDao.findAll();
    }


    /**
     * 设置检查项与检查组的关系
     */
    public void addCheckGroupAndCheckItem(CheckGroup checkGroup, Integer[] ids) {

        Integer groupId = checkGroup.getId();
        if (ids != null && ids.length > 0) {
            Map<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id", groupId);
            for (Integer itemId : ids) {
                map.put("checkitem_id", itemId);
                try {
                    checkGroupDao.addCheckGroup_CheckItem(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        }

    }
}
