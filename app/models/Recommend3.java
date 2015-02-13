package models;

import java.lang.Override;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Recommend3 extends Recommend{

    @Override
    public Episodio rec(List<Temporada> temporadas) {
        for(Temporada temp: temporadas){
            if (temp.verificaStatus() != 1) {
                for (int i = 0; i < temp.getEpisodios().size(); i ++) {
                    Episodio ep = temp.getEpisodios().get(i);
                    if (!ep.isAssistido() && !check3(temp, i)) {
                        return ep;
                    }
                }
            }
        }
        return new Episodio("Done", new Temporada(0, new Serie("lala")), 0);
    }

    public boolean check3(Temporada temp, int n){
        int count = 0;
            List<Episodio> episodios = temp.getEpisodios();
            for (int j = n+1; j < episodios.size(); j++) {
                if (episodios.get(j).isAssistido()){
                    count++;
                }
                if (count >= 3){
                    return true;
                }
            }

        return false;
    }

    @Override
    public String toString() {
        return "opção 3";
    }
}