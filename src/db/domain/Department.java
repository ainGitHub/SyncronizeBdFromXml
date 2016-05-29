package db.domain;

/**
 * Сущность для данных из таблицы
 */
public class Department {
    Long id;

    String depCode;

    String depJob;

    String description;

    public Department() {
    }

    public Department(String depCode, String depJob, String description) {
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (depCode != null ? !depCode.equals(that.depCode) : that.depCode != null) return false;
        return !(depJob != null ? !depJob.equals(that.depJob) : that.depJob != null);

    }

    @Override
    public int hashCode() {
        int result = depCode != null ? depCode.hashCode() : 0;
        result = 31 * result + (depJob != null ? depJob.hashCode() : 0);
        return result;
    }
}
