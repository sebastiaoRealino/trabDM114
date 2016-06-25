package br.inatel.lojaonline.fragments;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.EditTextPreference;
        import android.preference.PreferenceFragment;
        import android.util.Log;
        import android.widget.EditText;

        import br.inatel.lojaonline.R;
        import br.inatel.lojaonline.controller.SharedPreferenceController;

/**
 * Created by bccre on 22/06/2016.
 */
public class SettingsFragment extends PreferenceFragment {

    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.fragment_preferences);

//        getActivity().setTitle("Configurações");
//        EditText userLogin = (EditText) getActivity().findViewById(R.id.user_login);
//        EditText userPassword = (EditText) getActivity().findViewById(R.id.password);
//        String loginText = String.valueOf(userLogin.getText());
//        String passwordText = String.valueOf(userPassword.getText());
//        Log.i("edit text: ", loginText + passwordText);
//        SharedPreferenceController sharedPref = SharedPreferenceController.getSharedPreferencesController(getActivity());
//
//        sharedPref.putString("userLogin", loginText);
//        sharedPref.putString("userLogin", passwordText);

    }


}
