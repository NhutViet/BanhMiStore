package com.viethcn.duanandroid.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.viethcn.duanandroid.Models.MainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainModelRepository extends ViewModel {

    private final MutableLiveData<List<MainModel>> selectedItems = new MutableLiveData<>(new ArrayList<>());

    public void addItem(MainModel item) {
        List<MainModel> currentList = selectedItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        boolean exists = false;
        for (MainModel model : currentList) {
            if (model.getName().equals(item.getName())) {
                model.setQuantity(model.getQuantity() + 1);
                exists = true;
                break;
            }
        }

        if (!exists) {
            item.setQuantity(1);
            currentList.add(item);
        }

        selectedItems.setValue(currentList);
    }

    public int calcTotal(){
        int total = 0;
        for (MainModel model : Objects.requireNonNull(selectedItems.getValue())) {
            total += model.getQuantity() * Integer.parseInt(model.getPrice());
        }
        return total;
    }


    public LiveData<List<MainModel>> getSelectedItems() {
        return selectedItems;
    }

    public void updateQuanTityItem(MainModel item) {
        List<MainModel> currentList = selectedItems.getValue();
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                if (currentList.get(i).getName().equals(item.getName())) {
                    currentList.set(i, item);
                    break;
                }
            }
            selectedItems.setValue(currentList);
        }
    }

    public void removeItem(MainModel item) {
        List<MainModel> currentList = selectedItems.getValue();
        if (currentList != null) {
            currentList.remove(item);
            selectedItems.setValue(currentList);
        }
    }

    public void clearAll(){
        selectedItems.setValue(new ArrayList<>());
    }
}

