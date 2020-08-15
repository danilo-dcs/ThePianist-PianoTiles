package com.mygdx.pianist;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;

public class menuScreen implements Screen{
    private Texture backGround; // textura do fundo;
    private Texture playBtn; //textura do botão;
    private MainPianist jogo; // objeto da classe principal que contém o batch
    private Music menuMusic;

    private float x_playBtn;  // posição do otão em x;
    private float y_playBtn;  // posição do botão em y;
    private float btnWidth;   // largura do botão;
    private float btnHeight;  // altura do botão;


    public menuScreen(MainPianist jogo){
        this.jogo = jogo;
        backGround = new Texture("menuScreen.png");
        playBtn = new Texture("playButton2.png");
        btnWidth = playBtn.getWidth();
        btnHeight = playBtn.getHeight();
        x_playBtn = (jogo.getTelaX()/2) - (btnWidth/4);
        y_playBtn = (jogo.getTelaY()/2) - (btnHeight/4);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Mozart.wav"));
        menuMusic.setVolume(1f);
    }


    @Override
    public void show() {
        menuMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = jogo.getTelaY() - Gdx.input.getY();

            if (x > x_playBtn && x < (x_playBtn + btnWidth)                // checando se o toque foi dentro do botão
                    && y > y_playBtn && y < (y_playBtn + btnHeight)) {
                menuMusic.stop();
                jogo.setScreen(new GameScreen(jogo));
        }

        }else {
            jogo.batch.begin();
            jogo.batch.draw(backGround, 0, 0, jogo.getTelaX(), jogo.getTelaY());               // desenha o fundo do menu
            jogo.batch.draw(playBtn, x_playBtn, y_playBtn, (jogo.getTelaX()/ 6), (jogo.getTelaY() / 8));   // desenha o botão de play
            jogo.batch.end();
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
        backGround.dispose();
        playBtn.dispose();
        menuMusic.dispose();
    }
}
