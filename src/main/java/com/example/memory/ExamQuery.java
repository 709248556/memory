package com.example.memory;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**  
* @Title: ExamQuery.java
* @Package
* @Description:
* @author yanlianglong
* @date 2021-5-28
*/
@Data
public class ExamQuery  implements Serializable{

	private static final long serialVersionUID = 9487958886720624L ;
	private Integer id;
	private String question;
	private String answer;
	private String tip;
	private Integer type;
	private Integer weight;
	private Integer deleted;


	private List<Integer> idList;
	private List<Integer> notIdList;
}
