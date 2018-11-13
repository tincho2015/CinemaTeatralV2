package com.example.darkknight.cinemateatralv2.Helpers;

import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;


import com.example.darkknight.cinemateatralv2.ContenedorFragments.FragmentContent;
import com.example.darkknight.cinemateatralv2.Interfaces.NavigationManager;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
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
       mFragmentManager = mlprincipal.getSupportFragmentManager();
   }


    @Override
    public void showFragment(String titulo) {

       showFragment(FragmentContent.newInstance(titulo),false);
    }
    private void showFragment(Fragment fragmentContent, boolean b){

        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.content_frame,fragmentContainer);
    }
}
