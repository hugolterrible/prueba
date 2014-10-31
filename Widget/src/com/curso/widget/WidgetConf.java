package com.curso.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class WidgetConf extends Activity implements OnClickListener {

	private Button btnAceptar, btnCancelar;
	private EditText etMensaje;
	private  int widgetID=0;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_conf);
		
		Intent intentOrigen = getIntent();
		
		Bundle params = intentOrigen.getExtras();
		
		widgetID=params.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		
		setResult(RESULT_CANCELED);
		
		
		btnAceptar=(Button)findViewById(R.id.btn_Aceptar);
		btnAceptar.setOnClickListener(this);
		
		btnCancelar=(Button)findViewById(R.id.btn_Cancelar);
		btnCancelar.setOnClickListener(this);
		
		etMensaje=(EditText)findViewById(R.id.et_mensaje);
		
		
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	   switch (v.getId()) {
		case R.id.btn_Cancelar:
			finish();
			break;
		case R.id.btn_Aceptar:
			SharedPreferences prefs = getSharedPreferences("WidgePrefs", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor= prefs.edit();
			editor.putString("msg"+widgetID, etMensaje.getText().toString());
			
			//actualizamos el widget tras la configuracion
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetConf.this);
			MiWidget.actualizarWidget(WidgetConf.this, appWidgetManager, widgetID);
			
			//regresamos un ok
			Intent resultado = new Intent();
			resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
			setResult(RESULT_OK,resultado);
			finish();
			
			
			break;
	default:
		break;
	}
		
		
	}
}
