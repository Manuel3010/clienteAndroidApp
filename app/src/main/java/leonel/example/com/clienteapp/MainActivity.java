package leonel.example.com.clienteapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    public static List<Cliente> clientes;
    private AdaptadorClientes adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientes = new ArrayList<Cliente>();

        adaptador = new AdaptadorClientes(this,clientes);

        listView = (ListView) findViewById(R.id.listViewClientes);

        registerForContextMenu(listView);
    }

    @Override
    public void onResume() {
        super.onResume();

        listView.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuagregar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.AgregarCliente:
                Intent intent = new Intent(MainActivity.this,CrearClienteActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menueliminar,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.eliminar:
                clientes.remove(info.position);
                adaptador.notifyDataSetChanged();
                return true;

            case R.id.modificar:
                editarPersona((int)info.id);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void editarPersona(int id) {
        Cliente cliente = clientes.get(id);
        Intent intent = new Intent(MainActivity.this, ModificarClienteActivity.class);
        intent.putExtra("index", id );
        intent.putExtra("codigo", cliente.getCodigoCli());
        intent.putExtra("nombre", cliente.getNombreCli());
        intent.putExtra("apellido", cliente.getApellidoCli());
        intent.putExtra("dui", cliente.getDuiCli());
        intent.putExtra("tipo", cliente.getTipoCli());
        intent.putExtra("pago", cliente.getPagoCli());

        startActivity(intent);
    }

    class AdaptadorClientes extends ArrayAdapter<Cliente>{
            Activity context;

        public AdaptadorClientes(Activity context, List<Cliente> datos){
            super(context,R.layout.listitemcliente,datos);
        }

        public View getView(int posicion, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitemcliente, null);

            TextView lblCodigo = (TextView)item.findViewById(R.id.LblCodigo);
            TextView lblNombre = (TextView)item.findViewById(R.id.LblNombre);
            TextView lblApellidos = (TextView)item.findViewById(R.id.LblApellido);
            TextView lblTipo = (TextView)item.findViewById(R.id.LblTipo);
            TextView lblFormaDePago = (TextView)item.findViewById(R.id.LblFormaDePago);
            TextView lblDui = (TextView)item.findViewById(R.id.LblDUI);

            lblCodigo.setText(clientes.get(posicion).getCodigoCli());
            lblNombre.setText(clientes.get(posicion).getNombreCli());
            lblApellidos.setText(clientes.get(posicion).getApellidoCli());
            lblTipo.setText(clientes.get(posicion).getTipoCli());
            lblFormaDePago.setText(clientes.get(posicion).getPagoCli());
            lblDui.setText(clientes.get(posicion).getDuiCli());

            return (item);
        }
    }


}

