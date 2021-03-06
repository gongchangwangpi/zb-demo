package ${package.Controller};

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${table.entityPath}Service;

    @GetMapping("/{id}")
    public ${entity} get(@PathVariable("id") Long id) {
        return ${table.entityPath}Service.getById(id);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return ${table.entityPath}Service.removeById(id);
    }

    @PutMapping("/{id}")
    public Boolean update(@PathVariable("id") Long id, @RequestBody ${entity} ${table.entityPath}) {
        return ${table.entityPath}Service.updateById(${table.entityPath});
    }

    @PostMapping("/")
    public Long save(@RequestBody ${entity} ${table.entityPath}) {
        ${table.entityPath}Service.save(${table.entityPath});
        return ${table.entityPath}.getId();
    }

}
</#if>
