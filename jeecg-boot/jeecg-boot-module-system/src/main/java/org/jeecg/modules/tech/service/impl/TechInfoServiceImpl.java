package org.jeecg.modules.tech.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.io.ResolverUtil;
import org.jeecg.modules.commodity.entity.JsonSpuRelation;
import org.jeecg.modules.commodity.entity.SpuRelation;
import org.jeecg.modules.commodity.mapper.SpuRelationMapper;
import org.jeecg.modules.tech.entity.TechInfo;
import org.jeecg.modules.tech.entity.TechInfoVO;
import org.jeecg.modules.tech.entity.TechSpu;
import org.jeecg.modules.tech.mapper.TechInfoMapper;
import org.jeecg.modules.tech.mapper.TechSpuMapper;
import org.jeecg.modules.tech.service.ITechInfoService;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: tech_info
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Service
public class TechInfoServiceImpl extends ServiceImpl<TechInfoMapper, TechInfo> implements ITechInfoService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private TechInfoMapper techInfoMapper;

    @Autowired
    private TechSpuMapper techSpuMapper;

    @Override
    @Transactional
    public void updateTechInfo(TechInfoVO techInfoVO){
        //更新账户信息表
        UserAccount userAccount = new UserAccount();
        userAccount.setId(techInfoVO.getId());
        userAccount.setRealName(techInfoVO.getRealName());
        userAccount.setMobile(techInfoVO.getMobile());
        userAccount.setSex(techInfoVO.getSex());
        userAccount.setBirthday(techInfoVO.getBirthday());
        userAccount.setIdCode(techInfoVO.getIdCode());
        userAccount.setNickName(techInfoVO.getNickName());
        userAccount.setAddress(techInfoVO.getAddress());
        userAccountMapper.updateById(userAccount);

        TechInfo t =  techInfoMapper.selectById(techInfoVO.getId());
        if(t!=null){
            //更新技师表
            TechInfo techInfo = new TechInfo();
            techInfo.setLinkMobile(techInfoVO.getLinkMobile());
            techInfo.setLinkName(techInfoVO.getLinkName());
            techInfo.setId(techInfoVO.getId());
            techInfo.setGenericSpec(techInfoVO.getGenericSpec());
            techInfo.setThumbs(techInfoVO.getThumbs());
            techInfo.setDescription(techInfoVO.getDescription());
            techInfo.setBrief(techInfoVO.getBrief());
            techInfo.setLinkData(techInfoVO.getLinkData());
            techInfo.setCid(techInfoVO.getCid());
            techInfoMapper.updateById(techInfo);
        }else{
            //新增技师表
            TechInfo techInfo = new TechInfo();
            techInfo.setLinkMobile(techInfoVO.getLinkMobile());
            techInfo.setLinkName(techInfoVO.getLinkName());
            techInfo.setId(techInfoVO.getId());
            techInfo.setGenericSpec(techInfoVO.getGenericSpec());
            techInfo.setThumbs(techInfoVO.getThumbs());
            techInfo.setDescription(techInfoVO.getDescription());
            techInfo.setBrief(techInfoVO.getBrief());
            techInfo.setLinkData(techInfoVO.getLinkData());
            techInfo.setCid(techInfoVO.getCid());
            techInfoMapper.insert(techInfo);
        }

        if(techInfoVO.getLinkData() != null){
            updateSpuRelation(techInfoVO.getId(), techInfoVO.getLinkData());
        }
    }

    //关联物品
    private void updateSpuRelation(String techId, String linkData){
        //将字符串转化给Json对象
        List<TechSpu> list = JSON.parseArray(linkData, TechSpu.class);
        //清空
        QueryWrapper<TechSpu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tech_id", techId);
        techSpuMapper.delete(queryWrapper);
        //查询是否存在
        for(TechSpu item: list){
            TechSpu techSpu = new TechSpu();
            techSpu.setTechId(techId);
            techSpu.setSpuId(item.getSpuId());
            techSpuMapper.insert(techSpu);
        }
    }
}
