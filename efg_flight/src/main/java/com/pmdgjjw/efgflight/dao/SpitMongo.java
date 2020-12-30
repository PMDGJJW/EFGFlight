package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.Spit;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/2 9:57
 * @Description
 */

public interface SpitMongo extends MongoRepository<Spit,String>  {

     List<Spit> findAllByParentid (String parentid );

}
