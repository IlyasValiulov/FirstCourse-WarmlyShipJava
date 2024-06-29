package CollectionGenericObjects;

import DrawingShip.DrawingShip;
import DrawingShip.ExtentionDrawningShip;
import Exceptions.CollectionOverflowException;
import Exceptions.ObjectNotFoundException;
import Exceptions.PositionOutOfCollectionException;

import java.io.*;
import java.util.*;

public class StorageCollection<T extends DrawingShip> {
    private Map<String, ICollectionGenericObjects<T>> _storages;
    public StorageCollection()
    {
        _storages = new HashMap<String, ICollectionGenericObjects<T>>();
    }
    public Set<String> Keys() {
        Set<String> keys = _storages.keySet();
        return keys;
    }
    public void AddCollection(String name, CollectionType collectionType)
    {
        if (_storages.containsKey(name)) return;
        if (collectionType == CollectionType.None) return;
        else if (collectionType == CollectionType.Massive)
            _storages.put(name, new MassiveGenericObjects<T>());
        else if (collectionType == CollectionType.List)
            _storages.put(name, new ListGenericObjects<T>());
    }
    public void DelCollection(String name)
    {
        if (_storages.containsKey(name))
            _storages.remove(name);
    }
    // в джаве отсутствуют индикаторы
    public ICollectionGenericObjects<T> getCollectionObject(String name) {
        if (_storages.containsKey(name))
            return _storages.get(name);
        return null;
    }
    //дополнительное задание номер 1
    public T Get(String name, int position) throws ObjectNotFoundException, PositionOutOfCollectionException {
        if(_storages.containsKey(name))
            return _storages.get(name).Remove(position);
        return null;
    }
    private String _collectionKey = "CollectionsStorage";
    private String _collectionName = "StorageCollection";
    private String _separatorForKeyValueS = "|";
    private String _separatorForKeyValue = "\\|";
    private String _separatorItemsS = ";";
    private String _separatorItems = "\\;";
    public void SaveData(String filename) throws Exception {
        if (_storages.isEmpty()) throw new Exception("В хранилище отсутствуют коллекции для сохранения");
        File file = new File(filename);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(_collectionKey);
            writer.write("\n");
            for (Map.Entry<String, ICollectionGenericObjects<T>> value : _storages.entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(value.getKey());
                sb.append(_separatorForKeyValueS);
                sb.append(value.getValue().GetCollectionType());
                sb.append(_separatorForKeyValueS);
                sb.append(value.getValue().getCount());
                sb.append(_separatorForKeyValueS);
                for (T ship : value.getValue().GetItems()) {
                    String data = ExtentionDrawningShip.GetDataForSave((DrawingShip) ship);
                    if (data.isEmpty()) continue;
                    sb.append(data);
                    sb.append(_separatorItemsS);
                }
                sb.append("\n");
                writer.write(String.valueOf(sb));
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SaveOneCollection(String filename, String name) throws Exception {
        if (_storages.isEmpty()) throw new Exception("В хранилище отсутствуют коллекции для сохранения");
        File file = new File(filename);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(_collectionName);
            writer.write("\n");
            ICollectionGenericObjects<T> value = _storages.get(name);
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(_separatorForKeyValueS);
            sb.append(value.GetCollectionType());
            sb.append(_separatorForKeyValueS);
            sb.append(value.getCount());
            sb.append(_separatorForKeyValueS);
            for (T ship : value.GetItems()) {
                String data = ExtentionDrawningShip.GetDataForSave((DrawingShip) ship);
                if (data.isEmpty()) continue;
                sb.append(data);
                sb.append(_separatorItemsS);
            }
            writer.append(sb);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadData(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists()) throw new Exception("Файл не существует");
        try (BufferedReader fs = new BufferedReader(new FileReader(filename))) {
            String s = fs.readLine();
            if (s == null || s.isEmpty() || !s.startsWith(_collectionKey))
                throw new Exception("В файле нет данных или данные неверные");
            _storages.clear();
            s = "";
            while ((s = fs.readLine()) != null) {
                String[] record = s.split(_separatorForKeyValue);
                if (record.length != 4) {
                    continue;
                }
                ICollectionGenericObjects<T> collection = CreateCollection(record[1]);
                if (collection == null)
                {
                    throw new Exception("Не удалось создать коллекцию");
                }
                collection.SetMaxCount(Integer.parseInt(record[2]));
                String[] set = record[3].split(_separatorItems);
                for (String elem : set) {
                    try
                    {
                        DrawingShip ship = ExtentionDrawningShip.CreateDrawingShip(elem);
                        if (collection.Insert((T) ship) == -1)
                        {
                            throw new Exception("Объект не удалось добавить в коллекцию: " + record[3]);
                        }
                    }
                    catch (CollectionOverflowException ex)
                    {
                        throw new Exception("Коллекция переполнена", ex);
                    }
                }
                _storages.put(record[0], collection);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadOneCollection(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists()) throw new Exception("Файл не существует");
        try (BufferedReader fs = new BufferedReader(new FileReader(filename))) {
            String s = fs.readLine();
            if (s == null || s.isEmpty() || !s.startsWith(_collectionName))
                throw new Exception("В файле нет данных или данные неверные");
            if (_storages.containsKey(s)) {
                _storages.get(s).ClearCollection();
            }
            s = fs.readLine();
            String[] record = s.split(_separatorForKeyValue);
            if (record.length != 4) {
                throw new Exception("Неверные данные");
            }
            ICollectionGenericObjects<T> collection = CreateCollection(record[1]);
            if (collection == null)
            {
                throw new Exception("Не удалось создать коллекцию");
            }
            collection.SetMaxCount(Integer.parseInt(record[2]));
            String[] set = record[3].split(_separatorItems);
            for (String elem : set) {
                try
                {
                    DrawingShip ship = ExtentionDrawningShip.CreateDrawingShip(elem);
                    if (collection.Insert((T) ship) == -1)
                    {
                        throw new Exception("Объект не удалось добавить в коллекцию: " + record[3]);
                    }
                }
                catch (CollectionOverflowException ex)
                {
                    throw new Exception("Коллекция переполнена", ex);
                }
            }
            _storages.put(record[0], collection);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ICollectionGenericObjects<T> CreateCollection(String s) {
        switch (s) {
            case "Massive":
                return new MassiveGenericObjects<T>();
            case "List":
                return new ListGenericObjects<T>();
        }
        return null;
    }
}
