package com.mygdx.pianist;

import com.badlogic.gdx.Gdx;



public class Nota {

    public static int largura = Gdx.graphics.getWidth()/4;
    public static int espessura = Gdx.graphics.getHeight()/5;

    private float y; // Posição da nota em y;
    private float x;  // posição em x da nota;
    private int fileira;   // variável que irá indicar a fileira a ser desenhada;
    private boolean check;       // variável para controlar se a nota foi tocada ou não
    private boolean mudaCor;    // variável que irá controlar a cor da nota;

    public Nota(float y, int fileira){ // o método construtor vai receber como parâmetro a altura que a tile está, e a fileira na tela
        this.y = y;
        this.fileira = fileira;       // variável que irá controlar a fileira que a nota será desenhada
        this.x = fileira*largura;
        this.check = false;            // variável para verificar se a nota já foi tocada ou não
        this.mudaCor = false;
    }

    public float getY(){
        return y;
    }
    public float getLargura(){
        return largura;
    }
    public float getEspessura(){
        return espessura;
    }
    public float getX(){
        return this.x;
    }
    public boolean getCheck(){
        return this.check;
    }
    public boolean getMudaCor(){
        return this.mudaCor;
    }
    public void setMudaCor(){
        this.mudaCor = true;
    }
    public boolean movimenta(float delta, float vel){
        y -= delta*vel;
        if((y+espessura) - (espessura/5) <0){     // caso a parte superior da nota tenha atingido uma posição abaixo da tela
            return true;
        }
        return false;
    }
    public int toque(float tx, float ty) {
        if (ty >= this.y && ty <= this.y + this.espessura) {    // caso o toque em y tenha sido dentro das bordas superior e inferior da nota
            if (tx>=this.x && tx <= this.x+largura) {           // caso o toque em y tenha sido entre as bordas laterais da nota
                this.check = true;
                return 1;
            } else {
                return -1;
            }
        }
        return 0;      // caso o toque não tenha sito efetuado na posição da nota
    }
}
