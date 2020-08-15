package com.mygdx.pianist;

public class Jogador {

    private int vida;  // pontos de vida do jogador;
    private int score;  // Pontos de acerto do jogador;

    public Jogador(){
        this.vida = 10;        // construtor inicializando vida com 5, e score com 0 pontos;
        this.score = 0;
    }

    public int getVida(){
        return this.vida;
    }
    public int getScore(){
        return this.score;
    }
    public void diminuiVida(){
        this.vida -= 1;
    }
    public void setVida(){
        this.vida = 10;
    }
    public void aumentaScore(){
        this.score += 5;
    }
    public void setScore(){
        this.score = 0;
    }
}
