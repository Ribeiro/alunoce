package br.com.curso.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.curso.model.Aluno;
import br.com.curso.utils.Constantes;
import br.com.curso.utils.FileUtils;

public class AlunoDao {

	public boolean salvarOuAlterar(Aluno aluno) {
		File file = new File(Constantes.DIR_ALUNO, String.valueOf(aluno.getId()) + ".txt");
		File fileImg = new File(Constantes.DIR_IMAGENS, String.valueOf(aluno.getId()) + ".jpg");
		
		if(file.exists()){
			file.delete();
		} if(fileImg.exists()){
			fileImg.delete();
		}
		
		boolean isSalvoTxt = FileUtils.salvaDadosArquivoTexto(aluno.toString(), file, false);
		boolean isSalvoImg = FileUtils.salvaImagemBitmap(aluno.getFoto(), fileImg);
		
		boolean isSalvo = (isSalvoImg && isSalvoTxt);
		
		return isSalvo;
	}
	
	public void deletar(Aluno aluno) {
		File file = new File(Constantes.DIR_ALUNO, String.valueOf(aluno.getId()) + ".txt");
		File fileImg = new File(Constantes.DIR_IMAGENS, String.valueOf(aluno.getId()) + ".jpg");
		
		if(file.exists()){
			file.delete();
		} if(fileImg.exists()){
			fileImg.delete();
		}
	}
	
	public Aluno buscar(long id) {
		File [] files = Constantes.DIR_ALUNO.listFiles();
		
		if(files.length > 0){
			for (File file : files) {
				List<String> conteudo = FileUtils.lerTxt(file);
				
				if(conteudo != null){
					Aluno aluno = new Aluno(conteudo);
					
					if(aluno.getId() == id){						
						File arquivo = new File(Constantes.DIR_IMAGENS, aluno.getId() + ".jpg");
						aluno.setFoto(FileUtils.lerImagemBitmap(arquivo));
						
						return aluno;
					}
				}
			}
		}
		
		return null;
	}

	public Aluno buscar(String nome) {
		File [] files = Constantes.DIR_ALUNO.listFiles();
		
		if(files.length > 0){
			for (File file : files) {
				List<String> conteudo = FileUtils.lerTxt(file);
				
				if(conteudo != null){
					Aluno aluno = new Aluno(conteudo);
					
					if(aluno.getNome().equalsIgnoreCase(nome)){						
						File arquivo = new File(Constantes.DIR_IMAGENS, aluno.getId() + ".jpg");
						aluno.setFoto(FileUtils.lerImagemBitmap(arquivo));
						
						return aluno;
					}
				}
			}
		}
		
		return null;
	}

	public List<Aluno> listar() {
		if(Constantes.DIR_ALUNO != null && !Constantes.DIR_ALUNO.exists()){
			return null;
		}
		
		File [] files = Constantes.DIR_ALUNO.listFiles();		
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		if(files.length > 0){
			for (File file : files) {
				List<String> conteudo = FileUtils.lerTxt(file);
				
				if(conteudo != null){
					Aluno aluno = new Aluno(conteudo);
											
					File arquivo = new File(Constantes.DIR_IMAGENS, aluno.getId() + ".jpg");
					aluno.setFoto(FileUtils.lerImagemBitmap(arquivo));
						
					alunos.add(aluno);
				}
			}
		}
		
		return alunos;
	}

	public static int getId() {
		int maior = 0;
		
		if(Constantes.DIR_ALUNO != null && !Constantes.DIR_ALUNO.exists()){
			return maior;
		}
		
		File [] files = Constantes.DIR_ALUNO.listFiles();
		
		for (File file : files) {
			if(file.isFile() && file.getName().endsWith(".txt")){

				int id = 0;
				String nomeArqComExtensao = file.getName();				
				int pos = nomeArqComExtensao.lastIndexOf(".");				
				String nomeArqSemExtensao = nomeArqComExtensao;
				
				if(pos != -1){
					nomeArqSemExtensao = nomeArqComExtensao.substring(0, pos).trim();
				}
				
				try {
					id = Integer.parseInt(nomeArqSemExtensao);
				} catch (NumberFormatException e) {
					id = 0;
				}
				
				if(id > maior){
					maior = id;
				}
			}
		}
		
		return maior+1;
	}
}
