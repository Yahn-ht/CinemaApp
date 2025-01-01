package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.model.Snack;
import com.example.cinemaapp.data.repository.SnackRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SnackViewModel extends ViewModel {
    private final SnackRepository snackRepository;
    private final MutableLiveData<List<Snack>> snacks = new MutableLiveData<>();
    private final MutableLiveData<List<Snack>> filteredSnacks = new MutableLiveData<>();
    private final Map<Integer, Integer> selectedSnacks = new HashMap<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

    public SnackViewModel(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
        fetchSnacks();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    // Récupère la liste complète des snacks depuis le repository
    private void fetchSnacks() {
        isLoadingLiveData.setValue(true);
        snackRepository.getSnacks().observeForever(
                snacks -> {
                    this.snacks.setValue(snacks);
                    filteredSnacks.setValue(snacks);
                    isLoadingLiveData.setValue(false);
                }
        );
    }

    // Retourne la liste des snacks complète
    public LiveData<List<Snack>> getSnacks() {
        return snacks;
    }

    // Retourne la liste des snacks filtrés
    public LiveData<List<Snack>> getFilteredSnacks() {
        return filteredSnacks;
    }

    // Met à jour les snacks sélectionnés
    public void updateSelectedSnack(int snackId, int quantity) {
        if (quantity > 0) {
            selectedSnacks.put(snackId, quantity);
        } else {
            selectedSnacks.remove(snackId);
        }
    }

    // Applique un filtre sur la catégorie, mais conserve la liste d'origine intacte
    public void filterSnacksByCategory(String category) {
        List<Snack> filteredSnack = new ArrayList<>();
        for (Snack snack : Objects.requireNonNull(snacks.getValue())) {
            if (snack.getCategory().getName().equals(category)) {
                filteredSnack.add(snack);
            }
        }
        filteredSnacks.setValue(filteredSnack);
    }

    // Réinitialise les snacks filtrés pour afficher tous les snacks
    public void resetFilter() {
        filteredSnacks.setValue(snacks.getValue()); // Restaure la liste complète des snacks
    }

    // Retourne les snacks sélectionnés avec leurs quantités
    public Map<Integer, Integer> getSelectedSnacks() {
        return selectedSnacks;
    }
}
