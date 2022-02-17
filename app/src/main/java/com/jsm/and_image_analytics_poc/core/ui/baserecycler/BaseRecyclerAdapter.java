package com.jsm.and_image_analytics_poc.core.ui.baserecycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;


/**
 * Clase base para extender y crear los adaptadores de los recyclerViews de la aplicación
 * Tiene mecanismos de actualización automática y manejo de errores usando programación reactiva (LiveData)
 * @param <T> Tipo de objetos que se muestran en el recyclerView
 * @param <VH> Tipo del ViewHolder que se utiliza para la creación de las vistas
 * @param <RT> Tipo del response del repositorio asociado al viewmodel
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerViewHolder<T>, RT> extends RecyclerView.Adapter<VH> {

    private Context context;
    protected BaseRecyclerViewModel<T, RT> viewModel;
    protected NavController navController;
    private int listItemResource;


    /**
     *
     * @param context
     * @param viewModel ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner Propietario del ciclo de vida
     * @param navController Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public BaseRecyclerAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource) {
        super();
        this.context = context;
        this.viewModel = viewModel;
        this.navController = navController;
        this.listItemResource = listItemResource;

        viewModel.getApiResponseMediator().observe(lifeCycleOwner, categoryApiListResponse -> Log.d("Activado", "Activado"));
        viewModel.getElements().observe(lifeCycleOwner, categoriesResponse -> {

                    this.notifyDataSetChanged();
                }

        );
        viewModel.getError().observe(lifeCycleOwner, error -> {
            viewModel.handleError((BaseException) error, context);
        });
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
        T element = viewModel.getElements().getValue().get(position);
        holder.fillViewHolder(element);
        holder.itemView.setOnClickListener(v -> viewModel.onItemSelected(position, navController, v.getContext()));

    }

    /**
     * Devuelve el tamaño del listado de elementos (invocado por el layout manager)
     * @return
     */
    @Override
    public int getItemCount() {
        return viewModel.getElements().getValue().size();
    }


}
