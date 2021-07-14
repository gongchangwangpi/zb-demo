package com.ext.demo;

import com.ext.BizScenario;
import com.ext.ExtensionPointI;

/**
 * @author bo6.zhang
 * @date 2021/4/20
 */
public interface UserServiceExtPt extends ExtensionPointI {

    String getName(Long id);

    int getAge(BizScenario bizScenario, Long id);

}
