import { defineStore } from 'pinia'
import axios from 'axios'
import contentHeader from '@/services/content-header';
import authHeader from '@/services/auth-header';
import ResponseHandler from '@/scripts/responseHandler';
import { useRequestStore } from './requestStore';
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
      useRequestStore().showLoading()
      return axios.get(API_URL)
      .then((response) => {
        this.categories = response.data
        const responseHandler = ResponseHandler.success(response, "Категории получены")
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch((error) => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении категорий")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },
    /**
     * Запрос на получение списка категорий с дочерними категориями.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategoriesWithChildren() {
      useRequestStore().showLoading()
      return axios.get(API_URL + "/all-with-children")
      .then((response) => {
        this.categories = response.data
        const responseHandler = ResponseHandler.success(response, "Категории получены")
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch((error) => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении категорий")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },
    /**
     * Получение конкретной категории по id
     * @param {Number} id - id категории
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ответ содержит объект категории. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategory(id) {
      useRequestStore().showLoading()
      return axios.get(API_URL + `/${id}`)
      .then((response) => {
        const responseHandler = ResponseHandler.success(response , "Категория получена", response.data)
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch((error) => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении категории")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      })
    },
    /**
     * Получение пути от корневой катгории к указанной.
     * @param {Number} id - id категории.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCategoryPath(id) {
      useRequestStore().showLoading()
      return axios.get(API_URL + `/${id}/path`)
      .then((response) => {
        this.categoryPath = response.data
        const responseHandler = ResponseHandler.success(response, "Путь к категории получен")
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch((error) => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении пути к категории")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      })
    },

    /**
     * Получение дочерних категорий к указанной.
     * @param {Number} parentId - id категории.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ответ содержит массив дочерних категорий. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getChildren(parentId) {
      useRequestStore().showLoading()
      return axios.get(API_URL + `/${parentId}/children`)
      .then((response) => {
        const responseHandler = ResponseHandler.success(response, "Дочерние категории получены")
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch((error) => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении дочерних категорий")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },

    /**
     * Запрос на добавление категории
     * @param {Object} category - Категория, которая будет добавлена
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async addCategory(category) {
      useRequestStore().showLoading()
      if (!this.categories) {
        this.getCategories();
      }
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null
      const data = {
        "name": category.name,
        "parent": parent
      }
      return axios.post(API_URL, data, { headers: contentHeader() })
      .then(response => {
        const responseHandler = ResponseHandler.success(response, "Категория успешно добавлена")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при добавлении категории")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },

    /**
     * Запрос на изменение параметров категорий.
     * @param {Number} id - id категории, которую нужно изменить.
     * @param {Object} category - Новые данные.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async updateCategory(id, category) {
      useRequestStore().showLoading()
      await this.getCategories();
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null
      const data = {
        name: category.name,
        parent: parent
      }

      return axios.put(API_URL + `/${id}`, data, { headers: contentHeader() })
      .then(response => {
        const responseHandler = ResponseHandler.success(response, "Категория успешно изменена")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при изменении категории")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },

    /**
     * Запрос на удаление категории
     * @param {Number} id - id категории
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async deleteCategory(id) {
      useRequestStore().showLoading()
      return axios.delete(API_URL + `/${id}`, { headers: authHeader() })
      .then(response => {
        const responseHandler = ResponseHandler.success(response, "Категория успешно удалена")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при удалении категории")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },
  }
})