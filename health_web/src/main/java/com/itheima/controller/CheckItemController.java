package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

            return checkItemService.findPage(queryPageBean);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    public Result delete(int id) {

        // 1.查询是否添加了检查组
        int count = checkItemService.findById(id);

        if (count>0){
            return new Result(false,"检查项已添加检查组,无法删除");
        }else {
            // 2.删除检查项
            try {
                checkItemService.deleteById(id);
                return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
            }
        }
    }

    /**
     * 修改窗口回显查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/editFindById", method = RequestMethod.GET)
    public CheckItem editFindById(int id) {
        return checkItemService.editFindById(id);
    }

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }

    /**
     * 查询所有检查项
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)

    public Result findAll() {


        List<CheckItem> checkItemList = checkItemService.findAll();
        if (checkItemList != null && checkItemList.size()>0) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
        }else{

            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);

        }
    }



}
