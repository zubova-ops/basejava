package ru.javawebinar.basejava.storage;

public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapUuidStorage());
    }
}