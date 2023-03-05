package kz.zvezdochet.core.bean;

import java.io.Serializable;

import kz.zvezdochet.core.service.DataAccessException;
import kz.zvezdochet.core.service.ModelService;

/**
 * Прототип объекта предметной области
 * @author Natalie Didenko
 */
public abstract class Model implements Serializable {
	private static final long serialVersionUID = 1419179855113656076L;
	
	/**
	 * Идентификатор
	 */
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Возвращает сервис, поставляющий объект из БД
     * @return сервис объекта для взаимодействия с БД
     */
    public abstract ModelService getService();

    /**
     * Поле для хранения временного параметра модели
     */
    protected Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Поле для хранения временного статуса модели
	 */
	protected boolean done = false;

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Инициализация модели
	 * @param mode параметр для выполнения условия инициализации
	 */
	public abstract void init(boolean mode);

	/**
	 * Признак импортируемой модели
	 */
	protected boolean importable = false;

	public boolean isImportable() {
		return importable;
	}

	public void setImportable(boolean importable) {
		this.importable = importable;
	}

	/**
	 * Сохранение модели в БД
	 */
	public Model save() {
		try {
			Model model = getService().save(this);
			onSave();
			return model;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Действия после сохранения модели
	 * @param obj объект
	 */
	protected void onSave() {}

	/**
	 * Проверка, является ли модель сохранённой в БД
	 * @return
	 */
	public boolean isExisting() {
		return id != null && id > 0;
	}
}
