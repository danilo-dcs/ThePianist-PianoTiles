package com.mygdx.pianist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class FimScreen implements Screen {
    private MainPianist jogo;
    private Texture telaFim;
    private Music aplauso;

    public FimScreen(MainPianist jogo){
        aplauso = Gdx.audio.newMusic(Gdx.files.internal("applause.wav"));
        this.jogo = jogo;
        telaFim = new Texture("parabens.png");
        aplauso.setVolume(0.6f);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        aplauso.play();
        jogo.batch.begin();
        jogo.batch.draw(telaFim,0,0,jogo.getTelaX(),jogo.getTelaY());
        jogo.batch.end();

        if(Gdx.input.justTouched()){
            aplauso.stop();
            jogo.setScreen(new menuScreen(this.jogo));
        }


    }

    @Override
    public void resize(int width, int height) {
        jogo.setTelaX(width);
        jogo.setTelaY(height);
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
        telaFim.dispose();
        aplauso.dispose();
    }
}
