package com.example.memory;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName: ExamHandleDao
 * @Description:
 * @author yanlianglong
 * @date 2021-5-28
 */
@Mapper
public interface ExamHandleDao {

    /**
     *
     */
    int getSum();

    /**
     * 新增
     */
    int insert(ExamDTO record);

   /**
     * 根据Id删除
     */
    int deleteById(long id);


    /**
     * 编辑
     */
    int update(ExamDTO record);

    /**
     * 查询
     */
	List<ExamDTO> queryList(ExamQuery query);

    /**
    * 单个查询
    */
    ExamDTO querySingle(ExamQuery query);
}