package com.curso.widget;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class MiWidget extends AppWidgetProvider{



	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		//super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for (int i=0; i<appWidgetIds.length;i++)
		{
			int widgetId=appWidgetIds[i];
			actualizarWidget(context, appWidgetManager, widgetId);
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//super.onReceive(context, intent);
		if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE"))
		{
			//Obtener el ID  del widget
			int widgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			
			//Obtenemos el widget del contexto
			AppWidgetManager widgetManager= AppWidgetManager.getInstance(context);
			
			//Actualizamos el widget
			if (widgetId!=AppWidgetManager.INVALID_APPWIDGET_ID)
			{
				actualizarWidget(context,widgetManager,widgetId);
			}
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		SharedPreferences prefs =context.getSharedPreferences("WidgetPrefs", context.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		
		for (int i=0;  i < appWidgetIds.length;i++)
		{
			int widgetId=appWidgetIds[i];
			editor.remove("msg"+widgetId);
		}
		
		editor.commit();
		
		super.onDeleted(context, appWidgetIds);
	}

	public static void actualizarWidget(Context context, AppWidgetManager widgetManager, int widgetID) {
		// TODO Auto-generated method stub
		//Recuperar preferencias
		SharedPreferences prefs=context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
		String mensaje=prefs.getString("msg"+widgetID, "La hora");
		
		//Obtener los controles del widget
		RemoteViews controles = new RemoteViews(context.getPackageName(),R.layout.miwidget);
		
		//posicionamos eventos del widget
		Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		controles.setOnClickPendingIntent(R.id.btn_Actualizar, pendingIntent);

		Intent intent2 = new Intent(context,MainActivity.class);
		PendingIntent pendingIntent2 = PendingIntent.getActivity(context, widgetID, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
		
		controles.setOnClickPendingIntent(R.id.frmWidget, pendingIntent2);
		
		//Actualizamos el mensaje
		
		controles.setTextViewText(R.id.tv_mensaje, mensaje);
		Calendar calendario = new GregorianCalendar();
		
		String hora= calendario.getTime().toLocaleString();
		
		controles.setTextViewText(R.id.tv_hora, hora);
		widgetManager.updateAppWidget(widgetID, controles);
		
		
	}

	
}
