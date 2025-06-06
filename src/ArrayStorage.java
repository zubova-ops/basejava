import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage{
    private static final int STORAGE_LIMIT = 10000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    public void clear() {
        if (size != 0) {
            Arrays.fill(storage, 0, size, null);
        }
        size = 0;
    }

    @Override
    public void update(Resume r) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.getUuid().equals(r.getUuid())) {
                storage[i] = r;
            } else {
                System.out.println("Резюме не существует!");
            }
        }
    }

    @Override
    public void save(Resume r) {
        if (size == 0) {
            storage[size] = r;
            size++;
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Ошибка!Переполнение!");
        } else {
            int index = getIndex(r.getUuid());
            if (index < 0) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Ошибка! Резюме уже существует!" + r.getUuid());
            }
        }
    }
    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        if (size != 0) {
            Resume[] resumes = new Resume[size];
            System.arraycopy(storage, 0, resumes, 0, size);
            return resumes;
        }
        return new Resume[0];
    }
    @Override
    public int size() {
        return size;
    }

    int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
