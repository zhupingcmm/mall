package com.mf.order.convert;

import com.mf.order.controller.vo.OrderVO;
import com.mf.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderConvert {

    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    // VO -> Entity

    Order convertToEntity(OrderVO vo);

    // Entity -> VO

    OrderVO convertToVo(Order order);
}
