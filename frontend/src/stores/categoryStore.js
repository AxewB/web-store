import { defineStore } from 'pinia'
import axios from 'axios'
import contentHeader from '@/services/content-header';
import authHeader from '@/services/auth-header';
import ResponseHandler from '@/scripts/responseHandler';
const API_URL = "http://localhost:8080/api/category";


export const useCategoryStore = defineStore('category', {
  state: () => ({
    categories: [],
    category: {},
    categoryPath: [],
  }),
  getters: {
    /**
     * Возвращает корневые категории.
     * @param {Object} state - Состояние хранилища.
     * @returns {Array} Массив корневых категорий
     */
    rootCategories(state) {
      return state.categories.filter((cat) => cat.parent === null)
    },
    /**
     * Возвращает массив с именами категорий.
     * @param {Object} state - Состояние хранилища.
     * @returns {Array} - Массив имен
     */
    getCategoriesNames(state) {
      return state.categories.map((cat) => cat.name)
    },
    /**
     * Возвращает массив с именами и id категорий.
     * @param {Object} state - Состояние хранилища.
     * @returns {Array} - массив с именами и id категорий
     */
    getCategoriesNamesAndIds(state) {
      return state.categories.map((cat) => ({ name: cat.name, id: cat.id }))
    }
  },
  actions: {
    /**
     * Запрос на получение списка категорий.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategories() {
      return axios.get(API_URL)
      .then((response) => {
        this.categories = response.data
        return ResponseHandler.success(response, "Категории получены")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении категорий")
      });
    },
    /**
     * Запрос на получение списка категорий с дочерними категориями.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategoriesWithChildren() {
      return axios.get(API_URL + "/all-with-children")
      .then((response) => {
        this.categories = response.data
        return ResponseHandler.success(response, "Категории получены")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении категорий")
      });
    },
    /**
     * Получение конкретной категории по id
     * @param {Number} id - id категории
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ответ содержит объект категории. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategory(id) {
      return axios.get(API_URL + `/${id}`)
      .then((response) => {
        return ResponseHandler.success(response , "Категория получена", response.data)
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении категории")
      })
    },
    /**
     * Получение пути от корневой катгории к указанной.
     * @param {Number} id - id категории.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategoryPath(id) {
      return axios.get(API_URL + `/${id}/path`)
      .then((response) => {
        this.categoryPath = response.data
        return ResponseHandler.success(response, "Путь к категории получен")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении пути к категории")
      })
    },

    /**
     * Получение дочерних категорий к указанной.
     * @param {Number} parentId - id категории.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ответ содержит массив дочерних категорий. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getChildren(parentId) {
      return axios.get(API_URL + `/${parentId}/children`)
      .then((response) => {
        return ResponseHandler.success(response, "Дочерние категории получены")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении дочерних категорий")
      });
    },

    /**
     * Запрос на добавление категории
     * @param {Object} category - Категория, которая будет добавлена
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async addCategory(category) {
      await this.getCategories();
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null
      const data = {
        "name": category.name,
        "parent": parent
      }
      return axios.post(API_URL, data, { headers: contentHeader() })
      .then(response => {
        return ResponseHandler.success(response, "Категория успешно добавлена")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при добавлении категории")
      });
    },

    /**
     * Запрос на изменение параметров категорий.
     * @param {Number} id - id категории, которую нужно изменить.
     * @param {Object} category - Новые данные.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async updateCategory(id, category) {
      await this.getCategories();
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null
      const data = {
        name: category.name,
        parent: parent
      }

      return axios.put(API_URL + `/${id}`, data, { headers: contentHeader() })
      .then(response => {
        return ResponseHandler.success(response, "Категория успешно изменена")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при изменении категории")
      });
    },

    /**
     * Запрос на удаление категории
     * @param {Number} id - id категории
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async deleteCategory(id) {
      return axios.delete(API_URL + `/${id}`, { headers: authHeader() })
      .then(response => {
        return ResponseHandler.success(response, "Категория успешно удалена")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при удалении категории")
      });
    },
  }
})