package leonel.example.com.clienteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by leone on 24/4/2017.
 */

public class ModificarClienteActivity extends AppCompatActivity {
    Spinner cmbModoPago;
    EditText nombreCli;
    EditText apellidosCli;
    EditText duiCli;
    EditText codigoCli;
    Button btnModificar;
    Button btnCancelar;
    Cliente cliente = new Cliente();
    RadioGroup tipoCli;
    boolean checked;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymodificarcliente);
        Bundle extras = getIntent().getExtras();
        btnModificar = (Button) findViewById(R.id.btnModificar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        nombreCli = (EditText) findViewById(R.id.nombreText);
        nombreCli.setText(extras.getString("nombre"));
        apellidosCli = (EditText) findViewById(R.id.apellidoText);
        apellidosCli.setText(extras.getString("apellido"));
        duiCli = (EditText) findViewById(R.id.duiText);
        duiCli.setText(extras.getString("dui"));
        codigoCli = (EditText) findViewById(R.id.codigoText);
        codigoCli.setText(extras.getString("codigo"));
        tipoCli= (RadioGroup) findViewById(R.id.tipoRgbtn);
        cmbModoPago = (Spinner) findViewById(R.id.opcionSpn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_cliente, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbModoPago.setAdapter(adapter);

        cmbModoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Seleccione Modo Pago", Toast.LENGTH_LONG).show();
            }
        });

        cmbModoPago.setSelection(adapter.getPosition(extras.getString("pago")));

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        RadioGroup rg = (RadioGroup)findViewById(R.id.tipoRgbtn);
        rg.clearCheck();
        switch (extras.getString("tipo")){
            case "Minorista":
                checked = true;
                rg.check(R.id.rbtnMinorista);
                this.cliente.setTipoCli("Minorista");
                break;
            case "Mayorista":
                checked = true;
                rg.check(R.id.rbtnMayorista);
                this.cliente.setTipoCli("Mayorista");
                break;
        }
    }

    public  void limpiarForm(){
        nombreCli.getText().clear();
        apellidosCli.getText().clear();
        duiCli.getText().clear();
    }

    public void modificarCliente(View view){
        if (duiCli.getText().toString().isEmpty() ||
                nombreCli.getText().toString().isEmpty() ||
                apellidosCli.getText().toString().isEmpty() ||
                !checked    )
        {
            Toast.makeText(getApplicationContext(), "Campo requerido", Toast.LENGTH_LONG).show();
        }else{

            this.cliente.setPagoCli(cmbModoPago.getSelectedItem().toString());
            this.cliente.setNombreCli(nombreCli.getText().toString());
            this.cliente.setApellidoCli(apellidosCli.getText().toString());
            this.cliente.setDuiCli(duiCli.getText().toString());
            this.cliente.setCodigoCli(codigoCli.getText().toString());

            MainActivity.clientes.set(Integer.parseInt(codigoCli.getText().toString())-1, cliente);
            finish();
        }
    }

    public void onRadioButtonClicked(View view) {
        checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rbtnMayorista:
                if (checked)
                    this.cliente.setTipoCli("Mayorista");
                break;
            case R.id.rbtnMinorista:
                if (checked)
                    this.cliente.setTipoCli("Minorista");
                break;
        }
    }

}
