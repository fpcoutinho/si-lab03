package controllers;

import java.util.List;

import models.*;
import models.dao.GenericDAO;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	private static List<Serie> series;
	private static GenericDAO dao = new GenericDAO();
	
	@Transactional
	public static Result index() {
		String port = System.getenv("PORT");
		if (port==null || port.isEmpty()){
			port = "8080";
		}
		System.setProperty("server.port", port);
    	getSeriesFromDB();
        return ok(index.render(series));
    }
    
	@Transactional
	public static Result acompanhaSerie(Long id){
		Serie serie = getDAO().findByEntityId(Serie.class, id);
		serie.setAcompanhada(true);
		getDAO().merge(serie);
		getDAO().flush();
		
		Logger.info("Acompanhando " + serie.getNome());
		
		return redirect("/");
	}
	
	@Transactional
	public static Result desacompanhaSerie(Long id){
		Serie serie = getDAO().findByEntityId(Serie.class, id);
		serie.setAcompanhada(false);
		getDAO().merge(serie);
		getDAO().flush();
		
		Logger.info("Desacompanhou " + serie.getNome());
		
		return redirect("/");
	}
	
	@Transactional
	public static Result cancelaEpisodio(Long id){
		Episodio ep = getDAO().findByEntityId(Episodio.class, id);
		ep.setAssistido(false);
		Temporada temp = ep.getTemporada();
		int epiNum = ep.getNumero();
		temp.removeOrdenacaoEpisodios(epiNum);
		getDAO().merge(ep);
		getDAO().merge(temp);
		getDAO().flush();
		
		Logger.info("Cancelou ep " + ep.getNome());
		
		return redirect("/");
	}

	@Transactional
	public static Result setRecomendacao(Long id) {
		Serie serie = getDAO().findByEntityId(Serie.class, id);
		DynamicForm form = Form.form().bindFromRequest();
		int opcao = Integer.parseInt(form.get("opcao"));

		if (opcao == 1){
			serie.setR(new Recommend1());
		}else if (opcao == 2){
			serie.setR(new Recommend2());
		}else if (opcao == 3){
			serie.setR(new Recommend3());
		}
		getDAO().merge(serie);
		getDAO().flush();

		Logger.info("Próximo ep por opção " + opcao);

		return redirect("/");
	}
	
	@Transactional
	public static Result assisteEpisodio(Long id){
		Episodio ep = getDAO().findByEntityId(Episodio.class, id);
		Temporada temp = ep.getTemporada();
		int epiNum = ep.getNumero();
		temp.addOrdenacaoEpisodios(epiNum);
		Logger.debug(""+temp.getOrdenacaoEpisodios());
		ep.setAssistido(true);
		getDAO().merge(ep);
		getDAO().merge(temp);
		getDAO().flush();
		
		Logger.info("Assistiu ep " + ep.getNome());
		
		return redirect("/");
	}
	
    @Transactional
	private static void getSeriesFromDB(){
		series = getDAO().findAllByClassName("Serie");
		getDAO().flush();
	}
    
    public static GenericDAO getDAO(){
		return dao;
	}

}
