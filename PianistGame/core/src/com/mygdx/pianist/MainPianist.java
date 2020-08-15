package com.mygdx.pianist;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

import java.util.Random;


public class MainPianist extends Game {

	public SpriteBatch batch;
	private float tempo;  // variável que vai armazenar o tempo do jogo;
	private float vel;
	private float velIni;
	private Random rand;
	private int inferior;  // index da fileira mais abaixo;
	public Jogador jogador;

	public BitmapFont placar;  // birmap para escrever a pontuação na tela;
	public GlyphLayout gLauout;   // Objeto que me permitirá trabalhar com as dimensões do meu texto
	public BitmapFont texto;
	private int telaY;
	private int telaX;

	@Override
	public void create() {

		telaY = Gdx.graphics.getHeight();
		telaX = Gdx.graphics.getWidth();
		this.jogador = new Jogador();  // objeto que irá armazenar os dados do jogador;
		this.rand = new Random();
		this.velIni = telaY;
		this.vel = 0;
		gLauout = new GlyphLayout();

        // configurando o gerador e o parâmetro da fonte do placar
		FreeTypeFontGenerator.setMaxTextureSize(2048);
		FreeTypeFontGenerator gerador = new FreeTypeFontGenerator(Gdx.files.internal("Ailerons.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametro.size = (int) (0.1f*telaY);
		parametro.color = Color.BLACK;
		placar = gerador.generateFont(parametro);

        // configurando o parametro da fonte do texto "score" ou "vida".
		parametro.size = (int) (0.05f*telaY);
		parametro.color = Color.RED;
		texto = gerador.generateFont(parametro);

		gerador.dispose();

		batch = new SpriteBatch();
		this.setScreen(new menuScreen(this));

	}

	@Override
	public void render() {
		super.render(); // chamando o renderer das classes das outras telas;
	}

	@Override
	public void dispose() {
	    this.texto.dispose();
	    this.placar.dispose();
		this.batch.dispose();
	}

	public void atualiza(float delta, Array<Nota>notas){
		tempo += delta;
		vel = velIni/4 + velIni*tempo/100;   // a velocidade do jogo aumentará gradativamente com o tempo do jogo.
		boolean saiu;

		for(int i = 0;i<notas.size;i++){
			saiu = notas.get(i).movimenta(delta,vel);
			if(saiu){
				if(notas.get(i).getCheck() == false){
					jogador.diminuiVida();
					this.inferior++;    // estou dizendo que a nota mais inferior será a próxima do vetor, já que esta chegou ao fim da tela e não foi tocada;
				}
				notas.removeIndex(i);     // remove a nota que saiu da tela
				i--;                      // volta uma unidade no contador do laço, para nenhuma nota ser pulada
				this.inferior--;          // decrementa a posição de inferior, que guada o índice da nota mais inferior na tela.
				adicionaNota(notas);      // adiciona uma nova nota;
			}
		}

	}
	public void adicionaNota(Array<Nota>notas){
		float maisAlta = notas.get(notas.size-1).getY()+ telaY/5; // vai adicionar uma nova nota sepre acima da nota mais alta na tela
		notas.add(new Nota(maisAlta,rand.nextInt(4)));                            // que no caso foi a úlma nota que tinha sido adicionada
	}
	public void setTempo(float tempo){
		this.tempo = tempo;
	}
	public void setVel(float velo){
		this.vel = vel;
	}
	public int getInf(){
		return this.inferior;
	}
	public void setInf(int inferior){
		this.inferior = inferior;
	}
	public void incrementaInf(){
		this.inferior++;
	}
	public int getTelaY(){
		return telaY;
	}

	public int getTelaX() {
		return telaX;
	}
	public void setTelaY(int y){
		telaY = y;
	}
	public void setTelaX(int x){
		telaX = x;
	}
}