package com.test.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author zhangbo
 */
public class FastjsonFormatBigDecimal {

    public static void main(String[] args) {

        User user = new User("zhangsan", new BigDecimal("20.12845"));

        System.out.println(JSON.toJSONString(user, new BigDecimalValueFilter()));

    }
    
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private BigDecimal amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
    
    static class BigDecimalValueFilter implements ValueFilter {
        static final DecimalFormat FORMAT = new DecimalFormat("0.00");
        @Override
        public Object process(Object source, String name, Object value) {
            if (value != null && value instanceof BigDecimal) {
                value = FORMAT.format(value);
            }
            return value;
        }
    } 
}
