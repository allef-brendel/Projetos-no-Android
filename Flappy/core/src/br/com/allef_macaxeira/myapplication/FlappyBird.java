package br.com.allef_macaxeira.myapplication;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.lang.reflect.Field;
import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoAlto;
	private Texture canoBaixo;
	private Texture gameOver;

	private BitmapFont fonte;
	private BitmapFont mensagem;
	private BitmapFont maxPontuacao;
	private Circle passaroCirculo;
	private Rectangle retanguloCanoTopo;
	private Rectangle retanguloCanoBaixo;
	private ShapeRenderer shape;

	//Atributos de Configuração
	private float larguraDispositivo;
	private float alturaDispositivo;
	private Random numeroRandomico;
	private int estadoDoJogo = 0; //0 jogo parado - 1 jogo iniciado - 2 game over
	private int pontuacao = 0;
	private int bestPontuacao = 0;
	private Boolean marcouPonto = false;

	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandom;

	//Atributos da camera
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 600;
    private final float VIRTUAL_HEIGHT = 800;


	@Override
	public void create() {

		fonte = new BitmapFont();
		mensagem = new BitmapFont();
		maxPontuacao = new BitmapFont();

		batch = new SpriteBatch();

		passaroCirculo = new Circle();
		retanguloCanoBaixo = new Rectangle();
		retanguloCanoTopo = new Rectangle();

		//shape = new ShapeRenderer();

		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(2);

		mensagem.setColor(Color.WHITE);
		mensagem.getData().setScale(2);

		maxPontuacao.setColor(Color.WHITE);
		maxPontuacao.getData().setScale(2);

		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		gameOver = new Texture("arnaldo.png");
		canoAlto = new Texture("cano_topo.png");
		canoBaixo = new Texture("cano_baixo.png");

		fundo = new Texture("fundo.png");

		numeroRandomico = new Random();

		//Configuração da camera
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH/2,VIRTUAL_HEIGHT/2,0);
        viewport = new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);

		larguraDispositivo = VIRTUAL_WIDTH;
		alturaDispositivo = VIRTUAL_HEIGHT;
		posicaoInicialVertical = alturaDispositivo / 2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 540;
	}

	@Override
	public void render() {

		//Movimento do passaro
		variacao += 0.1;
		if (variacao > 2) variacao = 0;

		if(estadoDoJogo == 0) {
			if (Gdx.input.justTouched()) {
				estadoDoJogo =1;
			}
		}else {
			velocidadeQueda += 0.2;
			if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
				posicaoInicialVertical -= velocidadeQueda;
			}

				if (estadoDoJogo == 1) {
					deltaTime = Gdx.graphics.getDeltaTime() * 300;
					posicaoMovimentoCanoHorizontal -= deltaTime;

					if (Gdx.input.justTouched()) {
						velocidadeQueda = -6;
					}

					//Verifica se o cano saiu da tela
					if (posicaoMovimentoCanoHorizontal < -100) {
						posicaoMovimentoCanoHorizontal = larguraDispositivo - 50;
						alturaEntreCanosRandom = numeroRandomico.nextInt(400) - 350;
						marcouPonto = false;
					}
						//Verifica pontuacao
						if (posicaoMovimentoCanoHorizontal < 1) {
							if (!marcouPonto) {
								pontuacao++;
								marcouPonto = true;
								if(pontuacao > bestPontuacao){
									 bestPontuacao = pontuacao;
								}
							}
						}

					}else{//tela Game over
					if (Gdx.input.justTouched()) {
						estadoDoJogo = 0;
						pontuacao = 0;
						velocidadeQueda = 0;
						posicaoInicialVertical = alturaDispositivo /2;
						posicaoMovimentoCanoHorizontal = larguraDispositivo;
					}
			}
		}

            //informações da camera
            batch.setProjectionMatrix(camera.combined);

			batch.begin();

			batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);

			batch.draw(canoAlto, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandom, larguraDispositivo / 5, alturaDispositivo);
			batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - 400 - espacoEntreCanos / 2 + alturaEntreCanosRandom, larguraDispositivo / 5, alturaDispositivo);

			batch.draw(passaros[(int) variacao], 34, posicaoInicialVertical, 40, 30);

			fonte.draw(batch,String.valueOf(pontuacao),larguraDispositivo / 2,alturaDispositivo-50);
			maxPontuacao.draw(batch,String.valueOf(bestPontuacao),larguraDispositivo / 2,50);

			if(estadoDoJogo == 2){
				batch.draw(gameOver,larguraDispositivo/2-200,alturaDispositivo/2,400,300);
				mensagem.draw(batch,"VOCÊ PERDEU!!!",larguraDispositivo/3 - 15,alturaDispositivo/2);
			}

			batch.end();

			passaroCirculo.set(30 + passaros[0].getWidth()/3,posicaoInicialVertical + passaros[0].getHeight()/3,20);
			retanguloCanoBaixo.set(posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 - 400 - espacoEntreCanos / 2 + alturaEntreCanosRandom,canoBaixo.getWidth(),alturaDispositivo);
			retanguloCanoTopo.set(posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandom,canoBaixo.getWidth(),alturaDispositivo);

			//Desenhar Formas
			/*shape.begin(ShapeRenderer.ShapeType.Filled);

			shape.circle(passaroCirculo.x,passaroCirculo.y,passaroCirculo.radius);
			shape.rect(retanguloCanoBaixo.x,retanguloCanoBaixo.y,retanguloCanoBaixo.width,retanguloCanoBaixo.height);
			shape.rect(retanguloCanoTopo.x,retanguloCanoTopo.y,retanguloCanoTopo.width,retanguloCanoTopo.height);

			shape.setColor(Color.WHITE);
			shape.end();*/

			//Teste de colisão
			if(Intersector.overlaps(passaroCirculo,retanguloCanoBaixo) || Intersector.overlaps(passaroCirculo,retanguloCanoTopo)
					 || posicaoInicialVertical <=0 || posicaoInicialVertical >= alturaDispositivo){
				estadoDoJogo = 2;
			}

	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
}


