package br.inatel.lojaonline;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import br.inatel.lojaonline.controller.GCMRegister;
import br.inatel.lojaonline.controller.SharedPreferenceController;
import br.inatel.lojaonline.fragments.GCMFragment;
import br.inatel.lojaonline.fragments.OrdersFragment;
import br.inatel.lojaonline.fragments.ProductListFragment;
import br.inatel.lojaonline.fragments.ProductRegisterFragment;
import br.inatel.lojaonline.fragments.SettingsFragment;
import br.inatel.lojaonline.interfaces.GCMPutEvents;
import br.inatel.lojaonline.interfaces.GCMRegisterEvents;
import br.inatel.lojaonline.interfaces.LoginInterface;
import br.inatel.lojaonline.models.ProductInfo;
import br.inatel.lojaonline.tasks.GCMRegisterTask;
import br.inatel.lojaonline.tasks.LoginTask;
import br.inatel.lojaonline.webservice.WebServiceClient;
import br.inatel.lojaonline.webservice.WebServiceResponse;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginInterface, GCMRegisterEvents, GCMPutEvents {

    private ProductInfo productInfo;
    private GCMRegister gcmRegister;
    private String registrationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadSharedPreference();
        if (gcmRegister == null)
            gcmRegister = new GCMRegister(this, this);

        if (!gcmRegister.isRegistrationExpired()) {
            registrationID = gcmRegister.getCurrentRegistrationId();
        }

        Intent intent = this.getIntent();
        if (intent.hasExtra("productInfo")) {
            productInfo = (ProductInfo) intent.
                    getSerializableExtra("productInfo");

        } else if (savedInstanceState == null) {
            displayFragment(R.id.nav_config);
        }
        setGCMRegister();

    }
    private void setGCMRegister(){
        registrationID = gcmRegister.getRegistrationId("");
        if ((registrationID == null) ||
                (registrationID.length() == 0)) {
            Toast.makeText(this,
                    "Dispositivo ainda não registrado na nuvem. " +
                            "Tentando...",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,
                    "Dispositivo já registrado na nuvem.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSharedPreference() {
        SharedPreferences sharedSettings =
                PreferenceManager.getDefaultSharedPreferences(this);


        if(!sharedSettings.contains("pref_user_login")){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login de usuário");
            builder.setMessage("Login de usuário ainda não foi definido. Necessário configurar login de usuário")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            displayFragment(R.id.action_settings);
                        }
                    });
                    /*.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do nothing
                        }
                    });*/
            builder.create();
            builder.show();
        } else {
            //TODO TENTA FAZER LOGIN
            WebServiceResponse webServiceResponse;
            LoginTask orderTasks = new LoginTask(this,this);
            orderTasks.execute();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.hasExtra("productInfo")) {
            productInfo = (ProductInfo) intent.
                    getSerializableExtra("productInfo");
            if (productInfo != null) {
                displayFragment(R.id.nav_config);
            }
        }
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            displayFragment(item.getItemId());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayFragment(item.getItemId());
        return true;
    }

    private void displayFragment (int fragmentId) {
        Class fragmentClass;
        Fragment fragment = null;

        int backStackEntryCount;
        backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        for (int j = 0; j < backStackEntryCount; j++) {
            getFragmentManager().popBackStack();
        }

        try {
            switch (fragmentId) {
                case R.id.nav_pedidos:
                    fragmentClass = OrdersFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_config:
                    fragmentClass = SettingsFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.action_settings:
                    fragmentClass = SettingsFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_product_list:
                    fragmentClass = ProductListFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_register_product:
                    fragmentClass = ProductRegisterFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                default:
                    fragmentClass = SettingsFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,
                    fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void LoginResponse(WebServiceResponse response) {
        Log.e("LOGIN ERROR: ", ""+response);
        if (response.getResponseCode() != 200){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login de usuário");
            builder.setMessage("Não foi possivel fazer login de usuário!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do nothing
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    @Override
    public void gcmPushRegisterFinished(String registrationID) {
        Toast.makeText(this,
                "Dispositivo registrado na nuvem com sucesso.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gcmPushRegisterFailed(String error) {
        Toast.makeText(this,
                "Dispositivo pronto para receber push.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gcmRegisterFinished(String registrationID) {
        Toast.makeText(this,
                "Dispositivo não esta apto a receber push.",
                Toast.LENGTH_SHORT).show();
        GCMRegisterTask gcmRegisterTask = new GCMRegisterTask(this,this);
        gcmRegisterTask.putRegId(registrationID);
    }

    @Override
    public void gcmRegisterFailed(IOException ex) {
        Toast.makeText(this,
                "Falha ao registrar dispositivo na nuvem. " +
                        ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gcmUnregisterFinished() {
        Toast.makeText(this,
                "Dispositivo desregistrado da nuvem.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gcmUnregisterFailed(IOException ex) {
        Toast.makeText(this,
                "Falha ao desregistrar o dispositivo na nuvem.",
                Toast.LENGTH_SHORT).show();
    }
}