package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

///checkgroup/findGroupById.do?groupId=5
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_ADD')")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return checkGroupService.findPage(queryPageBean);
    }


    /**
     * 检查组编辑回写查询
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/findGroupById", method = RequestMethod.GET)
    public Result findGroupById(Integer groupId) {

        try {
            CheckGroup checkGroup = checkGroupService.findGroupById(groupId);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 检查组关联检查项回显查询
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/findItemIdsByGroupId", method = RequestMethod.GET)
    public Result findItemIdsByGroupId(Integer groupId) {

        try {
            List<Integer> itemIds = checkGroupService.findItemIdsByGroupId(groupId);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, itemIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }


    /**
     * 编辑检查组
     *
     * @param checkGroup
     * @param itemIds
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_EDIT')")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] itemIds) {

        try {
            checkGroupService.edit(checkGroup, itemIds);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_DELETE')")
    public Result delete(Integer id) {
        try {
            checkGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll() {

        List<CheckGroup> groupList = checkGroupService.findAll();

        if (groupList != null && groupList.size() > 0){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,groupList);
        }else {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


}
