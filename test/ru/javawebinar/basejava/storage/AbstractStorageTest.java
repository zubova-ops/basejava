package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");

        RESUME_1.addContact(ContactType.PHONE, "89966393181");
        RESUME_1.addSection(SectionType.PERSONAL, new ListSection(Arrays.asList("Коммуникабельный", "Трудолюбивый")));
        RESUME_1.addContact(ContactType.MAIL, "staszubov301995@mail.ru");
        RESUME_1.addContact(ContactType.SKYPE, "staszubov.@skype.com");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Achievement11", "Achievement12", "Achievement13")));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Java", "JavaScript", "SQL")));
        RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(List.of(
                new Organization(new Link("Inotex", "www.inotex.ru"),
                        List.of(new Organization.Position(
                                LocalDate.of(2012, 12, 1),
                                LocalDate.of(2024, 10, 14),
                                "Programmer", "progau"),
                                new Organization.Position(LocalDate.of(2024, 12, 10),
                                LocalDate.of(2025,5,5),"Analitic", "analitu"))))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(
                List.of(new Organization(new Link("College", "www.mgu.ru"),
                        List.of(new Organization.Position(LocalDate.of(2000, 10 , 2),
                                LocalDate.of(2003, 4, 12), "student", "Study"))))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}