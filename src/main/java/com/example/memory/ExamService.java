package com.example.memory;

import java.util.List;

/**
 * @ClassName: ExamService.java
 * @Description:
 * @author yanlianglong
 * @date 2021-5-28
 */
public interface ExamService {

    /**
     * 新增
     */
     void insert(ExamDTO record);

    /**
     * 删除
     */
    void delete(Long id);

    String getQuestion(Integer type);

    ExamDTO getSingleQuestion(Integer type);
    ExamDTO getSingleAnswer(Integer id);

    List<ExamDTO> getAnswer();

    void raise(String id);
    void lower(String id);
    void delete(String id);

    ExamDTO edit(ExamDTO examDTO);
}

