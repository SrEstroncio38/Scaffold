package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class SelectionFragment extends BaseFragment {

    public int currentShip;

    public SelectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selection, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.returnBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(currentShip);
            }
        });

        view.findViewById(R.id.shipSelect1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship1);
            }
        });
        view.findViewById(R.id.shipSelect2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship2);
            }
        });
        view.findViewById(R.id.shipSelect3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship3);
            }
        });
        view.findViewById(R.id.shipSelect4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship4);
            }
        });

        view.findViewById(R.id.shipSelect5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship5);
            }
        });
        view.findViewById(R.id.shipSelect6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship6);
            }
        });
        view.findViewById(R.id.shipSelect7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship7);
            }
        });
        view.findViewById(R.id.shipSelect8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).returnToMenu(R.drawable.ship8);
            }
        });
    }
}
