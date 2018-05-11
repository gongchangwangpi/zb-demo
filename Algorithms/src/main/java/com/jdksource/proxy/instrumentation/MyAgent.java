package com.jdksource.proxy.instrumentation;

import java.lang.instrument.Instrumentation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class MyAgent {

    public static void premain(String agentOps, Instrumentation inst){
        inst.addTransformer(new MyClassFileTransformer());
    }

}
