package org.zhang.purchasandsale.Entity;

import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    private static final long serialVersionUID = -3320971805590503443L;

    @Id
    private String id;


    private String userName;
    private String loginName;
    private String loginPassword;
    private String mobile;
    @Column(name = "create_date", columnDefinition = "datetime")
    private Date createDate;
    @Column(name = "last_login_time", columnDefinition = "datetime")
    private Date lastLoginTime;
    private Integer count;
}
