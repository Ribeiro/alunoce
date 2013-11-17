package br.com.curso.view;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.curso.dao.AlunoDao;
import br.com.curso.model.Aluno;
import br.com.curso.utils.ImageManipulation;

import com.google.inject.Inject;

@ContentView(R.layout.activity_form)
public class FormActivity extends RoboActivity implements OnClickListener, OnLongClickListener {

	public static final String SAVE_STATE = "state_aluno";
	public static final String INTENT_EXTRA_DATA_ID = "_id_";
	public static final String INTENT_EXTRA_DATA_ALUNO = "_aluno_";
	
	private static final int INTENT_RESULT_DATA_CAMERA = 101;	
	

	@Inject
	private AlunoDao alunoDao;
	
	@InjectView(R.id.tvId)
	private TextView tvId;
	
	@InjectView(R.id.ivContato)
	private ImageView ivContato;
	
	@InjectView(R.id.etNome)
	private EditText etNome;
	
	@InjectView(R.id.etTelefone)
	private EditText etTelefone;
	
	@InjectView(R.id.etEmail)
	private EditText etEmail;
	
	@InjectView(R.id.etEndereco)
	private EditText etEndereco;
	
	@InjectView(R.id.spCurso)
	private Spinner spCurso;
	
	@InjectView(R.id.rbMasc)
	private RadioButton rbMasc;
	
	@InjectView(R.id.rbFemi)
	private RadioButton rbFemi;
	
	@InjectView(R.id.chkMatriculado)
	private CheckBox chkMatriculado;
	
	@InjectView(R.id.btSalvar)
	private Button btSalvar;

	@InjectResource(R.array.cursos)
	private String [] cursos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// Habilita o Click sobre a imagem do Contato
		ivContato.setOnClickListener(this);
		ivContato.setOnLongClickListener(this);
		
		// Habilita o Click sobre botao Salvar 
		btSalvar.setOnClickListener(this);
		
		// Obtem os dados da Activity que a chamou
		Intent intent = getIntent();
		Aluno aluno = intent.getParcelableExtra(INTENT_EXTRA_DATA_ALUNO);
		setAluno(aluno);

//		String idStr = intent.getStringExtra(INTENT_EXTRA_DATA_ID);
//		// Obtem os dados do aluno e preenche na tela
//		if(idStr != null){
//			carregaDadosPeloId(Long.parseLong(idStr));
//		}
	}

	/**
	 * Configura o evento de click dos botoes
	 */
	@Override
	public void onClick(View v) {
		if(v == btSalvar){
			Aluno aluno = getAluno();
//			Log.i("aluno", aluno.toString());			
			alunoDao.salvarOuAlterar(aluno);
			
			Toast.makeText(this,
					aluno.getNome() + " salvo com sucesso!", 
					Toast.LENGTH_SHORT)
					.show();
			
			finish();
			
		} else if(v == ivContato){
			Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(it, INTENT_RESULT_DATA_CAMERA);
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if(v == ivContato){
			Bitmap photo = ((BitmapDrawable) ivContato.getDrawable()).getBitmap();
			Bitmap newPhoto = ImageManipulation.rotate(photo, -90);
			
			ivContato.setImageBitmap(newPhoto);
		}
		
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    	
		switch (requestCode) {
			case INTENT_RESULT_DATA_CAMERA:
				if(data != null){
					Bundle bundle = data.getExtras();
					
					if(bundle != null){
						// Recupera o bitmap retornado pela camera
						Bitmap bitmap = (Bitmap) bundle.get("data");
						
						int newH = 72;
						int newW = (int) (bitmap.getWidth() / (bitmap.getWidth()/72));
						Bitmap resize = ImageManipulation.resize(bitmap, newH, newW);
						
						// Atualiza a imagem na ImageView
						ivContato.setImageBitmap(resize);
					}
				}
				
				break;
		}
	}

	/**
	 * Cria um objeto Aluno a partir dos componentes da Tela
	 * 
	 * @return Aluno
	 */
	private Aluno getAluno() {
		int id = 0;
		
		try {
			id = Integer.parseInt(tvId.getText().toString());
		} catch (NumberFormatException e) {
			id = AlunoDao.getId();
		}
		
		Bitmap foto = ((BitmapDrawable) ivContato.getDrawable()).getBitmap();
		String nome = etNome.getText().toString();
		String telefone = etTelefone.getText().toString();
		String email = etEmail.getText().toString();
		String endereco = etEndereco.getText().toString();
		String curso = spCurso.getSelectedItem().toString();
		String sexo = rbMasc.isChecked() ? "Masculino" : "Feminino";
		boolean matriculado = chkMatriculado.isChecked();
		
		Aluno aluno = new Aluno(id, foto, nome, telefone, email, endereco, curso, sexo, matriculado);
		return aluno;
	}
	
	/**
	 * Seta os campos da Tela a partir de um objeto Aluno
	 * 
	 * @param aluno
	 */
	private void setAluno(Aluno aluno) {
		if(aluno != null){
			tvId.setText(String.valueOf(aluno.getId()));
			ivContato.setImageBitmap(aluno.getFoto());
			etNome.setText(aluno.getNome());
			etTelefone.setText(aluno.getTelefone());
			etEmail.setText(aluno.getEmail());
			etEndereco.setText(aluno.getEndereco());
			spCurso.setSelection(getPosition(aluno.getCurso()));
			rbMasc.setChecked(aluno.getSexo().equalsIgnoreCase("Masculino") ? true : false);
			rbFemi.setChecked(aluno.getSexo().equalsIgnoreCase("Feminino") ? true : false);
			chkMatriculado.setChecked(aluno.isMatriculado());
		}
	}
	
	/**
	 * Retorna o indice do curso
	 * 
	 * @param curso
	 * @return i
	 */
	private int getPosition(String curso) {
		for (int i = 0; i < cursos.length; i++) {
			String item = cursos[i];
			if(curso.equalsIgnoreCase(item)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Ler os dados do aluno com o id passado e preenche na tela
	 * 
	 * @param id_aluno
	 */
	private void carregaDadosPeloId(long id) {
		Aluno aluno = alunoDao.buscar(id);
		
		if(aluno != null){
			setAluno(aluno);
		} else{
			Toast.makeText(this, "O aluno de ID="+id+" não foi encontrado!",
					Toast.LENGTH_LONG).show();			
			finish();
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable(SAVE_STATE, getAluno());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		Aluno aluno = savedInstanceState.getParcelable(SAVE_STATE);
		setAluno(aluno);
	}
}
