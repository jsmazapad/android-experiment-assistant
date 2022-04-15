package com.jsm.exptool.core.ui.baserecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * Clase base para extender y crear los adaptadores de los recyclerViews de la aplicación
 * Tiene mecanismos de actualización automática y manejo de errores usando programación reactiva (LiveData)
 * @param <T> Tipo de objetos que se muestran en el recyclerView
 * @param <VH> Tipo del ViewHolder que se utiliza para la creación de las vistas
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private final Context context;
    protected LiveData<List<T>> elements;
    protected OnRecyclerItemSelectedListener onRecyclerItemSelectedListener;
    protected NavController navController;
    private final int listItemResource;


    //TODO Pensar si solo se usa el segundo constructor y se suprime este
    /**
     *
     * @param context
     * @param onRecyclerItemSelectedListener callback para cuando se hace click en un elemento
     * @param elements Listado de elementos del recycler
     * @param navController Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public BaseRecyclerAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<T>> elements, NavController navController, int listItemResource) {
        super();
        this.context = context;
        this.onRecyclerItemSelectedListener = onRecyclerItemSelectedListener;
        this.elements = elements;
        this.navController = navController;
        this.listItemResource = listItemResource;
    }

    /**
     *
     * @param context
     * @param onRecyclerItemSelectedListener callback para cuando se hace click en un elemento
     * @param elements Listado de elementos del recycler
     * @param owner Propietario del ciclo de vida que se usará para la suscripción a cambios en los elementos proporcionados
     * @param navController Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public BaseRecyclerAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<T>> elements, LifecycleOwner owner, NavController navController, int listItemResource) {
        super();
        this.context = context;
        this.onRecyclerItemSelectedListener = onRecyclerItemSelectedListener;
        this.elements = elements;
        this.navController = navController;
        this.listItemResource = listItemResource;
        this.elements.observe(owner, elementsResponse ->{
            this.notifyDataSetChanged();
        } );
    }

    /**
     * {@inheritdoc}
     * Creamos la vista usando el recurso layout que corresponda
     * Instanciamos el viewholder pasándole y lo devolvemos
     */
    @Override
    public VH onCreateViewHolder(ViewGroup Base, int viewType) {
        // create a new view
        View v = LayoutInflater.from(Base.getContext())
                .inflate(listItemResource, Base, false);

        VH viewHolder = instanceViewHolder(v);
        return viewHolder;
    }

    /**
     * Devuelve una instancia del ViewHolder (es necesario porque no se pueden instanciar tipos genéricos)
     * @param v Vista para asociar al viewholder
     * @return
     */
    public abstract VH instanceViewHolder(View v);

    /**
     * {@inheritdoc}
     * Realizamos el binding entre los parámetros del objeto de la lista y la vista
     * Además seteamos el evento onClick de la vista para que llame al método correspondiente del viewmodel
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        T element = elements.getValue().get(position);
        holder.fillViewHolder(element);
        holder.itemView.setOnClickListener(v -> onRecyclerItemSelectedListener.onItemSelected(position, navController, v.getContext()));

    }

    /**
     * Devuelve el tamaño del listado de elementos (invocado por el layout manager)
     * @return
     */
    @Override
    public int getItemCount() {
        return elements.getValue().size();
    }


}
