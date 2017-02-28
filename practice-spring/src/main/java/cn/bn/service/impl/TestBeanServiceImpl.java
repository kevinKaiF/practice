package cn.bn.service.impl;

import cn.bn.service.DataBeanService;
import cn.bn.service.TestBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @version: 1.0
 * 2016/10/25
 */
@Service
public class TestBeanServiceImpl implements TestBeanService {
    @Autowired
    private DataBeanService dataBeanService;

    @Override
    public void sayName() {
        System.out.println(">>>>>>>>>>>>>>> name");
    }
}
