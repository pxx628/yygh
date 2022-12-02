package com.pxx.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pxx.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface DictService extends IService<Dict> {
    List<Dict> findChlidData(Long id);

    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);


    //导入数据字典
    void importData(MultipartFile file);
}
