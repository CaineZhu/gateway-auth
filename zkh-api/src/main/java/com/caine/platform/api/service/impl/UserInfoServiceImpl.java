package com.caine.platform.api.service.impl;

import com.caine.platform.api.mapper.UserInfoMapper;
import com.caine.platform.api.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caine.platform.common.pojo.UserInfo;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CaineZhu
 * @since 2019-09-09
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
