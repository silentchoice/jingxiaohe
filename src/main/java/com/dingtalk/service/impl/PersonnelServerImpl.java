package com.dingtalk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.mapper.PersonnelMapper;
import com.dingtalk.model.Personnel;
import com.dingtalk.service.PersonnelServer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// UserService.java
@Service
public class PersonnelServerImpl extends ServiceImpl<PersonnelMapper, Personnel> implements PersonnelServer {
}

