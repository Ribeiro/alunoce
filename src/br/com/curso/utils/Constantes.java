package br.com.curso.utils;

import java.io.File;

import android.os.Environment;

public class Constantes {

	/**
	 * LOGS
	 */
	public static final String CATEGORIA = "aluno";
	
	/**
	 * Nova linha de acordo com o SO
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	/**
	 * Diretorios
	 */
	public static final File DIR_SDCARD = Environment.getExternalStorageDirectory();
	public static final File DIR_ALUNO = new File(DIR_SDCARD, "aluno_verde/");
	public static final File DIR_IMAGENS = new File(DIR_ALUNO, "imagens/");
	
}
