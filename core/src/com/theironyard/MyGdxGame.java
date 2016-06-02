package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion stand;
	float x,y, xv, yv;
	boolean canJump;

	static final float MAX_JUMP_VELOCITY = 2000;
	static final int WIDTH = 18;
	static final int HEIGHT = 26;

	static final float MAX_VELOCITY = 300;
	static final float DECELERATION = 0.95f; // try 0.99 for skating on ice effect

	static final int GRAVITY = -50;


	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture sheet = new Texture("Koalio.png");
		TextureRegion[][] tiles = TextureRegion.split(sheet, 18, 26);
		stand = tiles[0][0];
	}

	@Override
	public void render () {
		move();

		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(stand, x, y, WIDTH*3, HEIGHT*3);
		batch.end();
	}

	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && canJump) {
			yv = MAX_JUMP_VELOCITY;
			canJump = false;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = -MAX_VELOCITY;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = -MAX_VELOCITY;
		}

		yv += GRAVITY;

		float delta = Gdx.graphics.getDeltaTime();
		y+= yv * delta;
		x+= xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);

		if (y < 0) {
			y = 0;
			canJump = true;
		}
	}

	public float decelerate(float velocity) {
		velocity *= DECELERATION; // could also cast as float.
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

}
