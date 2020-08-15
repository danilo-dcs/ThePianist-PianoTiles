package com.mygdx.pianist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;


public class GameScreen implements Screen{


    private ShapeRenderer shapeRenderer;
    private Array<Nota> notas;             // array de Nota, os objetos de Nota estarão contidos nesse array;
    public MainPianist jogo;   // variável do tipo MainPianist, para me permitir usar o SpriteBatch;
    private Musica musica;


    public GameScreen(MainPianist jogo){
        this.jogo = jogo;

        shapeRenderer = new ShapeRenderer(); // Shaperenderer que irá servir para desenhar as formas retangulares;
        shapeRenderer.setAutoShapeType(true);
        notas = new Array <Nota>();
        musica = new Musica("musica");

        notas.clear();
        jogo.setTempo(0);
        jogo.setVel(0);
        jogo.setInf(0);
        jogo.jogador.setScore();
        jogo.jogador.setVida();
        musica.setAtual();
        musica.setFim();


        notas.add(new Nota(jogo.getTelaY(),0));  // estou adicionando as notas bases do jogo
        jogo.adicionaNota(notas);
        jogo.adicionaNota(notas);
        jogo.adicionaNota(notas);
        jogo.adicionaNota(notas);
        jogo.adicionaNota(notas);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        if(jogo.jogador.getVida()>0 && !musica.getFim()){     // o jogó irá funcionar enquando a vida do jogador for maior que zero.

            if(Gdx.input.justTouched()) {          // pegando o toque no touch;
                int tx = Gdx.input.getX();
                int ty = jogo.getTelaY() - Gdx.input.getY(); //a posição em y do toque será dada pela altura da tela - a coordenada do toque, que é invertida
                                                                        // em relação às coordenadas de desenho das imagens.
                for(int i=0;i<notas.size;i++){
                    int acerto = notas.get(i).toque(tx,ty);

                    if(acerto!=0){
                        if (acerto == 1 && i == jogo.getInf()){  // se ele tiver acertado a nota, e ela for a mais inferior na tela
                            jogo.jogador.aumentaScore();        // incrementando na pontuação
                            jogo.incrementaInf();              // setando o próximo índice do array, ou seja a próxima nota, como a mais baixa que deverá ser tocada
                            notas.get(i).setMudaCor();    // se a nota tiver sido tocada, irá setar uma variável Muda cor para true, e a cor da nota será alterada
                            musica.tocar();
                        }
                        else{
                            jogo.jogador.diminuiVida();       // caso o toque tenha sido em uma posição qualquer na tela que não seja na nota perderá pontos.
                        }
                        break;

                    }
                }
            }

            jogo.atualiza(delta,notas);     // função que atualiza o tempo e a velocidade do jogo, além de fazer a movimentação
                                                                                // e verificar se a nota saiu da tela
            Gdx.gl.glClearColor(1,1,1,0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            shapeRenderer.begin();          // iniciando o shaperenderer para desenhar as formas dos retângulos
            for (int i=0;i<notas.size;i++) {
                shapeRenderer.set(ShapeRenderer.ShapeType.Filled); // nessa e nas próximas 2 linhas será desenhado o contorno da tecla;
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.rect(notas.get(i).getX(), notas.get(i).getY(), notas.get(i).getLargura() + 3, notas.get(i).getEspessura() + 3);

                shapeRenderer.set(ShapeRenderer.ShapeType.Filled); // nessa e nas próximas 2 linhas será desenhado o retângulo que será a nota;

                if(notas.get(i).getCheck()&& notas.get(i).getMudaCor()){  // se a nota tiver sido tocada e o muda cor for verdadeiro, irá trocar a cor da nota
                    shapeRenderer.setColor(Color.ROYAL);
                }else{
                    shapeRenderer.setColor(Color.CYAN);       // se a nota não tiver sido tocada não irá trocar a cor da nota
                }
                shapeRenderer.rect(notas.get(i).getX() + 3, notas.get(i).getY() + 3, notas.get(i).getLargura() - 3, notas.get(i).getEspessura() - 3);
            }
            shapeRenderer.end();

            jogo.gLauout.reset();
;
            jogo.batch.begin();
            jogo.gLauout.setText(jogo.texto, "Score");
            jogo.texto.draw(jogo.batch,"Score",jogo.getTelaX()- jogo.gLauout.width,jogo.getTelaY() - (int)(0.01*jogo.getTelaY()));
            jogo.texto.draw(jogo.batch,"Vida",0,jogo.getTelaY() - (int)(0.01*jogo.getTelaY()));

            jogo.gLauout.setText(jogo.placar, String.valueOf(jogo.jogador.getScore()));
            jogo.placar.draw(jogo.batch,String.valueOf(jogo.jogador.getScore()),jogo.getTelaX() - jogo.gLauout.width,jogo.getTelaY()- (int)(0.1*jogo.getTelaY()));
            jogo.placar.draw(jogo.batch,String.valueOf(jogo.jogador.getVida()),0,jogo.getTelaY()- (int)(0.1*jogo.getTelaY()));
            jogo.batch.end();



        }else if (musica.getFim()){
            jogo.setScreen(new FimScreen(this.jogo));
        }else{
            jogo.setScreen(new GameOverScreen(this.jogo));     // caso a vida tenha atingido 0 pontos, irá trocar para tela de fim de jogo.
        }

    }

    @Override
    public void resize(int width, int height) {
        jogo.setTelaY(height);
        jogo.setTelaX(width);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        musica.dispose();
    }
}
