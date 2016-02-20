package com.jennifer.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jennifer.R;
import com.jennifer.model.UsuarioApuestaPrivada;

import java.util.List;

/**
 * Created by echessa on 7/24/15.
 */
public class UsuariosApuestasPrivadaAdapter extends RecyclerView.Adapter<UsuariosApuestasPrivadaAdapter.ViewHolder> {

    private List<UsuarioApuestaPrivada> items;

    public UsuariosApuestasPrivadaAdapter(List<UsuarioApuestaPrivada> items) {
        this.items = items;
    }

    private PopupWindow popUpWindow;
    private LayoutInflater layoutInflater;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_apuestas_publicas, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        UsuarioApuestaPrivada item = items.get(i);

        // Data Set
        viewHolder.name.setText(item.getName());
        viewHolder.description.setText(item.getDescription());

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_info_apuestas, null);
                popUpWindow = new PopupWindow(container, 1000, 750, true);
                popUpWindow.showAtLocation(view, Gravity.NO_GRAVITY, 500, 500);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popUpWindow.dismiss();
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cv;
        private final TextView name;
        private final TextView description;

        ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.cv_apublicas);
            name = (TextView) v.findViewById(R.id.nombre_apuesta);
            description = (TextView) v.findViewById(R.id.descripcion_apuesta);
        }
    }
}
