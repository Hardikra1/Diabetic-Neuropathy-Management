package Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabetesmanagement.R;
import com.example.diabetesmanagement.YoutubeView;

public class AboutDiabetesFragment extends Fragment {
    private ImageButton Youtube;
    private ImageButton Diabetes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_aboutdiabetes,container,false);

        Youtube =(ImageButton)view.findViewById(R.id.youtube);
        Diabetes =(ImageButton)view.findViewById(R.id.diabetes);

        Youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Opening youtube view",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), YoutubeView.class);
                startActivity(intent);
            }
        });

        Diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Opening About Diabetes view",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Diabetes.Diabetes_intro.class);
                startActivity(intent);
            }
        });


        return view;

    }

}
