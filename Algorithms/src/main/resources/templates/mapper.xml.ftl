<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.name},
</#list>
        ${table.fieldNames}
    </sql>

</#if>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        insert into ${table.name}
        (
        <#list table.fields as field>
            <#if !field.keyFlag>
            ${field.name}<#sep>,</#sep>
            </#if>
        </#list>
        )
        values
        (
    <#list table.fields as field>
        <#if !field.keyFlag>
            <#noparse>#{</#noparse>${field.propertyName}}<#sep>,</#sep>
        </#if>
    </#list>
        )
    </insert>

    <insert id="batchInsert" useGeneratedKeys="true" keyProperty="id">
        insert into ${table.name}
        (
        <#list table.fields as field>
            <#if !field.keyFlag>
            ${field.name}<#sep>,</#sep>
            </#if>
        </#list>
        )
        values
        <foreach collection="list" item="i" separator=",">
            (
            <#list table.fields as field>
                <#if !field.keyFlag>
                    <#noparse>#{i.</#noparse>${field.propertyName}}<#sep>,</#sep>
                </#if>
            </#list>
            )
        </foreach>
    </insert>

    <update id="updateById" >
        update ${table.name}
        <set>
            <#list table.fields as field>
                <#if !field.keyFlag>
            <if test="${field.propertyName} != null">
                ${field.name} = <#noparse>#{</#noparse>${field.propertyName}}<#sep>,</#sep>
            </if>
                </#if>
            </#list>
        </set>
        where id = <#noparse>#{id}</#noparse>
    </update>

</mapper>
