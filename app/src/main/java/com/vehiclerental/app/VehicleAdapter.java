package com.vehiclerental.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.vehiclerental.database.VehicleEntity;
import java.util.List;

public class VehicleAdapter extends ArrayAdapter<VehicleEntity> {

    public VehicleAdapter(@NonNull Context context, @NonNull List<VehicleEntity> vehicles) {
        super(context, 0, vehicles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_vehicle, parent, false);
        }

        VehicleEntity vehicle = getItem(position);

        ImageView image = convertView.findViewById(R.id.itemVehicleImage);
        TextView model = convertView.findViewById(R.id.itemVehicleModel);
        TextView type = convertView.findViewById(R.id.itemVehicleType);
        TextView price = convertView.findViewById(R.id.itemVehiclePrice);
        TextView status = convertView.findViewById(R.id.itemVehicleStatus);

        if (vehicle != null) {
            model.setText(vehicle.modelName);
            type.setText(vehicle.vehicleType);
            price.setText("$" + vehicle.rentalRate + "/day");
            status.setText(vehicle.available ? "Available" : "Booked");

            if (vehicle.imageUrl != null && !vehicle.imageUrl.isEmpty()) {
                Glide.with(getContext())
                        .load(vehicle.imageUrl)
                        .placeholder(android.R.drawable.ic_menu_camera)
                        .into(image);
            } else {
                image.setImageResource(android.R.drawable.ic_menu_camera);
            }
        }

        return convertView;
    }
}
