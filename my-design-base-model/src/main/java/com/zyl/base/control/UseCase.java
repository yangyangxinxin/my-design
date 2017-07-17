package com.zyl.base.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyl.base.jdbc.DataSource;
import com.zyl.base.model.Model;


/**
 * 接口封装基础类
 *
 * @author ZhouGuoQiang
 *         2017年3月5日
 * @version 1.0
 *          UseCase
 */
public class UseCase {

    private static Logger logger = Logger.getLogger(UseCase.class);

    /**
     * 一些限定的sql语句例如 where语句,order语句等
     */
    private String sql;
    /**
     * 查询或变动的model类
     */
    private Model model;
    /**
     * 分页查询所需的页码
     */
    private int pageNum = 1;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 方法调用后参数填入
     */
    private Object result;

    /**
     * 返回结果值
     */
    private int res = 1;

    /**
     * 传入参数值
     */
    private Map<String, Object> input;

    /**
     * sql ID
     */
    private String sqlId;

    /**
     * 执行状态<缓存开关>
     */
    private boolean state = false;

    /**
     * 数据源引入
     */
    public static DataSource datasource;


    /**
     * 数据修改或添加
     *
     * @return Object
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object edit() {
        try {
            //检查传入参数是否有主键信息.
            String guid = (String) this.input.get(this.model.getPrimayKey());
            //如果有这视为修改,没有则视为新增
            if (guid == null || guid.isEmpty()) {
                if (datasource.add(this.model) != null) {
                    model.setValue("Ip", model.getIp());
                    result = datasource.selectById(model);
                }
                return result;
            }
            if (datasource.edit(this.model) != null) {
                model.setValue("Ip", model.getIp());
                result = datasource.selectById(model);
            }
            if (result == null) {
                this.res = -1;
                this.msg = "没有此" + model.getId() + "外建记录";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            this.res = -1;
            this.msg = "edit 方法执行错误";
            return null;
        }
    }

    /**
     * 数据添加
     *
     * @return Object
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object add() {
        try {
            if (datasource.add(this.model) != null) {
                result = datasource.selectById(model);
            }
            return result;
        } catch (Exception e) {
            this.msg = "add方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据modle删除单条数据
     *
     * @return Object
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object delete() {
        try {
            int i = datasource.delete(this.model);
            if (i == 1) {
                this.msg = "删除成功";
            } else {
                this.res = -1;
                this.msg = "删除失败";
            }
            this.result = i;
            return result;
        } catch (Exception e) {
            this.msg = "deleteModel方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据model与sql删除指定数据
     *
     * @return Object
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object deleteBatch() {
        try {
            this.result = datasource.delete(this.model, this.sql);
            return result;
        } catch (Exception e) {
            this.msg = "delete方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分页查询
     *
     * @return Map
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object listByPage() {
        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        try {
            List<Object> datalist = new ArrayList<>();
            List<Model> source = datasource.listByPage(this.model, this.pageNum, this.sql);
            for (Model model : source) {
                datalist.add(model.getModelValue());
            }
            map.put("list", datalist);
            if (this.sql == null || this.sql.isEmpty()) {
                map.put("total", this.model.getPage().getTotal());
            } else {
                map.put("total", datasource.count(model, sql));
            }
            map.put("pageNum", this.pageNum);
            map.put("pageSize", this.model.getPage().getPageSize());
            map.put("pageTotal", this.model.getPage().getPageTotal());
            list.add(map);
            this.result = list;
            return result;
        } catch (Exception e) {
            this.msg = "listByPage方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 指定查询
     *
     * @return List
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object search() {
        try {
            this.result = datasource.selectSearch(this.model, this.sql);
            return result;
        } catch (Exception e) {
            this.msg = "search方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据主键去查询单挑数据
     *
     * @return Map
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Model view() {
        try {
            Model model = datasource.selectById(this.model);
            if (model != null) {
                model.setIsNew(false);
            }
            this.result = model;
            return model;
        } catch (Exception e) {
            this.msg = "view方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询限定条件下的第一条数据
     *
     * @return Map
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object findFirst() {
        try {
            List<Model> list = datasource.selectSearch(this.model, this.sql);
            if (list != null && list.size() != 0) {
                this.result = list.get(0);
                return result;
            }
            return result;
        } catch (Exception e) {
            this.msg = "findFirst方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询限定条件下的最后一条数据
     *
     * @return Map
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object findLast() {
        List<Model> list;
        try {
            list = datasource.selectSearch(this.model, this.sql);
            if (list != null && list.size() != 0) {
                this.result = list.get(list.size() - 1);
                return result;
            }
            return result;
        } catch (Exception e) {
            this.msg = "findLast方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询限定条件的条数,不能使用分组,只是单一的查询某字段想相同的数量.例如用户性别为"男"的总数
     *
     * @return Integer
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Integer count() {
        try {
            Integer i = datasource.count(this.model, this.sql);
            this.result = i;
            return i;
        } catch (Exception e) {
            this.msg = "count方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 原生sql查询
     *
     * @return Object
     * @author ZhouGuoQiang
     * 2017年3月5日
     */
    public Object getBySql() {
        try {
            this.result = datasource.getBySql(this.sql);
            return result;
        } catch (Exception e) {
            this.msg = "getBySql方法执行错误";
            this.res = -1;
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }


    /****************** get set method start ******************/

    public String getSql() {
        return sql;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(Map<String, Object> input) {
        this.input = input;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSqlId() {
        return sqlId;
    }
    /****************** get set method end ******************/
}
