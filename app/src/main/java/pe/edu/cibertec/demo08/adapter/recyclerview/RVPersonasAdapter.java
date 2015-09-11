package pe.edu.cibertec.demo08.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import pe.edu.cibertec.demo08.R;
import pe.edu.cibertec.demo08.dao.PersonaDAO;
import pe.edu.cibertec.demo08.entities.Persona;

/**
 * Created by luisrios on 9/5/15.
 */
public class RVPersonasAdapter extends RecyclerView.Adapter<RVPersonasAdapter.RVPersonasAdapterViewHolder> implements Filterable {

    private String sFilter = "";
    private ArrayList<Persona> mLstPersona, mLstPersonaFilter;
    private RVPersonasAdapterCallBack mRVPersonasAdapterCallBack;
    private RVPersonasAdapterFilter mRVPersonasAdapterFilter;

    @Override
    public Filter getFilter() {
        if (mRVPersonasAdapterFilter == null)
            mRVPersonasAdapterFilter = new RVPersonasAdapterFilter();
        return mRVPersonasAdapterFilter;
    }

    public interface RVPersonasAdapterCallBack {
        void onPersonaClick(Persona persona, int position);
    }

    public RVPersonasAdapter(RVPersonasAdapterCallBack mRVPersonasAdapterCallBack) {
        this.mRVPersonasAdapterCallBack = mRVPersonasAdapterCallBack;
        mLstPersonaFilter = new ArrayList<>();
        mLstPersona = new ArrayList<>();
        mLstPersona.addAll(new PersonaDAO().listPersona());
        mLstPersonaFilter.addAll(mLstPersona);
    }

    @Override
    public RVPersonasAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVPersonasAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.personas_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RVPersonasAdapterViewHolder rvPersonasAdapterViewHolder, int i) {
        Persona persona = mLstPersonaFilter.get(i);

        rvPersonasAdapterViewHolder.itemView.setTag(i);
        rvPersonasAdapterViewHolder.itemView.setOnClickListener(itemViewOnClickListener);
        rvPersonasAdapterViewHolder.tvPersonasItemNombre.setText(persona.getNombre());
        rvPersonasAdapterViewHolder.tvPersonasItemApellido.setText(persona.getApellido());
        rvPersonasAdapterViewHolder.tvPersonasItemEdad.setText(String.valueOf(persona.getEdad()));
        rvPersonasAdapterViewHolder.tvPersonasItemDNI.setText(persona.getDNI());
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mRVPersonasAdapterCallBack != null)
                mRVPersonasAdapterCallBack.onPersonaClick(mLstPersonaFilter.get( (int) view.getTag()), (int) view.getTag() );
        }
    };

    @Override
    public int getItemCount() {
        return mLstPersonaFilter.size();
    }

    static class RVPersonasAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvPersonasItemNombre, tvPersonasItemApellido, tvPersonasItemEdad, tvPersonasItemDNI;

        public RVPersonasAdapterViewHolder(View itemView) {
            super(itemView);
            tvPersonasItemNombre = (TextView) itemView.findViewById(R.id.tvPersonasItemNombre);
            tvPersonasItemApellido = (TextView) itemView.findViewById(R.id.tvPersonasItemApellido);
            tvPersonasItemEdad = (TextView) itemView.findViewById(R.id.tvPersonasItemEdad);
            tvPersonasItemDNI = (TextView) itemView.findViewById(R.id.tvPersonasItemDNI);
        }
    }

    public void add(Persona persona){
        mLstPersona.add( persona );
        notifyItemInserted(mLstPersona.size());
    }

    public void update(Persona persona,int position){
        Persona personaOld = mLstPersona.get(position);
        personaOld.setNombre( persona.getNombre() );
        personaOld.setApellido(persona.getApellido());
        personaOld.setEdad(persona.getEdad());
        personaOld.setDNI(persona.getDNI());
        notifyItemChanged(position);

    }

    public ArrayList<Persona> refrescar (){
        PersonaDAO listar = new PersonaDAO();
        return listar.listPersona();
    }





    class RVPersonasAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            sFilter = charSequence == null ? "" : charSequence.toString();
            FilterResults filterResults = new FilterResults();

            if (sFilter.isEmpty()) {
                filterResults.values = mLstPersona;
                filterResults.count = mLstPersona.size();
            } else {
                String[] sFilters = sFilter.toUpperCase().split(" ");
                boolean isContains = false;

                ArrayList<Persona> mLstPersonaNew = new ArrayList<>();

                for (int i = 0; i < mLstPersonaNew.size(); i++) {
                    isContains = true;
                    Persona persona = mLstPersonaNew.get(i);

                    for (int j = 0; j < sFilters.length; j++) {
                        if (persona.getNombre().toUpperCase().contains(sFilters[i]) || persona.getApellido().toUpperCase().contains(sFilters[i]) || String.valueOf(persona.getEdad()).toUpperCase().contains(sFilters[i]) || persona.getDNI().toUpperCase().contains(sFilters[i])) {
                            isContains = false;
                            break;
                        }
                    }

                    if (isContains)
                        mLstPersonaNew.add(persona);
                }

                filterResults.values = mLstPersonaNew;
                filterResults.count = mLstPersonaNew.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null) {
                mLstPersonaFilter.clear();
                mLstPersonaFilter.addAll((ArrayList<Persona>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    }
}
