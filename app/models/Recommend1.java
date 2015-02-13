package models;

import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class Recommend1 extends Recommend{

    @Override
    public Episodio rec(List<Temporada> temporadas){
        for(Temporada temp: temporadas){
            if (temp.verificaStatus() != 1) {
                List<Episodio> t = temp.getEpisodios();
                for (Episodio ep: t) {
                    if (!ep.isAssistido()) {
                        return ep;
                    }
                }
            }
        }
        return new Episodio("Done", new Temporada(0, new Serie("lala")), 0);
    }

    @Override
    public String toString() {
        return "opção 1";
    }
}