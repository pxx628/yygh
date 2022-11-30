package com.pxx.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pxx.yygh.common.result.Result;
import com.pxx.yygh.common.utils.MD5;
import com.pxx.yygh.hosp.service.HospitalSetService;
import com.pxx.yygh.model.hosp.HospitalSet;
import com.pxx.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.startup.HostRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(value = "医院设置信息")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


    /**
     * 查询医院设置表中的所有记录
     * @return
     */
    @ApiOperation("获取所有的设置信息")
    @GetMapping("/findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> hospitalSetList = hospitalSetService.list();
        return Result.ok(hospitalSetList);
    }

    /**
     * 根据id删除设置
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除设置")
    @DeleteMapping("/{id}")
    @ApiParam("id")
    public Result removeHospSet(@PathVariable("id") Long id){
        boolean flag = hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 分页查询医院设置
     * @param current
     * @param limit
     * @param hospitalSetQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询医院设置")
    @PostMapping("/findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable("current") Long current,
                                  @PathVariable("limit") Long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){

        Page<HospitalSet> page = new Page<>(current,limit);

        LambdaQueryWrapper<HospitalSet> wrapper=new LambdaQueryWrapper<>();
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();

        if (!StringUtils.isEmpty(hoscode)){
            wrapper.eq(HospitalSet::getHoscode,hoscode);
        }
        if (!StringUtils.isEmpty(hosname)){
            wrapper.like(HospitalSet::getHosname,hosname);
        }

        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        return Result.ok(pageHospitalSet);

    }

    /**
     * 添加医院设置
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value = "添加医院设置")
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        hospitalSet.setStatus(1);
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+ new Random().nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if (save){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据医院获取设置
     * @param id
     * @return
     */
    @ApiOperation(value = "根据医院获取设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable("id") Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value ="修改医院设置" )
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 批量删除医院设置
     * @param idList
     * @return
     */
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {

        boolean flag = hospitalSetService.removeByIds(idList);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 设置是否启用
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "设置是否启用")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    /**
     * 发送签名密钥——待完善
     * @param id
     * @return
     */
    @ApiOperation(value = "发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }


}

