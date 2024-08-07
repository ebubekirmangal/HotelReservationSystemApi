package com.tobeto.hotelReservationSystem.services.mappers;

import com.tobeto.hotelReservationSystem.entities.addresses.District;
import com.tobeto.hotelReservationSystem.services.dtos.responses.district.GetAllByCityIdResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DistrictMapper {

    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    GetAllByCityIdResponse districtToGetAllByCityIdResponse(District district);
}
