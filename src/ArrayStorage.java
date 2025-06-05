import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        if (size != 0) {
            Arrays.fill(storage, null);
        }
        size = 0;
    }

    void update(Resume r) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.uuid.equals(r.uuid)) {
                storage[i] = r;
            } else {
                System.out.println("Резюме не существует!");
            }
        }
    }

    void save(Resume r) {
        if (size == 0) {
            storage[size] = r;
            size++;
        } else if (size >= storage.length) {
            System.out.println("Ошибка!Переполнение!");
        } else {
            int index = getIndex(r.uuid);
            if (index < 0) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Ошибка! Резюме уже существует!" + r.uuid);
            }
        }
    }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }

    void delete(String uuid) {
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
    Resume[] getAll() {
        if (size != 0) {
            Resume[] resumes = new Resume[size];
            System.arraycopy(storage, 0, resumes, 0, size);
            return resumes;
        }
        return new Resume[0];
    }

    int size() {
        return size;
    }

    int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
