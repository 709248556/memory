package com.example.memory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ExamServiceImpl.java
 * @Description:
 * @author yanlianglong
 * @date 2021-5-28
 */
@Service("examService")
@Slf4j
public class ExamServiceImpl implements ExamService{

    private volatile static List<Integer> ListId = new ArrayList<>();
    private volatile static List<Integer> weightList = new ArrayList<>();

    @Autowired
    private ExamHandleDao examHandleDao;

	@Override
	public void insert(ExamDTO record) {
        try {
            record.setCraetTime(new Date());
            record.setDeleted(0);
            examHandleDao.insert(record);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
	}

    @Override
	public void delete(Long id) {
        try {
            examHandleDao.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
	}

    @Override
    public String getQuestion(Integer type) {
        ListId.clear();
	    String result = "";
	    int sum  = examHandleDao.getSum();
        ExamQuery query = new ExamQuery();
        query.setType(type);
        query.setDeleted(0);
        List<ExamDTO> examDTOS = examHandleDao.queryList(query);
        int size = examDTOS.size();
        if(size == 0) return "无该类型题目";
        int num = 1;
        List<Integer> collect = examDTOS.stream().map(ExamDTO::getId).collect(Collectors.toList());
        int min = examDTOS.stream().mapToInt(ExamDTO::getId).min().orElse(0);
        int max = examDTOS.stream().mapToInt(ExamDTO::getId).max().orElse(0);
        while (weightList.stream().reduce(Integer::sum).orElse(0) <= sum){
            Integer randomId =(int)  (Math.random()*(max-min+1)+min);
            if(collect.contains(randomId) && !ListId.contains(randomId)){
                List<ExamDTO> list = examDTOS.stream().filter(item->item.getId().equals(randomId)).collect(Collectors.toList());
                if (list.size() == 0) continue;
                ListId.add(randomId);
                weightList.add(list.get(0).getWeight());
                result = result + num+". "+ list.get(0).getQuestion() +"\n";
                num++;
            }
//                query.setId(randomId);
//                ExamDTO examDTO = examHandleDao.querySingle(query);
//                if(examDTO == null || examDTO.getDeleted() == 1) continue;
//                ListId.add(randomId);
//                weightList.add(examDTO.getWeight());
//                result = result + num+". "+ examDTO.getQuestion() +"\n";
//                num++;
        }
        weightList.clear();
        return result;
    }

    @Override
    public ExamDTO getSingleQuestion(Integer type) {
        ExamQuery query = new ExamQuery();
        query.setType(type);
        query.setDeleted(0);
        List<ExamDTO> examDTOS = examHandleDao.queryList(query);
        Integer randomId =(int)  (Math.random()*examDTOS.size());
        return examDTOS.get(randomId);
    }
    @Override
    public ExamDTO getSingleAnswer(Integer id) {
        ExamQuery query = new ExamQuery();
        query.setId(id);
        ExamDTO examDTO = examHandleDao.querySingle(query);
        return examDTO;
    }

    @Override
    public List<ExamDTO> getAnswer() {
        ExamQuery query = new ExamQuery();
        query.setIdList(ListId);
        List<ExamDTO> examDTOS = examHandleDao.queryList(query);
        ListId.clear();
        return examDTOS;
    }

    @Override
    public void raise(String ids) {
        String[] split = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for(int i = 0;i < split.length;i++){
            idList.add(Integer.valueOf(split[i]));
        }
        ExamQuery query = new ExamQuery();
        query.setIdList(idList);
        List<ExamDTO> examDTOS = examHandleDao.queryList(query);
        examDTOS.forEach(item->{
            item.setWeight(item.getWeight() + 1);
            if(item.getWeight().equals(10)){
                item.setDeleted(1);
            }
            examHandleDao.update(item);
        });
    }

    @Override
    public void lower(String id) {
        ExamQuery query = new ExamQuery();
        query.setId(Integer.valueOf(id));
        ExamDTO examDTO = examHandleDao.querySingle(query);
        if(examDTO.getWeight() > 0){
            examDTO.setWeight(examDTO.getWeight() -1);
            examHandleDao.update(examDTO);
        }
    }

    @Override
    public void delete(String id) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(Integer.valueOf(id));
        examDTO.setDeleted(1);
        examHandleDao.update(examDTO);
    }

    @Override
    public ExamDTO edit(ExamDTO examDTO) {
        examHandleDao.update(examDTO);
        return examDTO;
    }
}

