package br.com.curso.utils;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class Sms {

	// LogCat para o SMS
	public static final String LOG_SMS = "log_sms";
	
	/**
	 * Envia um SMS para o numero indicado
	 * Obs.: o numero pode ser o do proprio emulador, exemplo: 5554
	 * 
	 * @param context
	 * @param numero
	 * @param mensagem
	 * @return
	 */
	public static boolean enviarSms(Context context, String numero, String mensagem) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
			
			// identifica a chamada
			PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0); 
			
			// Divide a mensagem caso ultrapasse 160 caracteres
			ArrayList<String> messages = smsManager.divideMessage(mensagem);
			
			// Instancia uma lista de PendingIntent
			ArrayList<PendingIntent> pIntents = new ArrayList<PendingIntent>(messages.size());
			
			for (int i = 0; i < messages.size(); i++) {
				pIntents.add(pIntent);
			}
			
			// envia a mensagem binaria
			smsManager.sendMultipartTextMessage(numero, null, messages, pIntents, null);
			
			return true;
			
		} catch (Exception e) {
			Log.e(LOG_SMS, "Erro ao enviar sms: " + e.getMessage(), e);
			return false;
		}
	}
	
}
