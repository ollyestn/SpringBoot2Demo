package com.xyai.shenniuban.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyai.shenniuban.entity.TestData;
import com.xyai.shenniuban.mapper.TestDataMapper;
import com.xyai.shenniuban.service.TestDataService;
import org.springframework.stereotype.Service;

@Service
public class TestDataServiceImpl extends ServiceImpl<TestDataMapper, TestData> implements TestDataService {
}
