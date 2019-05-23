package com.bk.sv.core;

import org.springframework.stereotype.Service;

/**
 * @author BK
 * @version V2.0
 * @description:
 * @team:
 * @date 2019/5/9 0:41
 */
@Service
public class UserPhoneServiceImpl implements  UserPhoneService{
    @Override
    public boolean isBlackTelephone(String telephone) {
        return "1888".equals(telephone);
    }
}
