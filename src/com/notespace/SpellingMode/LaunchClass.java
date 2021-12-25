package com.notespace.SpellingMode;

import java.util.Random;


public class LaunchClass
{
	
	public String dictFile = "data/dict.txt";
	
	public LaunchClass () {
		super();
	}
	
	public com.notespace.SpellingMode.document.Document getDocument( String text) {
		// Change this to BasicDocument(text) for week 1 only
		return new com.notespace.SpellingMode.document.EfficientDocument(text);
	}
	
	public com.notespace.SpellingMode.textgen.MarkovTextGenerator getMTG() {
		return new com.notespace.SpellingMode.textgen.MarkovTextGeneratorLoL(new Random());
	}
	
	public com.notespace.SpellingMode.spelling.WordPath getWordPath() {
		return new com.notespace.SpellingMode.spelling.WPTree();
	}
	
    public com.notespace.SpellingMode.spelling.AutoComplete getAutoComplete() {
	  com.notespace.SpellingMode.spelling.AutoCompleteDictionaryTrie tr = new com.notespace.SpellingMode.spelling.AutoCompleteDictionaryTrie();
	  com.notespace.SpellingMode.spelling.DictionaryLoader.loadDictionary(tr, dictFile);
        return tr;
    }
    
    public com.notespace.SpellingMode.spelling.Dictionary getDictionary() {
	  com.notespace.SpellingMode.spelling.Dictionary d = new com.notespace.SpellingMode.spelling.DictionaryBST();
	  com.notespace.SpellingMode.spelling.DictionaryLoader.loadDictionary(d, dictFile);
    	return d;
    }
    
    public com.notespace.SpellingMode.spelling.SpellingSuggest getSpellingSuggest( com.notespace.SpellingMode.spelling.Dictionary dic) {
    	//return new spelling.SpellingSuggestNW(new spelling.NearbyWords(dic));
    	return new com.notespace.SpellingMode.spelling.NearbyWords(dic);
    
    }
}
