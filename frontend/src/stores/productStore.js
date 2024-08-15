import {defineStore} from 'pinia';
import axios from 'axios';
import { useCategoryStore } from '@/stores/categoryStore';
import authHeader from "@/services/auth-header";
import contentHeader from '@/services/content-header';
import ResponseHandler from '@/scripts/responseHandler';
import { useRequestStore } from './requestStore';

const API_URL = "http://localhost:8080/api/products";

export const useProductStore = defineStore('products', {
  state: () => ({
    products: [], // Список продуктов
    total: 0,     // Общее количество продуктов
    skip: 0,      // Количество пропускаемых продуктов
    limit: 10,    // Ограниечение на количество получаемых продуктов
    page: 1,      // Страница
  }),
  getters: {
    totalPages: (state) => Math.ceil(state.total / state.limit),
  },
  actions: {
    /**
     * Запрос на получение продуктов с сервера
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProducts() {
      useRequestStore().showLoading();;
      return axios.get(API_URL + `?skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        const responseHandler = ResponseHandler.success(response, "Продукты получены")
        useRequestStore().hideLoading(responseHandler, false)
        this.setFetchedParams(response.data)
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении продуктов")
        useRequestStore().hideLoading(responseHandler)
        return ResponseHandler.error(error, "Произошла ошибка при получении продуктов")
      });
    },
   
    /**
     * Запрос на получение продуктов согласно поисковому запросу
     * @param {String} name - Строка, по которой будет проходить поиск
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProductsByName(name) {
      useRequestStore().showLoading();;

      return axios.get(API_URL + `/search?name=${name}&skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        const responseHandler = ResponseHandler.success(response, "Продукты получены")
        useRequestStore().hideLoading(responseHandler, false)
        this.setFetchedParams(response.data)
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении продуктов")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },
    /**
     * Запрос на получение продуктов согласно категории
     * @param {Number} categoryId - id категории
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProductByCategory(categoryId) {
      useRequestStore().showLoading();

      return axios.get(API_URL + `/category/${categoryId}?skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        this.setFetchedParams(response.data)
        const responseHandler = ResponseHandler.success(response, "Продукты получены")
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении продуктов по категории")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      });
    },
    /**
     * Запрос на получение продукта по id
     * @param {Number} id - id продукта
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ответ содержит объект продукта. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProduct(id) {
      useRequestStore().showLoading();
      
      return axios.get(`${API_URL}/${id}`)
      .then((response) => {
        const responseHandler = ResponseHandler.success(response, "Продукт получен")
        useRequestStore().hideLoading(responseHandler, false)
        return responseHandler
      })
      .catch((error) => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при получении продукта")
        useRequestStore().hideLoading(responseHandler)
        return responseHandler
      })
    },

    /**
     * Запрос на добавление продукта в БД
     * @param {Object} product - Продукт, который нужно добавить
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async addProduct(product) {
      useRequestStore().showLoading();
      
      const categoryStore = useCategoryStore();
      const categories = categoryStore.categories.filter((cat) => product.categories.includes(cat.id));
      const data  = {
        "name": product.name,
        "description": product.description,
        "images": product.images,
        "thumbnail": product.thumbnail,
        "quantity": product.quantity,
        "cost": product.cost,
        "categories": categories
      }

      return axios.post(API_URL + '/add', data, { headers: contentHeader() })
      .then(response => {
        this.fetchProducts();
        const responseHandler = ResponseHandler.success(response, "Продукт успешно добавлен")
        useRequestStore().hideLoading(responseHandler);
        return responseHandler
      })
      .catch(error => {
        const responseHandler = ResponseHandler.error(error, "Произошла ошибка при добавлении продукта")
        useRequestStore().hideLoading(responseHandler);
        return responseHandler
      });

    },

    /**
     * Запрос на обновление параметров продукта
     * @param {Number} id - id изменяемого продукта
     * @param {Object} product - новые данные
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async updateProduct(id, product) {
      useRequestStore().showLoading();

      const categoryStore = useCategoryStore();
      const categories = categoryStore.categories.filter((cat) => product.categories.includes(cat.id));
      const data = {
        "name": product.name,
        "description": product.description,
        "images": product.images,
        "thumbnail": product.thumbnail,
        "quantity": product.quantity,
        "cost": product.cost,
        "categories": categories
      }

      return axios.put(API_URL + `/${id}`, data, { headers: contentHeader() })
      .then(response => {
        this.fetchProducts();
        const requestHandler = ResponseHandler.success(response, "Продукт успешно обновлен")
        useRequestStore().hideLoading(requestHandler)
        return requestHandler
      })
      .catch(error => {
        const requestHandler = ResponseHandler.error(error, "Произошла ошибка при обновлении продукта")
        useRequestStore().hideLoading(requestHandler)
        return requestHandler
      });
    },

    /**
     * Запрос на удаление продукта
     * @param {Number} id - Идентификатор продукта
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async deleteProduct(id) {
      useRequestStore().showLoading();

      return axios.delete(API_URL + `/${id}`, { headers: authHeader() })
      .then(response => {
        const requestHandler = ResponseHandler.success(response, "Продукт успешно удален")
        useRequestStore().hideLoading(requestHandler)
        this.fetchProducts();
        return requestHandler
      })
      .catch(error => {
        const requestHandler = ResponseHandler.error(error, "Произошла ошибка при удалении продукта")
        useRequestStore().hideLoading(requestHandler)
        return requestHandler
      });

    },

    /**
     * Сеттер для параметра skip.
     * @param {Number} skip - Новое значение.
     */
    setSkip(skip) {
      this.skip = skip
    },

    /**
     * Сеттер для параметра limit.
     * @param {Number} limit - Новое значение.
     */
    setLimit(limit) {
      this.limit = limit
    },

    /**
     * Переход на страницу
     * @param {Number} page - Выбранная страница.
     */
    async turnPage(page) {
      this.page = page
      this.skip = (this.page - 1) * this.limit
      this.fetchProducts()
    },
    /**
     * Вспомогательный метод для установки полученных значений.
     * @param {Object} params - Новые значения.
     */
    setFetchedParams(params) {
      if (params) {
        this.products = params.data;
        this.skip = params.skip;
        this.limit = params.limit;
        this.total = params.total;
        this.page = 1
      }
      else {
        this.resetStore();
      }
    },
    /**
     * Сброс значений хранилища
     */
    resetStore() {
      this.products = []
      this.total = 0
      this.skip = 0
      this.limit = 10
      this.page = 1
    },
  }
})