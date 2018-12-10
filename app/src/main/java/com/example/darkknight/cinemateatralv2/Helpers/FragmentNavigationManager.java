package com.example.darkknight.cinemateatralv2.Helpers;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


import com.example.darkknight.cinemateatralv2.BuildConfig;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_cine_fragment;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_obra_teatro_fragment;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_pelicula_fragment;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_teatro_fragment;
import com.example.darkknight.cinemateatralv2.Fragmentos.bienvenida;
import com.example.darkknight.cinemateatralv2.Interfaces.NavigationManager;
import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.menu_lateral_principal;

public class FragmentNavigationManager implements NavigationManager {



   private static FragmentNavigationManager mIntance;

   private FragmentManager mFragmentManager;
   private menu_lateral_principal mlprincipal;

   public static FragmentNavigationManager getmIntance(menu_lateral_principal mlprincipal){

       if(mIntance == null){

           mIntance = new FragmentNavigationManager();
           mIntance.configurar(mlprincipal);
       }
       return mIntance;
   }

   private void configurar(menu_lateral_principal mlprincipal){

       mlprincipal = mlprincipal;
       this.mFragmentManager = mlprincipal.getFragmentManager();
   }


    @Override
    public void showFragmentBienvenida() {

       showFragment(bienvenida.newInstance(),false);
    }
    @Override
    public void showFragmentABMCines() {

        showFragment(abm_cine_fragment.newInstance(),false);
    }
    @Override
    public void showFragmentABMTeatros() {

        showFragment(abm_teatro_fragment.newInstance(),false);
    }
    @Override
    public void showFragmentABMObraTeatro() {

        showFragment(abm_obra_teatro_fragment.newInstance(),false);
    }
    @Override
    public void showFragmentABMPelicula() {

        showFragment(abm_pelicula_fragment.newInstance(),false);
    }
    private void showFragment(Fragment fragmentContent, boolean b){

       FragmentManager fm = mFragmentManager;
       FragmentTransaction ft = fm.beginTransaction();
       Fragment currentFragment = fm.findFragmentById(R.id.content_frame);

        if(currentFragment == null){ //Al ser el primer fragment, se setea al comienzo

               setearFragment(b,fragmentContent);
        }
        else {
            //if (currentFragment.isVisible() && fragmentContent.isHidden())
            if(fragmentContent.isAdded())
            {

                if(isBackStackExists(fragmentContent.getTag()))
                ft.show(fragmentContent);
            }
        }

        setearFragment(b,fragmentContent);
    }

    public void setearFragment(boolean b,Fragment fragmentContent){

        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame,fragmentContent,fragmentContent.getClass().getName());
        ft.addToBackStack(fragmentContent.getTag());
        if(b || !BuildConfig.DEBUG){
            ft.commitAllowingStateLoss();
        }else{
            ft.commit();
        }
    }
    public boolean isBackStackExists(String tag) {
        FragmentManager manager = mFragmentManager;
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            String backStackTag = manager.getBackStackEntryAt(i).getName();
            if (backStackTag.equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
