package org.zhang.purchasandsale.auth;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private  String id;
	private String userId;//用户id（主键）
	private String userCode;// 用户账号
	private String username;// 用户名称
}
