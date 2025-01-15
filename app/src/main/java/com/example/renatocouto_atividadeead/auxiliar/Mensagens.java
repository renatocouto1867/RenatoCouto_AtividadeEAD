package com.example.renatocouto_atividadeead.auxiliar;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

public class Mensagens {

    public static void showSnackbarPersonalizado(View view, String mensagem, int cor, int tempo) {

        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG);

        switch (cor) {
            case 1:
                snackbar.setBackgroundTint(view.getResources().getColor(android.R.color.holo_red_dark));
                break;
            case 2:
                snackbar.setBackgroundTint(view.getResources().getColor(android.R.color.holo_green_dark));
                break;
            case 3:
                snackbar.setBackgroundTint(view.getResources().getColor(android.R.color.holo_blue_dark));
                break;
            case 4:
                snackbar.setBackgroundTint(view.getResources().getColor(android.R.color.holo_orange_dark));
                break;
            default:
                snackbar.setBackgroundTint(view.getResources().getColor(android.R.color.black));
                break;
        }

        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params;

        if (snackbarView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            // Obtém as dimensões para layouts baseados em FrameLayout
            params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        } else {
            // Fallback para outros tipos de layout
            params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        params.gravity = Gravity.CENTER;
        snackbarView.setLayoutParams(params);

        snackbar.setDuration(tempo);

        snackbar.show();
    }


}