package com.example.darkknight.cinemateatralv2.Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.darkknight.cinemateatralv2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class adaptadorListaDesplegable extends BaseExpandableListAdapter {


    private Context context;
    private List<String>listaTitulos;
    private Map<String,List<String>>listaItems;

    public adaptadorListaDesplegable(Context context, List<String> listaTitulos, Map<String, List<String>> listaItems) {
        this.context = context;
        this.listaTitulos = listaTitulos;
        this.listaItems = listaItems;
    }

    @Override
    public int getGroupCount() {
        return listaTitulos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listaItems.get(listaTitulos.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listaTitulos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listaItems.get(listaTitulos.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String titulo = (String)getGroup(groupPosition);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.lista_grupo,null);
        }
        TextView txtTitulo = convertView.findViewById(R.id.textViewGrupo);
        txtTitulo .setTypeface(null, Typeface.BOLD);
        txtTitulo.setText(titulo);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String titulo = (String)getChild(groupPosition,childPosition);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.lista_item,null);
        }
        TextView txtChild = convertView.findViewById(R.id.textViewItem);
        //txtChild .setTypeface(null, Typeface.BOLD);
        txtChild.setText(titulo);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
