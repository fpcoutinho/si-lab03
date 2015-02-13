package models;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Recommend2 extends Recommend{

    public Episodio rec(List<Temporada> temporadas){
        for(int i = temporadas.size()-1; i >= 0; i--){
            if (temporadas.get(i).verificaStatus() != 1) {
                List<Episodio> t = temporadas.get(i).getEpisodios();
                for (int j = t.size()-1; j >= 0; j--){
                    if (t.get(j).isAssistido() && j != t.size() -1) {
                        return t.get(j+1);
                    }
                }
            }
        }
        return new Episodio("Done", new Temporada(0, new Serie("lala")), 0);
    }
    @Override
    public String toString() {
        return "opção 2";
    }
}