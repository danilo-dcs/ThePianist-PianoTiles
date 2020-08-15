package com.mygdx.pianist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class Musica {
    private Map <String, Sound> sons;
    public Array<String> notasMusicais;
    private int atual = 0;
    private boolean fimMusica;

    public Musica(String musica){
        FileHandle arq = Gdx.files.internal(musica + ".txt");   // iniciando o arquivo de texto que contém as notas musicais da música
        String texto = arq.readString();                            //  lendo o conteúdo do arquivo de texto para uma string
        notasMusicais = new Array<String>(texto.split(" "));    // separando cada letra do arquivo txt em uma string, dentro de um vetor

        sons = new HashMap<String, Sound>();                      // ciando o hashmap que irá associar cada letra ( e , a , g , c ..) a um som da nota musical.
        fimMusica = false;                                        // variável que irá indicar o fim da música;

        for(String s:notasMusicais){
            if(!sons.containsKey(s)){                   // caso o hashMap não contenha a "chave" refenrente ao som de uma nota
                sons.put(s,Gdx.audio.newSound(Gdx.files.internal(s + ".wav")));        // será acrescentado essa chave no hashMap
            }
        }
    }

    public boolean getFim(){
        return fimMusica;
    }
    public void setFim(){
        fimMusica = false;
    }

    public void tocar(){                             // função que irá reprodizir os sons das notas
        sons.get(notasMusicais.get(atual)).play();       // procura a letra no hashMap da respectiva nota e reproduz o som do arquivo de música.
        this.atual++;                                    // a variável indicará "atual" indicará a próxima nota no array
        if(this.atual == notasMusicais.size){
            fimMusica = true;                            // se o array tiver chegado ao fim, o usuário finalizou a música
        }
    }

    public void dispose(){
        for(String key:sons.keySet()){
            sons.get(key).dispose();
        }
    }
    public void setAtual(){
        this.atual = 0;
    }

}
