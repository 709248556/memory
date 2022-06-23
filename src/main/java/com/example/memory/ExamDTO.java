package com.example.memory;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**  
* @Title: ExamQuery.java
* @Package
* @Description:
* @author yanlianglong
* @date 2021-5-28
*/
@Data
public class ExamDTO implements Serializable{

	private static final long serialVersionUID = 9487958886720634L ;
	private Integer id;
	private String question;
	private String answer;
	private String tip;
	private Integer type;
	private Integer weight;
	private Integer deleted;
	private Date craetTime;
}
