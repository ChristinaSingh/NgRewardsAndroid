package main.com.ngrewards.marchant.rent;

import main.com.ngrewards.Models.PropertyListModel;

public interface onPropertyListener {
    void onProperty(int position, String Type, PropertyListModel.Datum data);

}
