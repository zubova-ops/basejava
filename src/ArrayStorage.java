/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                storage[i] = null;
            }
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.uuid.equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (size != 0) {
            Resume[] resumes = new Resume[size];
            for (int i = 0; i < size; i++) {
                resumes[i] = storage[i];
            }
            return resumes;
        }
        return new Resume[0];
    }

    int size() {
        return size;
    }
}
