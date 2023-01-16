package com.mf.payment.convert;


import com.mf.common.payment.PaymentVO;
import com.mf.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentConvert {

    PaymentConvert INSTANCE = Mappers.getMapper(PaymentConvert.class);

    // VO -> Entity
    Payment convertToEntity (PaymentVO vo);

    // Entity -> Vo
    PaymentVO convertToVO(Payment payment);

}
