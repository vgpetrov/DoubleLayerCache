package ru.main;

import java.io.Serializable;

import ru.cache.DoubleLayerCache;
import ru.cache.ICache;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Создадим кэш состоящий из 20 элементов в оперативной памяти и 50 на диске
		// временное хранилище для файлов определим как tempDir
		ICache<Integer, Serializable> cache = new DoubleLayerCache<Integer, Serializable>(
				20, 50, "tempDir");
		// Закинем в кэш некоторое количество объектов
		cache.putObject(1, "A");
		cache.putObject(2, "B");
		cache.putObject(3, "C");
		cache.putObject(4, "D");
		cache.putObject(5, "E");
		cache.putObject(6, "F");
		cache.putObject(7, "G");
		cache.putObject(8, "H");

		// Выведем объект
		System.out.println(cache.getObject(5));
		
		// Удалим объект, 
		cache.removeObject(5);
		
	}

}
