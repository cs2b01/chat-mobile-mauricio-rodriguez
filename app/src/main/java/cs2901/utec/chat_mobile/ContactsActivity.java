package cs2901.utec.chat_mobile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsActivity extends RecyclerView.Adapter<ContactsActivity.ViewHolderContacts> {
    ArrayList<String> listContactos;

    public ContactsActivity(ArrayList<String> listContactos) {
        this.listContactos = listContactos;
    }

    @NonNull
    @Override
    public ViewHolderContacts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list,null,false);
        return new ViewHolderContacts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContacts holder, int position) {
        holder.asignarContactos(listContactos.get(position));
    }

    @Override
    public int getItemCount() {
        return listContactos.size();
    }

    public class ViewHolderContacts extends RecyclerView.ViewHolder{
        TextView contactos;
        public ViewHolderContacts(View itemView) {
            super(itemView);
            contactos = itemView.findViewById(R.id.idcontactos);
        }

        public void asignarContactos(String contacto) {
            contactos.setText(contacto);
        }
    }
}
