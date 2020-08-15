package com.mygdx.pianist;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreen implements Screen {

    private MainPianist jogo;
    private Texture fimJogo;
    private Music musicaFim;
    private int telaY;
    private int telaX;

    public GameOverScreen(MainPianist jogo){
        musicaFim = Gdx.audio.newMusic(Gdx.files.internal("lose.wav"));
        musicaFim.setVolume(0.6f);
        this.jogo = jogo;
        fimJogo = new Texture("fim.png");
    }

    @Override
    public void show() {
        musicaFim.play();
    }

    @Override
    public void render(float delta) {


        if(Gdx.input.justTouched()){
            musicaFim.stop();
            jogo.setScreen(new menuScreen(this.jogo));
        }

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jogo.gLauout.reset();
        jogo.gLauout.setText(jogo.placar, String.valueOf(jogo.jogador.getScore()));

        jogo.batch.begin();
        jogo.batch.draw(fimJogo, 0, 0,telaX, telaY);    // desenha o fundo de game over
        jogo.placar.draw(jogo.batch,String.valueOf(jogo.jogador.getScore()),telaX/2 - jogo.gLauout.width/2, telaY/2 - (int) (jogo.gLauout.height*0.2) * 2);
        jogo.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        telaX = width;
        telaY = height;
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
        fimJogo.dispose();
        musicaFim.dispose();
    }
}
