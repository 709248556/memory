package com.example.memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2021-05-28 14:11
 **/
@RestController
@CrossOrigin
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @PostMapping("submit")
    public void submit(@RequestBody ExamDTO examDTO) {
         examService.insert(examDTO);
    }

    @PostMapping("edit")
    public ExamDTO edit(@RequestBody ExamDTO examDTO) {
         return examService.edit(examDTO);
    }

    @GetMapping("getQuestion/{type}")
    public String getQuestion(@PathVariable Integer type) {
        return examService.getQuestion(type);
    }

    @GetMapping("getSingleQuestion")
    public ExamDTO getSingleQuestion(Integer type) {
        return examService.getSingleQuestion(type);
    }

    @GetMapping("getSingleAnswer")
    public ExamDTO getSingleAnswer(Integer id) {
        return examService.getSingleAnswer(id);
    }

    @GetMapping("getAnswer")
    public List<ExamDTO> getAnswer() {
        return examService.getAnswer();
    }

    @GetMapping("raise")
    public void raise(String id) {
        examService.raise(id);
    }
    @GetMapping("lower")
    public void lower(String id) {
        examService.lower(id);
    }
    @GetMapping("delete")
    public void delete(String id) {
        examService.delete(id);
    }
}
