package com.springapp.retry;

/**
 * @author zhangbo
 */
public class UserService {
    
    int count = 0;
    
    @Retry(count = 5, delay = 1000)
    public String say(String name) {
        System.out.println("say ---- " + name);
        
        if (count++ < 2) {
            int i = 1 / 0;
        }
        
        return "hello " + name;
    }
    
}
