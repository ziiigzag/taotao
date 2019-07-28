package com.taotao.mapper;

import com.taotao.pojo.TbContentCat;
import com.taotao.pojo.TbContentCatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbContentCatMapper {
    int countByExample(TbContentCatExample example);

    int deleteByExample(TbContentCatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbContentCat record);

    int insertSelective(TbContentCat record);

    List<TbContentCat> selectByExample(TbContentCatExample example);

    TbContentCat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbContentCat record, @Param("example") TbContentCatExample example);

    int updateByExample(@Param("record") TbContentCat record, @Param("example") TbContentCatExample example);

    int updateByPrimaryKeySelective(TbContentCat record);

    int updateByPrimaryKey(TbContentCat record);
}