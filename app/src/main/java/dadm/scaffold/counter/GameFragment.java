package dadm.scaffold.counter;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.engine.Lifes;
import dadm.scaffold.engine.Score;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.mainmenu.ParallaxedBackground;
import dadm.scaffold.space.GameController;
import dadm.scaffold.space.SpaceShipPlayer;


public class GameFragment extends BaseFragment {

    private GameEngine theGameEngine;
    public int currentShip;
    private View pauseMenu;

    /* Dejo esto por que a lo mejor cuando los inicializas en la view
    se puede inicializar tambien aqui y ya tienes la puntuacion y las vidas
    en el jodido fragmento de mierda */

    /*
    public Lifes life;
    public Score score;
    */

    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pauseMenu = (View) view.findViewById(R.id.pauseMenu);
        pauseMenu.setVisibility(View.INVISIBLE);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado

                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                Lifes life = new Lifes(theGameEngine);
                Score score = new Score(theGameEngine);
                theGameEngine.setSoundManager(getScaffoldActivity().getSoundManager());
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                theGameEngine.addGameObject(new ParallaxedBackground(theGameEngine, R.drawable.gamebg2));
                SpaceShipPlayer ss = new SpaceShipPlayer(theGameEngine, currentShip , life, score, getScaffoldActivity());
                theGameEngine.addGameObject(ss);
                theGameEngine.addGameObject(life);
                theGameEngine.addGameObject(score);
                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.addGameObject(new GameController(theGameEngine, ss));
                theGameEngine.startGame();
            }
        });

        ImageView imgPause = (ImageView) getView().findViewById(R.id.btn_play_pause);
        imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseGameAndShowPauseDialog();
            }
        });

        ImageView continueBtn = (ImageView) getView().findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.resumeGame();
                pauseMenu.setVisibility(View.INVISIBLE);
            }
        });

        ImageView exitBtn = (ImageView) getView().findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.stopGame();
                ((ScaffoldActivity)getActivity()).returnToMenu(currentShip);
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();
        pauseMenu.setVisibility(View.VISIBLE);
    }

    private void playOrPause() {
        ImageView button = (ImageView) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            //button.setText(R.string.pause);
        }
        else {
            theGameEngine.pauseGame();
            //button.setText(R.string.resume);
        }
    }

}
