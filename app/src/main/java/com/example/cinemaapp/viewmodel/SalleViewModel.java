package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.salles.SalleResponse;
import com.example.cinemaapp.data.model.Place;
import com.example.cinemaapp.data.repository.SalleRepository;

import java.util.List;

public class SalleViewModel extends ViewModel {
    private final SalleRepository roomRepository;

    public SalleViewModel() {
        roomRepository = new SalleRepository();
    }

    public LiveData<SalleResponse> getRoomById(int roomId) {
        LiveData<SalleResponse> roomLiveData = roomRepository.getRoomById(roomId);
        roomLiveData.observeForever(room -> {
            if (room != null && room.getPlaces() != null) {
                initializeSeats(room.getPlaces());
            }
        });
        return roomRepository.getRoomById(roomId);
    }

    private void initializeSeats(List<Place> seats) {
        for (Place seat : seats) {
            seat.setSelected(false); // Initialiser manuellement à false
        }
    }
}
