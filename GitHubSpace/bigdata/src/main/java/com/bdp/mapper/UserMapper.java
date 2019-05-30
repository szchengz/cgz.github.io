package com.bdp.mapper;

import com.bdp.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by cgz on 2019-05-30 23:24
 * 描述：
 */
@Repository
public interface UserMapper {

    User Sel(int id);
}
