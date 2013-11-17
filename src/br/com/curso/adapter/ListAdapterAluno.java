package br.com.curso.adapter;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.curso.model.Aluno;
import br.com.curso.view.R;

public class ListAdapterAluno extends RoboBaseAdapter {

	@InjectView(R.id.adapter_aluno_item_tvItem)
	private TextView adapter_aluno_item_tvItem;
	
	@InjectView(R.id.adapter_aluno_item_ivItem)
	private ImageView adapter_aluno_item_ivItem;
	
	private List<Aluno> alunos;

	public ListAdapterAluno(Context context, int layoutId, List<Aluno> alunos) {
		super(context, layoutId);
		
		this.alunos = (alunos == null) ? new ArrayList<Aluno>() : alunos;
	}
	
	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		return alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<Aluno> getAlunos() {
		return this.alunos;
	}
	
	public void add(Aluno aluno) {
		this.alunos.add(aluno);
	}
	
	public void addAll(List<Aluno> alunos) {
		this.alunos = (alunos == null) ? new ArrayList<Aluno>() : alunos;
	}
	
	public void clear() {
		this.alunos.clear();
	}
	
	public void newList(List<Aluno> alunos) {
		clear();
		addAll(alunos);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		
		Aluno aluno = (Aluno) getItem(position);
		adapter_aluno_item_ivItem.setImageBitmap(aluno.getFoto());
		adapter_aluno_item_tvItem.setText(aluno.getNome());
		
		return view;
	}

}
