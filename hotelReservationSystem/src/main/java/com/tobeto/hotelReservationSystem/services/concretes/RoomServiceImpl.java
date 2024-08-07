package com.tobeto.hotelReservationSystem.services.concretes;

import com.tobeto.hotelReservationSystem.core.utils.exceptions.types.BusinessException;
import com.tobeto.hotelReservationSystem.entities.Room;
import com.tobeto.hotelReservationSystem.entities.enums.RoomType;
import com.tobeto.hotelReservationSystem.repositories.RoomRepository;
import com.tobeto.hotelReservationSystem.services.abstracts.RoomService;
import com.tobeto.hotelReservationSystem.services.dtos.requests.room.AddRoomRequest;
import com.tobeto.hotelReservationSystem.services.dtos.requests.room.UpdateRoomRequest;
import com.tobeto.hotelReservationSystem.services.dtos.responses.room.*;
import com.tobeto.hotelReservationSystem.services.mappers.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public AddRoomResponse add(AddRoomRequest request) {
        Room room = RoomMapper.INSTANCE.addRoomRequestToRoom(request);
        room.setAvailable(true);

        Room saved = roomRepository.save(room);
        AddRoomResponse response = RoomMapper.INSTANCE.roomToAddRoomResponse(saved);
        return response;
    }


    @Override
    public UpdateRoomResponse update(UpdateRoomRequest request) {
        Room room = RoomMapper.INSTANCE.updateRoomRequestToRoom(request);
        Room updated = roomRepository.save(room);
        UpdateRoomResponse response = RoomMapper.INSTANCE.roomToUpdateRoomResponse(updated);
        return response;
    }

    @Override
    public void delete(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Room not found"));
        roomRepository.deleteById(id);
    }

    @Override
    public List<ListRoomResponse> getAll() {
        List<Room> rooms = roomRepository.findAll();


        List<ListRoomResponse> result = new ArrayList<>();
        for (Room room : rooms) {

            ListRoomResponse dto = RoomMapper.INSTANCE.roomsToGetAllRoomResponses(room);
           result.add(dto);

        }

        return result;
    }

    @Override
    public GetByIdRoomResponse getById(int id) {

        Room room = roomRepository.findById(id).orElseThrow();
        GetByIdRoomResponse response = RoomMapper.INSTANCE.roomToGetByIdRoomResponse(room);
        return response;
    }

    @Override
    public List<ListRoomResponse> findAvailableRooms(LocalDate startDate, LocalDate endDate, RoomType roomType) {
        // Belirtilen tarih aralığında ve belirli oda türünde müsait odaları bulmak için gereken sorguyu yapın
        List<Room> availableRooms = roomRepository.findAvailableRooms(startDate, endDate, roomType);

        // Bulunan odaları ListRoomResponse formatına dönüştürün
        List<ListRoomResponse> availableRoomResponses = new ArrayList<>();
        for (Room room : availableRooms) {
//            ListRoomResponse roomResponse = new ListRoomResponse();
//            roomResponse.setId(room.getId());
//          //  roomResponse.setRoomNumber(room.getRoomNumber());
//            roomResponse.setCapacity(room.getCapacity());
//            roomResponse.setPrice(room.getPrice());
//            roomResponse.setAvailable(room.getAvailable());
//            // Diğer gerekli bilgileri ekleyin
//            availableRoomResponses.add(roomResponse);
            ListRoomResponse dto = RoomMapper.INSTANCE.roomsToGetAllRoomResponses(room);
            availableRoomResponses.add(dto);

        }

        return availableRoomResponses;
    }

    @Override
    public Room findRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public void updateAvailable(Room room, boolean isAvailable) {
        room.setAvailable(isAvailable);
        System.out.println("Updating room ID: " + room.getId() + " to availability: " + room.getAvailable());
        roomRepository.save(room);

    }

    @Override
    public List<GetAllRoomByHotelIdResponse> getAllRoomByHotelId(int hotelId) {
       List<Room> rooms = roomRepository.findByHotelId(hotelId);
       List<GetAllRoomByHotelIdResponse> result = new ArrayList<>();
       for (Room room:rooms){
          GetAllRoomByHotelIdResponse dto = RoomMapper.INSTANCE.roomsToGetAllRoomByHotelIdResponse(room);
          result.add(dto);
       }
        return result;
    }


}
