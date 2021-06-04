package com.demo.test;

import com.demo.bean.Deparment;
import com.demo.bean.Role;
import com.demo.bean.User;
import com.demo.dao.DeparmentRepository;
import com.demo.dao.RoleRepository;
import com.demo.dao.UserRepository;
import com.demo.util.JpaConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class MysqlTest {

    private static Logger logger = LoggerFactory.getLogger(MysqlTest.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeparmentRepository deparmentRepository;

    @Autowired
    RoleRepository roleRepository;

    public void initData(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        deparmentRepository.deleteAll();

        Deparment deparment = new Deparment();
        deparment.setName("开发部");
        deparmentRepository.save(deparment);
        Assert.notNull(deparment.getId(),"部门名称不能为空");

        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        Assert.notNull(role,"角色名称不能为空");

        User user = new User();
        user.setName("user");
        user.setCreatedate(new Date());
        user.setDeparment(deparment);

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Assert.notNull(roles,"角色list不能为空");
        user.setRoles(roles);
    }

    @Test
    public void findPage(){
        //springboot2.2.1（含）以上的版本已经不能再实例化了，构造方法已经是私有的了！
        //Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,"id"));
        Page<User> page = userRepository.findAll(pageable);
        Assert.notNull(page,"page为空");
        for (User user: page.getContent()) {
            logger.info("====user==== user name:{},");
        }
    }
}
