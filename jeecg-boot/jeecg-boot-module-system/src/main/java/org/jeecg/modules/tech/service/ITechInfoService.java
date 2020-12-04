package org.jeecg.modules.tech.service;

import org.jeecg.modules.tech.entity.TechInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.tech.entity.TechInfoVO;

/**
 * @Description: tech_info
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
public interface ITechInfoService extends IService<TechInfo> {

    public void updateTechInfo(TechInfoVO techInfoVO);
}
