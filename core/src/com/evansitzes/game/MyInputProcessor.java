package com.evansitzes.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by evan on 11/12/16.
 */
public class MyInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown (int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                // handle left push
                break;
            case Input.Keys.RIGHT:
                // handle right push
                break;
            case Input.Keys.DOWN:
                System.out.println("Seriously down??");
                return true;
            // handle more keys here if you need

            default:
                // unused key pushed
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
