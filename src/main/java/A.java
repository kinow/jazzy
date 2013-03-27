import java.io.File;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;


public class A {

    public static void main(String[] args) throws Exception {
        SpellCheckListener listener = new SpellCheckListener() {
            public void spellingError(SpellCheckEvent event) {
                System.out.println(event.getInvalidWord());
                System.out.println(event.getSuggestions());
                System.out.println(event.getReplaceWord());
                System.out.println(event.getWordContext());
            }
        };
        SpellChecker sc = new SpellChecker();
        File fileDict = new File("dict/br2.dic");
        SpellDictionary dict = new SpellDictionaryHashMap(fileDict);
        sc.addDictionary(dict);
        sc.addSpellCheckListener(listener);
        int total = sc.checkSpelling(new StringWordTokenizer("O domingo (17) foi marcado por fortes chuvas na Região Sudeste, principalmente nos estados de São Paulo e Rio de Janeiro. Diversas cidades paulistas registraram alagamentos, principalmente no Litoral Sul e Norte do estado. Uma delas, São Sebastião, decretou estado de calamidade pública."));
        System.out.println(total);
        total = sc.checkSpelling(new StringWordTokenizer("Delicioza"));
        System.out.println(total);
    }
    
}
