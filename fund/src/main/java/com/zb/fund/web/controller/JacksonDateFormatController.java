package com.zb.fund.web.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zb.commons.dto.RestfulResultDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zhangbo
 */
@RestController
public class JacksonDateFormatController {
    
    @GetMapping(value = "/date")
    public RestfulResultDto date() {
        User user = new User(2L, new Date(), new Date());
        return RestfulResultDto.succeed(user);
    }
    
    static class User {
        private Long id;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;
        private Date modifyTime;

        public User(Long id, Date createTime, Date modifyTime) {
            this.id = id;
            this.createTime = createTime;
            this.modifyTime = modifyTime;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
        }
    }
}
