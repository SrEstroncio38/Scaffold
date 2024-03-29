package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class ResultFragment extends BaseFragment {

    public int lifes;
    public int score;
    public int enemies;
    public int currentShip;

    public ResultFragment() {
        lifes = 0;
        score = 0;
        enemies = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.scoreValue)).setText(Integer.toString(score));
        ((TextView) view.findViewById(R.id.lifesValue)).setText(Integer.toString(lifes));
        ((TextView) view.findViewById(R.id.enemiesValue)).setText(Integer.toString(enemies));

        view.findViewById(R.id.replayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).startGame(currentShip);
            }
        });

        view.findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(currentShip);
            }
        });
    }
}
