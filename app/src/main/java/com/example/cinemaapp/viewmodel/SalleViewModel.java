package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.salles.SalleResponse;
import com.example.cinemaapp.data.model.Place;
import com.example.cinemaapp.data.repository.SalleRepository;

import java.util.List;

public class SalleViewModel extends ViewModel {
    private final SalleRepository roomRepository;
    private final MutableLiveData<Boolean> isLoadLiveData = new MutableLiveData<>();

    public SalleViewModel(SalleRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadLiveData;
    }

    public LiveData<SalleResponse> getRoomById(int roomId) {
        isLoadLiveData.setValue(true);
        LiveData<SalleResponse> roomLiveData = roomRepository.getRoomById(roomId);
        roomLiveData.observeForever(room -> {
            isLoadLiveData.setValue(false);
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
