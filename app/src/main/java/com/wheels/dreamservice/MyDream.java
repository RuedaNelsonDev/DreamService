package com.wheels.dreamservice;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.graphics.Point;
import android.service.dreams.DreamService;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import java.util.Random;

public class MyDream extends DreamService implements View.OnClickListener {

    private final int FILAS_COLUM = 5;
    private final int NUM_IMAGENES = FILAS_COLUM * FILAS_COLUM;
    private Button btnDetener;
    private ImageView[] ivImagenes;
    private AnimatorSet[] imageSets;
    private int randomPosicion;


    @Override
    public void onDreamingStarted() {
//Comenzar dayDream
        super.onDreamingStarted();
        for (int r = 0; r < NUM_IMAGENES; r++) {
            if (r != randomPosicion)
                imageSets[r].start();
        }
    }

    @Override
    public void onDreamingStopped() {
//Detener dayDream
        for (int r = 0; r < NUM_IMAGENES; r++) {
            if (r != randomPosicion)
                imageSets[r].cancel();
        }
    }

    @Override
    public void onAttachedToWindow() {
//Configurar daydream

        super.onAttachedToWindow();

        setInteractive(true);
        setFullscreen(true);

        Random rand = new Random();
        randomPosicion = rand.nextInt(NUM_IMAGENES);

        GridLayout ddLayout = new GridLayout(this);
        ddLayout.setColumnCount(FILAS_COLUM);
        ddLayout.setRowCount(FILAS_COLUM);

        imageSets = new AnimatorSet[NUM_IMAGENES];
        ivImagenes = new ImageView[NUM_IMAGENES];

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        int robotWidth = screenSize.x / FILAS_COLUM;
        int robotHeight = screenSize.y / FILAS_COLUM;

        for (int r = 0; r < NUM_IMAGENES; r++) {
//Agregar la cuadricula

            GridLayout.LayoutParams ddP = new GridLayout.LayoutParams();
            ddP.width = robotWidth;
            ddP.height = robotHeight;


            if (r == randomPosicion) {
//Boton de detener

                btnDetener = new Button(this);
                btnDetener.setText("stop");
                btnDetener.setBackgroundColor(Color.BLACK);
                btnDetener.setTextColor(Color.RED);
                btnDetener.setTextSize(22);
                btnDetener.setOnClickListener(this);
                btnDetener.setLayoutParams(ddP);
                ddLayout.addView(btnDetener);
            } else {

//Animacion de la imagen

                ivImagenes[r] = new ImageView(this);
                ivImagenes[r].setImageResource(R.drawable.wposs_logo);
                ddLayout.addView(ivImagenes[r], ddP);

                imageSets[r] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.android_spin);


                imageSets[r].setTarget(ivImagenes[r]);
                ivImagenes[r].setOnClickListener(this);
            }

        }
        setContentView(ddLayout);
    }

    @Override
    public void onDetachedFromWindow() {
//Ordenar las imagenes
        for (int r = 0; r < NUM_IMAGENES; r++) {
            if (r != randomPosicion)
                ivImagenes[r].setOnClickListener(null);
        }
        super.onDetachedFromWindow();
    }

    public void onClick(View v) {
//Manejar los clic
        if (v instanceof Button && (Button) v == btnDetener) {
//Boton parar
            this.finish();
        } else {
//imagen

            for (int r = 0; r < NUM_IMAGENES; r++) {
//Verificar la matriz
                if (r != randomPosicion) {
//Verificar el iamge view
                    if ((ImageView) v == ivImagenes[r]) {
//is the current view
                        if (imageSets[r].isStarted()) imageSets[r].cancel();
                        else imageSets[r].start();
                    }
                }
                break;

            }

        }
    }
}